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
	int n=inf.readInt(1, 300'000, "n");
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
	inf.readEof();
	return 0;
}
