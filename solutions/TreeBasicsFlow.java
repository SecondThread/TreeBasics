import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
/*
2 1
1 2 1000000000
2
1 2
2 1

5 4
4 2 10348
1 4 2690
5 4 9807
3 4 8008
5
5 4
1 5
5 4
5 4
1 5
 */
public class TreeBasicsFlow {

	public static void main(String[] args) {
		FastScanner fs=new FastScanner();
		int n=fs.nextInt(); fs.next();
		Node[] nodes=new Node[n];
		for (int i=0; i<n; i++) nodes[i]=new Node(i+1);
		for (int i=1; i<n; i++) {
			int a=fs.nextInt()-1, b=fs.nextInt()-1, c=fs.nextInt();
			nodes[a].adj.add(nodes[b]);
			nodes[b].adj.add(nodes[a]);
			nodes[a].edges.add(c);
			nodes[b].edges.add(c);
		}
		nodes[0].dfs0(null, -1, 0);
		for (int e=1; e<20; e++)
			for (Node nn:nodes)
				if (nn.lift[e-1]!=null) {
					nn.lift[e]=nn.lift[e-1].lift[e-1];
					nn.minLift[e]=Math.min(nn.minLift[e-1], nn.lift[e-1].minLift[e-1]);
				}
		int q=fs.nextInt();
		PrintWriter out=new PrintWriter(System.out);
		for (int qq=0; qq<q; qq++) {
			Node a=nodes[fs.nextInt()-1], b=nodes[fs.nextInt()-1];
			if (a==b) throw null;
			Node lca=a.lca(b, 19);
			int min=Math.min(a.goUpMin(a.depth-lca.depth), b.goUpMin(b.depth-lca.depth));
			out.println(min);
		}
		out.close();
	}
	
	static class Node {
		Node[] lift=new Node[20];
		int[] minLift=new int[20];
		int depth, id;
		ArrayList<Node> adj=new ArrayList<>();
		ArrayList<Integer> edges=new ArrayList<>();
		
		public Node(int id) {
			this.id=id;
		}
		
		public void dfs0(Node par, int parEdge, int depth) {
			this.depth=depth;
			lift[0]=par;
			minLift[0]=parEdge;
			for (int i=0; i<adj.size(); i++) {
				if (adj.get(i)==par) continue;
				adj.get(i).dfs0(this, edges.get(i), depth+1);
			}
		}

		public Node goUp(int nSteps) {
			if (nSteps==0) return this;
			return lift[Integer.numberOfTrailingZeros(nSteps)].goUp(nSteps-Integer.lowestOneBit(nSteps));
		}
		
		public Node lca(Node o, int nJumps) {
			if (this==o) return this;
			if (depth!=o.depth) {
				if (depth>o.depth) return goUp(depth-o.depth).lca(o, 19);
				return lca(o.goUp(o.depth-depth), 19);
			}
			if (lift[0]==o.lift[0]) return lift[0];
			while (lift[nJumps]==o.lift[nJumps]) nJumps--;
			return lift[nJumps].lca(o.lift[nJumps], nJumps);
		}
		
		public int goUpMin(int nSteps) {
			if (nSteps==0) return Integer.MAX_VALUE;
			int minExp=Integer.lowestOneBit(nSteps);
			int exp=Integer.numberOfTrailingZeros(minExp);
			return Math.min(minLift[exp], lift[exp].goUpMin(nSteps-minExp));
		}

		public String toString() {
			return ""+id;
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
