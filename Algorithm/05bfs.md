# 너비 우선 탐색 (BFS : breadth-first search)
* 시작 정점을 방문한 후 시작 정점에 인접한 모든 정점들을 우선 방문하는 방식
* 장점 : BFS 방식으로 탐색하면, 출발 노드에서 목표 노드까지의 최단 길이 경로라는 것을 보장한다.
* 주로 queue와 함께 쓰임

* 문제 형식
  ```cpp
  queue<int> q;
  void bfs(){
    // q의 초깃값 push
    while(!q.empty()){ // q가 빌 때까지 반복문을 돈다.
      // q의 front를 하나 읽어옴
      // 읽어온 원소를 pop
      // 원소가 답이 될 수 있는지, 혹은 정보 update가 가능한지 check 이후 행동.
      
      // 읽어온 원소로부터 q에 push할 수 있는 원소를 push 하기.
    }
  }
  ```
