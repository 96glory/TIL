# C++의 String
## char & at(size_t index)
* index에는 숫자, 해당 위치에 해당하는 문자를 반환합니다.
* index는 0부터 시작합니다.
* index가 string의 범위를 벗어나게 되면 예외를 반환합니다.
```C++
String str = "MyName";
char c = str.at(0); // c = 'M'
```
