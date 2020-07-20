# 다익스트라 알고리즘 (Dijkstra Algorithm)
* 1번 정점에서 다른 모든 정점으로 갈 때의 최단 거리를 구하는 알고리즘
* 기본 로직
  * 첫 정점을 기준으로 연결되어 있는 정점들을 추가해가며 최단 거리를 갱신하는 것
  * 정점을 잇기 전까지는 시작점을 제외한 정점에 대한 거리는 모두 무한대 값을 가진다.
  * 정점 A에서 정점 B로 이어지면, 시작점에서 정점 B가 가지는 최단 거리는 (시작점부터 정점 A까지의 최단거리 + 정점 A와 정점 B 사이의 가중치)와 (기존에 가지고 있었던 시작점에서 정점 B까지의 최단 거리 중 작은 값이 B의 최단 거리)가 된다.
* 조건
  * 모든 간선의 비용은 양수여야 한다.
* 예시
  * 그래프
    ![graph](https://user-images.githubusercontent.com/52440668/87952770-2c6ec580-cae5-11ea-94de-378e7854a725.png)
  * 프로그램 과정
    ![process](https://user-images.githubusercontent.com/52440668/87952703-195bf580-cae5-11ea-8cbc-ead6959ed787.png)
    * 각 loop에서 dist의 최소 거리를 갖는 정점을 찾는 방법
      * priority queue의 최소 힙 이용
  * 코드
    ```cpp
    #include <bits/stdc++.h>
    #define MAX_DIS 2147000000

    using namespace std;

    struct Edge{
      int vertex;
      int distance;

      Edge(int a, int b){
        vertex = a;
        distance = b;
      }

      bool operator < (const Edge & b) const {
        return distance > b.distance;
      }
    };

    int main(){
      ios_base::sync_with_stdio(false);

      int i, n, m, a, b, c;
      priority_queue<Edge> PQ;
      vector< pair<int, int> > graph[30];

      cin >> n >> m;
      vector<int> dist(n + 1, MAX_DIS);

      for(i = 1; i <= m; i++){
        cin >> a >> b >> c;
        graph[a].push_back(make_pair(b, c));
      }

      PQ.push(Edge(1, 0));
      dist[1] = 0;
      while(!PQ.empty()){
        int now = PQ.top().vertex;
        int cost = PQ.top().distance;
        PQ.pop();

        if(cost > dist[now]) continue;
        for(i = 0; i < graph[now].size(); i++){
          int next = graph[now][i].first;
          int nextDis = cost + graph[now][i].second;
          if(dist[next] > nextDis){
            dist[next] = nextDis;
            PQ.push(Edge(next, nextDis));
          }
        }
      }

      for(i = 2; i <= n; i++){
        if(dist[i] != MAX_DIS)	cout << i << " : " << dist[i] << endl;
        else	cout << i << " : impossible" << endl;
      }
    }
    ```
