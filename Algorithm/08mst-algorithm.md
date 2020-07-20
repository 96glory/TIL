# 최소 스패닝 트리 알고리즘 (MST Algorithm : minimum spanning tree algorithm)
* ST (신장 트리, 스패닝 트리)
  * 그래프 내의 모든 정점을 포함하는 트리
  * 그래프의 최소 연결 부분 그래프로, 간선의 수가 가장 적은 트리
* MST : 사용된 간선들의 가중치 합이 최소인 트리
## Kruskal Algorithm with Union-Find Algorithm
1. 간선들을 나열하는 데, 간선의 비용이 낮은 순서로 나열한다. 
2. 비용이 낮은 순서대로 간선을 선택한다. 
3. 선택한 간선의 정점들을 find()하여 부모가 다르다면 그 간선은 만들 도로로 인정한다(union()). 부모가 같다면 사이클이 만들어지는 것이므로, 다음 간선을 선택한다. 
4. (간선 갯수 - 1)번 만큼 과정 2와 과정 3을 반복한다. 
```cpp
#include <iostream>
#include <algorithm>
#include <queue>
#include <vector>

using namespace std;

int unified[10001];

struct Edge{
	int v1;
	int v2;
	int cost;
	
	Edge(int a, int b, int c){
		v1 = a; v2 = b; cost = c;
	}
	
	bool operator < (Edge & b){
		return cost < b.cost;
	}
};

int Find(int v){
	if(v == unified[v]) return v;
	else return unified[v] = Find(unified[v]);
}

void Union(int a, int b){
	a = Find(a);
	b = Find(b);
	if(a != b) unified[a] = b;
}

int main(){
	vector<Edge> E;
	int i, n, m, a, b, c, cnt = 0, answer = 0;
	
	scanf("%d %d", &n, &m);
	for(i = 1; i <= n; i++)
		unified[i] = i;
	for(i = 1; i <= m; i++){
		scanf("%d %d %d", &a, &b, &c);
		E.push_back(Edge(a, b, c));
	}
	sort(E.begin(), E.end());
	
	for(i = 0; i < m; i++){
		int pa = Find(E[i].v1);
		int pb = Find(E[i].v2);
		if(pa != pb){
			answer += E[i].cost;
			Union(E[i].v1, E[i].v2);
		}
	}
	printf("%d", answer);
}
```

## Prim Algorithm with priority queue
1. PQ는 최소 힙으로 선언한다. 임의의 시작 정점을 선택한다.
2. 시작 정점을 PQ에 push 한다. PQ의 값은 (노드 번호, 시작 정점의 비용)이 된다.
3. PQ의 top의 값을 가져오고, pop한다. top의 노드 번호가 MST의 노드로 인정되어 있지 않다면, top은 MST의 새 노드로 인정된다. MST의 노드로 인정이 되어있다면, 다시 과정 3을 한다.
4. top의 노드에서 갈 수 있는 노드를 탐색하고, 각각 (노드 번호, 노드 비용)을 PQ에 push한다.
5. (정점 갯수 - 1)번 만큼 과정 3과 과정 4를 반복한다.
```cpp
#include <iostream>
#include <algorithm>
#include <queue>
#include <vector>

using namespace std;

int check[30];
struct Edge{
	int e;
	int cost;
	
	Edge(int a, int b){
		e = a;
		cost = b;
	}
	
	bool operator < (const Edge & b) const {
		return cost > b.cost;
	}
};

int main(){
	priority_queue<Edge> pq;
	vector< pair<int, int> > map[30];
	
	int i, n, m, a, b, c, answer = 0;
	scanf("%d %d", &n, &m);
	for(i = 1; i <= m; i++){
		scanf("%d %d %d", &a, &b, &c);
		map[a].push_back(make_pair(b, c));
		map[b].push_back(make_pair(a, c));
	}
	pq.push(Edge(1, 0));
	while(!pq.empty()){
		Edge temp = pq.top();
		pq.pop();
		int v = temp.e;
		int cost = temp.cost;
		if(check[v] == 0){
			answer += cost;
			check[v] = 1;
			for(i = 0; i < map[v].size(); i++){
				pq.push(Edge(map[v][i].first, map[v][i].second));
			}
		}
	}
	printf("%d", answer);
}
```
