# Union-Find Algorithm with disjoint-set
* 서로소(disjoint)인 집합들을 분할하는 방식.
* 주로 disjoint set이라는 자료구조와 함께 쓰인다.
  * 공통 원소가 있다면 같은 집합이고, 공통 원소가 없다면 다른 집합이다!
* 문제
  * 집합 원소들은 1부터 N까지의 숫자이고, 각각 두 원소는 같은 집합 내에 있다는 관계가 번호로 표현된 숫자쌍이 주어진다. 공통 원소가 있다면 같은 집합 내에 존재한다. 만약 (1, 2), (2, 3), (3, 4)의 숫자쌍이 주어지면 1, 2, 3, 4는 동일한 집합 내에 존재한다.
* 입력 예제
  ```
  9 7 // n, num of relations
  
  // relations
  1 2
  2 3
  3 4
  4 5
  6 7
  7 8
  8 9
  
  3 8 // are in same set?
  ```
* solution with disjoint set
  * disjoint set은 tree 구조로 구현한다.
    ![disjoint set은 tree 구조로 구현](https://user-images.githubusercontent.com/52440668/87876687-d92d4200-ca14-11ea-9f45-6a3bb86bfc19.png)
    * 1차원 배열로 tree 구조를 표현한다.
      * arr index는 원소 번호, arr 값은 집합의 번호.
  * int find(int index) : index의 집합 번호를 반환
  * void union(int a. int b) : a와 b 원소의 집합 번호를 각각 찾는다.
* 개선 전의 프로그램 과정
    ![개선 전의 프로그램 과정](https://user-images.githubusercontent.com/52440668/87877101-4e017b80-ca17-11ea-8c28-fa9a04bbe6fb.png)
* 개선 전의 코드
  ```cpp
  #include <iostream>
  #include <vector>
  using namespace std;

  int n, m, a, b, temp;
  vector<int> arr(1001);

  int find(int index){
    if(arr[index] == index)	return index;
    else	return find(arr[index]);
  }

  void union(int a, int b){
    a = find(a);
    b = find(b);
    if(a != b) arr[a] = b;
  }

  int main(){
    scanf("%d %d", &n, &m);
    for(int i = 1; i <= n ; i++)
      arr[i] = i;
    for(int i = 1; i <= m; i++){
      scanf("%d %d", &a, &b);
      union(a, b);
    }
      
    scanf("%d %d", a, b);
    if(find(a) == find(b)) printf("YES");
    else printf("NO");
  }
  ```
* 개선 후의 프로그램 과정 (경로 압축)
  ![개선 후의 프로그램 과정](https://user-images.githubusercontent.com/52440668/87877260-3c6ca380-ca18-11ea-9afc-a7baecc40719.png)
  * 개선 후의 코드
    ```cpp
    #include <iostream>
    #include <vector>
    using namespace std;
    int n, m, a, b, temp;
    vector<int> arr(1001);

    int find(int index){
      if(arr[index] == index)	return index;
      else return arr[index] = find(arr[index]);
    }

    void union(int a, int b){
      a = find(a);
      b = find(b);
      if(a != b) arr[a] = b;
    }

    int main(){
      scanf("%d %d", &n, &m);
      for(int i = 1; i <= n ; i++)
        arr[i] = i;
      for(int i = 1; i <= m; i++){
        scanf("%d %d", &a, &b);
        union(a, b);
      }

      scanf("%d %d", a, b);
      if(find(a) == find(b)) printf("YES");
      else printf("NO");
    }
    ```
