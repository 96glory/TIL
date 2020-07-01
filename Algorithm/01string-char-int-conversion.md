# String과 Char *, int 의 변환

## 1. char * -> string
```C++
#include <iostream>
using namespace std;

char * cStr = "Cstring";
string appStr = cStr;
```

## 2. string -> char *
```C++
#include <iostream>
using namespace std;

string cStr = "Cstring";
const char * cStr2 = cStr.c_str();
// const 타입으로 리턴되는 것을 기억하자.
```

## 3. char * -> int
```C++
#include <iostream>
using namespace std;

char * cStr = "20200701"
int num = atoi(cStr);
```

## 4. string -> char * -> int
```C++
#include <iostream>
string s = "2020"
int num = atoi(s.c_str());
```
