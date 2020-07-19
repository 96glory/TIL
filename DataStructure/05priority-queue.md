# 우선순위 큐 (priority queue)
## 최대 힙 (Max Heap)
* 부모 노드가 왼쪽 자식 노드와 오른쪽 자식 노드보다 값이 커야 한다.
* 빈 트리에서 차례로 원소를 넣는다고 가정.
  * 빈 노드(leaf)에 원소를 넣는다.
  * 부모 노드보다 값이 작다면, 그 노드가 원소의 자리다.
  * 부모 노드보다 값이 크다면, 부모 노드보다 값이 작을 때까지 부모와 자리를 바꾼다. 그 노드가 원소의 자리다.
* C++ STL 사용 예시
  ```cpp
  #include <iostream>
  #include <queue>

  using namespace std;

  int main(){
    priority_queue<int, vector<int>, less<int>> pq;
    int temp;
    while(-1){
      scanf("%d", &temp);
      if(temp == -1) break;
      else if(temp == 0){
        if(pq.empty())	printf("-1\n");
        else{
          printf("%d\n", pq.top());
          pq.pop();	
        }
      }
      else pq.push(temp);
    }
  }
  ```
## 최소 힙 (Min Heap)
* 부모 노드가 왼쪽 자식 노드와 오른쪽 자식 노드보다 값이 작아야 한다.
* 빈 트리에서 차례로 원소를 넣는다고 가정.
  * 빈 노드(leaf)에 원소를 넣는다.
  * 부모 노드보다 값이 크다면, 그 노드가 원소의 자리다.
  * 부모 노드보다 값이 작다면, 부모 노드보다 값이 클 때까지 부모와 자리를 바꾼다. 그 노드가 원소의 자리다.
* C++ STL 사용 예시
  ```cpp
  #include <iostream>
  #include <queue>

  using namespace std;

  int main(){
    priority_queue<int, vector<int>, greater<int>> pq;
    int temp;
    while(-1){
      scanf("%d", &temp);
      if(temp == -1) break;
      else if(temp == 0){
        if(pq.empty())	printf("-1\n");
        else{
          printf("%d\n", pq.top());
          pq.pop();	
        }
      }
      else pq.push(temp);
    }
  }
  ```
  
