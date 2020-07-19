# stack
* 스택이란?
  * 한 쪽 끝에서만 자료를 넣고 뺄 수 있는 LIFO(Last In First Out) 형식의 자료 구조
  * 가장 최근에 스택에 추가한 항목이 가장 먼저 제거될 항목이다.
  
* 스택 직접 구현
  ```cpp
  int stack[MAXSIZE], top = -1;

  void push(int x){
    if(top >= MAXSIZE){
      printf("stack is full");
      exit(0);
    }
    stack[++top] = x;
  }

  int pop(){
    if(top < 0){
      printf("stack is empty");
      exit(0);
    }
    return stack[top--];
  }
  ```

* C++ STL stack 사용
  * stack\<int\> s;
  * s.push(element) : top에 원소를 추가
  * s.pop() : top에 있는 원소를 삭제
  * s.top() : top에 있는 원소를 반환
  * s.empty() : s가 비어있다면 true를, 비어있지 않다면 false를 반환
  * s.size() : s의 크기를 반환
