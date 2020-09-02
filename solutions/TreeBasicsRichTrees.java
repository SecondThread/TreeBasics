import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TreeBasicsRichTrees {

	public static void main(String[] args) {
		FastScanner fs=new FastScanner();
		int n=fs.nextInt();
		Node[] nodes=new Node[n];
		for (int i=0; i<n; i++) nodes[i]=new Node();
		for (int i=1; i<n; i++) {
			int a=fs.nextInt()-1, b=fs.nextInt()-1;
			nodes[a].adj.add(nodes[b]);
			nodes[b].adj.add(nodes[a]);
		}
		nodes[0].dfs(null, 0);
		BIT bit=new BIT(n);
		int q=fs.nextInt();
		double[] values=new double[n];
		PrintWriter out=new PrintWriter(System.out);
		for (int qq=0; qq<q; qq++) {
			int type=fs.nextInt(), x=fs.nextInt(), y=fs.nextInt();
			if (type==1) {
				x--;
				
				double newVal=Math.log(y);
				double delta=newVal-values[x];
				bit.update(nodes[x].pre, delta);
				values[x]=newVal;
			}
			else {
				x--;
				y--;
				
				double xLog=bit.rangeSum(nodes[x].pre, nodes[x].post);
				double yLog=bit.rangeSum(nodes[y].pre, nodes[y].post);
				double ansLog=xLog-yLog;
				ansLog=Math.max(Math.log(1e-9), Math.min(ansLog, Math.log(1e9)));
				out.printf("%.10f\n", Math.exp(ansLog));
			}
		}
		out.close();
	}
	
	
	
	static class Node {
		int pre, post;
		ArrayList<Node> adj=new ArrayList<>();
		
		public int dfs(Node from, int counter) {
			this.pre=counter;
			for (Node nn:adj) {
				if (nn==from) continue;
				counter=nn.dfs(this, counter+1);
			}
			return post=counter;
		}
	}
	
	static class BIT {
		int n;
		double[] tree;
		
		public BIT(int n) {
			this.n = n;
			tree = new double[n + 2];
		}
		
		double read(int i) {
			i++;
			double sum = 0;
			while (i > 0) {
				sum += tree[i];
				i -= i & -i;
			}
			return sum;
		}
		
		void update(int i, double val) {
			i++;
			while (i <= n) {
				tree[i] += val;
				i += i & -i;
			}
		}
		
		double rangeSum(int from, int to) {
			return read(to)-read(from-1);
		}

	}

	
	static class FastScanner {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st=new StringTokenizer("");
		public String next() {
			while (!st.hasMoreElements())
				try {
					st=new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
	}

}
