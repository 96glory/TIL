# C++ STL map
* key-value 쌍을 여러 개를 갖는 자료구조
* 새로운 key-value 쌍을 넣을 때 균형 잡힌 이진 트리 형태로 key-value가 생성된다.
* 문자열 또는 알파벳 카운팅하는 문제에서 종종 쓰인다.
* 라이브러리 추가
  ```cpp
  #include <map>
  ```
* map 선언
  ```cpp
  map<char, int> m;
  ```
* map의 key로 접근하여 value 바꾸기 / 추가하기
  ```cpp
  m['c']++;
  m['d'] = 7;
  ```
* map의 모든 원소 출력
  ```cpp
  // with iterator
  map<char, int>::iterator itr;
  
  for(itr = m.begin(); itr != m.end(); itr++){
    cout << itr->first << " " << itr->second << endl;
  }
  
  // with auto
  for(auto &k : m){
    cout << k.first << " " << k.second << endl;
  }
  ```
* map의 단일 원소 출력
  ```cpp
  cout << m['c'] << endl;
  ```
  * 주의 : 개발자가 추가하지 않는 value를 출력하려고 할 때 오류를 반환하지 않고 0을 출력해준다.
