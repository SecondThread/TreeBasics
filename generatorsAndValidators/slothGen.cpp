#include "testlib.h"
#include <bits/stdc++.h>

using namespace std;
typedef pair<int, int> ii;

/*
Generates a random tree.

First arguement: n, the number of nodes
Second arguemnt: type of tree, as follows:
	1: rope
	2: almost rope
	3: balanced binary tree
	4: almost balanced binary tree
	5: random MST
 
 */
struct DisjointSet {
	vector<int> s;
	DisjointSet(int n): s(vector<int>(n, -1)) {}

	int find(int i) {
		return s[i]<0?i:(s[i]=find(s[i]));
	}

	void join(int a, int b) {
		a=find(a);b=find(b);
		if (a==b) return;
		if (s[a]==s[b]) s[a]--;
		if (s[a]<=s[b]) s[b]=a; else s[a]=b;
	}
};


vector<int> getPerm(int n) {
	vector<int> res(n);
	for (int i=0; i<n; i++) res[i]=i+1;
	shuffle(res.begin(), res.end());
	return res;
}

vector<ii> getRope(int n) {
	vector<ii> res;
	for (int i=1; i<n; i++) res.emplace_back(i-1, i);
	return res;
}

//makes the first 50% be a rope
vector<ii> getAlmostRope(int n) {
	assert(n>1);
	vector<ii> res=getRope(n/2);
	for (int i=n/2; i<n; i++)
		res.emplace_back(i, rnd.next(i));
	return res;
}

vector<ii> getBalancedTree(int n) {
	vector<ii> res;
	for (int i=1; i<n; i++)
		res.emplace_back(i, i/2);
	return res;
}

vector<ii> getAlmostBalancedTree(int n) {
	assert(n>1);
	vector<ii> res=getBalancedTree(n/2);
	for (int i=n/2; i<n; i++)
		res.emplace_back(i, rnd.next(i));
	return res;
}

vector<ii> getRandomMST(int n) {
	DisjointSet dj(n);
	vector<ii> res;
	while ((int)res.size()+1<n) {
		int a=rnd.next(n), b=rnd.next(n);
		if (dj.find(a) != dj.find(b)) {
			res.emplace_back(a, b);
			dj.join(a, b);
		}
	}
	return res;
}

int main(int argc, char* argv[]) {
	registerGen(argc, argv, 1);
	int n=atoi(argv[1]);
	int type=atoi(argv[2]);

	int q=n;

	vector<int> perm=getPerm(n);
	vector<ii> edges;
	if (type==1) {
		edges=getRope(n);
	}
	else if (type==2) {
		edges=getAlmostRope(n);
	}
	else if (type==3) {
		edges=getBalancedTree(n);
	}
	else if (type==4) {
		edges=getAlmostBalancedTree(n);
	}
	else if (type==5) {
		edges=getRandomMST(n);
	}
	else {
		assert(0);
	}
	
	cout<<n<<'\n';
	for (ii i:edges) {
		cout<<perm[i.first]<<" "<<perm[i.second]<<'\n';
	}

	cout<<q<<'\n';
	for (int i=0; i<q; i++) {
		int a=1+rnd.next(n), b=1+rnd.next(n);
		int dist=1+rnd.next(n);
		cout<<a<<" "<<b<<" "<<dist<<'\n';
	}

	return 0;
}

