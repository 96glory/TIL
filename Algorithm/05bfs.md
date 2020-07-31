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
  

* BFS를 활용하여 7 by 7 미로의 최단 거리 구하기
    * 주어진 미로
        ![그림1](https://user-images.githubusercontent.com/52440668/88052733-a78fb480-cb95-11ea-9fa7-ef0b07be4a04.png)
    * 코드
      ```cpp
      #include <bits/stdc++.h>
      using namespace std;

      int arr[9][9], dis[9][9], answer;
      int dx[4] = {0, 1, 0, -1};
      int dy[4] = {1, 0, -1, 0};

      struct Location{
          int x;
          int y;
          Location(int a, int b){
              x = a;	y = b;
          }
      };

      queue<Location> q;

      int main(){
          for(int i = 1; i <= 7; i++)
              for(int j = 1; j <= 7; j++)
                  scanf("%d", &arr[i][j]);

          q.push(Location(1, 1));
          arr[1][1] = 1;
          while(!q.empty()){
              Location temp = q.front();
              q.pop();
              for(int i = 0; i < 4; i++){
                  int xx = temp.x + dx[i];
                  int yy = temp.y + dy[i];
                  if(arr[xx][yy] == 0 && xx >= 1 && xx <= 7 && yy >= 1 && yy <= 7){
                      q.push(Location(xx, yy));
                      arr[xx][yy] = 1;
                      dis[xx][yy] = dis[temp.x][temp.y] + 1;
                  }
              }
          }
          printf("%d", dis[7][7]);
      }
      ```
