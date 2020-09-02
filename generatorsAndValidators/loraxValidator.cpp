#include "testlib.h"
#include <bits/stdc++.h>
using namespace std;


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

int main(int argc, char* argv[]) {
	registerValidation(argc, argv);
	int T=inf.readInt(1, 20, "T");
	inf.readEoln();
	for (int tt=0; tt<T; tt++) {
		int n=inf.readInt(1, 100'000, "n");
		inf.readSpace();
		int nq=inf.readInt(1, 100'000, "q");
		inf.readEoln();

		DisjointSet dj(n);
		for (int i=1; i<n; i++) {
			int a=inf.readInt(1, n, "a")-1;
			inf.readSpace();
			int b=inf.readInt(1, n, "b")-1;
			inf.readEoln();
			ensuref(dj.find(a) != dj.find(b), "graph must be a tree!");
			dj.join(a, b);
		}

		for (int qq=0; qq<nq; qq++) {
			int a=inf.readInt(1, n, "a");
			inf.readSpace();
			int b=inf.readInt(1, n, "b");
			inf.readSpace();
			int x=inf.readInt(0, 1000'000'00, "x");
			inf.readEoln();
		}
	}
	inf.readEof();
	return 0;
}
