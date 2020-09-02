import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

//fun fact: the word vaccine comes from the latin word for cow: vacca
public class TreeBasicsLorax {

	public static void solve(Scanner fs, PrintWriter out) {
		int T=fs.nextInt();
		for (int tt=0; tt<T; tt++) {
			int n=fs.nextInt();
			int nQueries=fs.nextInt();
			Node[] nodes=new Node[n];
			for (int i=0; i<n; i++) nodes[i]=new Node();
			for (int i=1; i<n; i++) {
				int a=fs.nextInt()-1, b=fs.nextInt()-1;
				nodes[a].adj.add(nodes[b]);
				nodes[b].adj.add(nodes[a]);
			}
			nodes[0].dfs(null, 0);
			BIT bit=new BIT(n);
			for (int qq=0; qq<nQueries; qq++) {
				int from=fs.nextInt()-1, to=fs.nextInt()-1, count=fs.nextInt();
				if (count==0) {
					Node lower= (nodes[from].par==nodes[to])?nodes[from]:nodes[to];
					long ans=bit.query(lower.postorder)-bit.query(lower.preorder-1);
					out.println(Math.abs(ans));
				}
				else {
					bit.update(nodes[from].preorder, count);
					bit.update(nodes[to].preorder, -count);
				}
			}
		}
		out.close();
	}
	
	static class Node {
		int preorder, postorder;
		ArrayList<Node> adj=new ArrayList<>();
		Node par;
		
		public int dfs(Node par, int counter) {
			this.par=par;
			if (par!=null) adj.remove(par);
			this.preorder=counter;
			for (Node nn:adj) counter=nn.dfs(this, counter+1);
			this.postorder=counter;
			return counter;
		}
	}
	
	static class BIT {
		long[] a;
		int n;
		public BIT(int n) {
			this.n=n+1;
			this.a=new long[n+2];
		}
		void update(int ind, int by) {
			ind++;
			while (ind<n) {
				a[ind]+=by;
				ind+=ind&-ind;
			}
		}
		long query(int i) {
			i++;
			long ans=0;
			while (i>0) {
				ans+=a[i];
				i-=(i&-i);
			}
			return ans;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		Thread t=new Thread(null, null, "", 1<<28) {
			public void run() {
				try {
					go(null);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		t.join();
	}
	
	public static void go(String[] args) throws FileNotFoundException {
		solve(new Scanner(System.in), new PrintWriter(System.out));
	}

}
