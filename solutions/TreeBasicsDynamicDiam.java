import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

public class TreeBasicsDynamicDiam {

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
		bfs(nodes[0], nodes);
		Node farthest=farthest(nodes);
		bfs(farthest, nodes);
		farthest=farthest(nodes);
		int origDiam=farthest.dist;
		for (Node nn:nodes) nn.diam|=nn.dist==origDiam;
		bfs(farthest, nodes);
		for (Node nn:nodes) nn.diam|=nn.dist==origDiam;
		
		PrintWriter out=new PrintWriter(System.out);
		for (int i=0; i<n; i++) {
			out.println(nodes[i].diam?origDiam+1:origDiam);
		}
		out.close();
	}
	
	static class Node {
		ArrayList<Node> adj=new ArrayList<>();
		int dist;
		
		boolean diam=false;
	}
	
	static Node farthest(Node[] nodes) {
		Node max=nodes[0];
		for (Node nn:nodes) if (nn.dist>max.dist) max=nn;
		return max;
	}
	
	static void bfs(Node from, Node[] nodes) {
		for (Node nn:nodes) nn.dist=-1;
		from.dist=0;
		ArrayDeque<Node> bfs=new ArrayDeque<>();
		bfs.add(from);
		while (!bfs.isEmpty()) {
			Node next=bfs.remove();
			for (Node nn:next.adj) {
				if (nn.dist==-1) {
					nn.dist=next.dist+1;
					bfs.add(nn);
				}
			}
		}
	}

	static final Random random=new Random();
	
	static void ruffleSort(int[] a) {
		int n=a.length;//shuffle, then sort 
		for (int i=0; i<n; i++) {
			int oi=random.nextInt(n), temp=a[oi];
			a[oi]=a[i]; a[i]=temp;
		}
		Arrays.sort(a);
	}
	
	static class FastScanner {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st=new StringTokenizer("");
		String next() {
			while (!st.hasMoreTokens())
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
		int[] readArray(int n) {
			int[] a=new int[n];
			for (int i=0; i<n; i++) a[i]=nextInt();
			return a;
		}
		long nextLong() {
			return Long.parseLong(next());
		}
	}


}
