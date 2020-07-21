# 벨만 포드 알고리즘 (Bellman-Ford Algorithm)
* 시작 정점에서 도착 정점까지의 최소 비용을 탐색하는 알고리즘
  * 간선의 갯수를 1개부터 (그래프 정점의 갯수 - 1)개까지 늘려가면서, 그 경로의 비용이 최소인지 아닌지를 고려한다.
* 다익스트라 알고리즘에 반해 음의 비용이 있어도 최소 비용 탐색이 가능하다.
* 조건
  * 음의 사이클이 존재하면 안된다.
    * 음의 사이클을 돌면 돌수록 비용이 낮아지기 때문에 최단 경로를 구하는 의미가 없어짐.
    * 음의 사이클이 존재한다면, 간선의 갯수를 그래프 정점의 갯수 이상을 선택한 값이 그 경로의 최소 비용이라는 의미다.

* 문제 예시 <br>
![graph](https://user-images.githubusercontent.com/52440668/88006266-32e15980-cb46-11ea-8461-e55bd4f752f9.png)
* 문제 과정
![process](https://user-images.githubusercontent.com/52440668/88006973-e008a180-cb47-11ea-9a78-0195b137dfc6.png)
  * 가로는 각 정점의 번호, 세로는 고려한 간선의 갯수
  * 간선을 추가할 때 (기존의 최소 비용)과 (새로 추가한 간선의 비용 + 시작 정점에서 새로 추가한 간선의 시작 정점을 잇는 간선의 비용)을 비교한다. 비용이 낮은 것으로 업데이트한다. 단, 무한대의 연산은 업데이트하지 않는다.
  * 위 과정을 간선의 갯수가 (그래프 정점의 갯수 - 1)개가 될 때까지 반복한다.
* 문제 코드
```cpp
#include <bits/stdc++.h>
#define DIS_MAX 2147000000

using namespace std;

int dist[101];
struct Edge{
	int start;
	int end;
	int val;
	Edge(int a, int b, int c){
		start = a;
		end = b;
		val = c;
	}
};

int main(){
	int i, n, m, a, b, c, j, s, e;
	vector<Edge> Ed;
	
	for(i = 1; i <= m; i++){
		scanf("%d %d %d", &a, &b, &c);
		Ed.push_back(Edge(a, b, c));
	}
	for(i = 1; i <= n; i++)
		dist[i] = DIS_MAX;
	
	scanf("%d %d", &s, &e);
	dist[s] = 0;
	
	// 간선의 갯수 
	for(i = 1; i < n; i++){
		// 모든 간선에 대해 적용 
		for(j = 0; j < Ed.size(); j++){
			int u = Ed[j].start;
			int v = Ed[j].end;
			int w = Ed[j].val;
			if(dist[u] != DIS_MAX && dist[u] + w < dist[v]) 
				dist[v] = dist[u] + w;
		}
	}
	
	// 음의 사이클이 있는 지 계산. n 번째 간선을 추가했을 때 최소 비용이 나오는 것이 있는가를 검사. 
	for(j = 0; j < Ed.size(); j++){
		int u = Ed[j].start;
		int v = Ed[j].end;
		int w = Ed[j].val;
		if(dist[u] != DIS_MAX && dist[u] + w < dist[v]){
			printf("-1");
			exit(0);
		}
	}
	printf("%d", dist[e]);
}
```
