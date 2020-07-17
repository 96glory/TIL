# queue
* 먼저 집어 넣은 데이터가 먼저 나오는 FIFO (First In First Out)구조로 저장하는 자료구조
* BFS에 자주 쓰임

* 직접 구현
  ```cpp
  #define MAX 100
  int Q[MAX], front = -1, back = -1;
  
  int isEmpty(){
    if(front == back) return true;
    else return false;
  }
  
  int isFull(){
    int tmp = (back + 1) % MAX;
    if(tmp == front) return true;
    else return false;
  }
  
  void push(int value){
    if(isFull()){
      printf("Queue is full.\n");
      exit(0);
    }
    else{
      back = (back + 1) % MAX;
      Q[back] = value;
    }
  }
  
  int pop(){
    if(isEmpty()){
      printf("Queue is empty.\n");
      exit(0);
    }
    else{
      front = (front + 1) % MAX;
      return queue[front];
    }
  }
  ```

* STL queue 이용
  * 라이브러리 추가
    ```cpp
    #include <queue>
    ```
  * 새로운 큐 선언
    ```cpp
    queue<int> q;
    ```
  * 큐에 원소 push
    ```cpp
    q.push(element);
    ```
  * 큐의 원소 pop
    ```cpp
    q.pop();
    ```
  * 큐 제일 앞에 있는 원소 반환
    ```cpp
    int a = q.front();
    ```
  * 큐 제일 뒤에 있는 원소 반환
    ```cpp
    int b = q.back();
    ```
  * 큐가 비어있으면 true, 비어있지 않다면 false
    ```cpp
    q.empty();
    ```
  * 큐의 원소 갯수 반환
    ```cpp
    q.size();
    ```
