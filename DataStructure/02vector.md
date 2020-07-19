# C++ STL vector
* 라이브러리 추가
  ```cpp
  #include <vector>
  ```
  
* vector 맨 뒤에 원소 삽입하기
  ```cpp
  a.push_back(6);
  a.push_back(8);
  a.push_back(11);
  ```

* vector의 원소 갯수 출력하기
  ```cpp
  cout << a.size() << endl;
  ```

* vector의 원소 출력하기
  ```cpp
  cout << a[1] << endl;
  cout << a.at(1) << endl;
  for(auto k : a) cout << k;
  ```

* vector 생성
  * empty vector 생성
    ```cpp
    vector<int> a;
    ```
  * size가 정의된 vector 생성
    ```cpp
    vector<int> a(5);
    ```
  * vector 배열 생성
    ```cpp
    vector<int> a[3];
    c[0].push_back(1);
    c[0].push_back(2);
    c[1].push_back(2);
    c[2].push_back(3);
    cout << c[1][0] << endl;
    ```
  * pair와 vector를 혼합
    ```cpp
    vector< pair<int, int> > graph[3];
    graph[1].push_back({3, 5});
    graph[2].push_back(make_pair(7, 7));
    cout << graph[1][0].first << " " << graph[2][0].second << endl;
  * struct와 vector를 혼합
    ```cpp
    struct Data{
      int money;
      int when;
      Data(int a, int b){
        money = a;
        when = b;
      }
      bool operator<(const Data &b) const {
        return when > b.when;
      }	
    };

    int main(){
      vector<Data> T;
      // ...
      T.push_back(Data(a, b));
      sort(T.begin(), T.end());
      // ...
    }
    ```
