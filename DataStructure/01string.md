# C++의 String
## .at
* index에는 숫자, 해당 위치에 해당하는 문자를 반환합니다.
* index는 0부터 시작합니다.
* index가 string의 범위를 벗어나게 되면 예외를 반환합니다.
    ```C++
    String str = "MyName";
    char c = str.at(0); // c = 'M'
    ```

## .assign
* 문자열을 할당
* ( 문자열 ) : 문자열을 할당
* ( 개수, 문자 ) : 문자를 개수만큼 할당한다.
* ( 문자열, 시작위치, 개수 ) : 매개변수 문자열의 시작 위치부터 개수만큼을 호출한 문자열에 할당
    ```cpp
    string s1, s2, s3;
    s1.assign("ABCDEFG"); // s1 = "ABCDEFG"
    s2.assign(3,'a');     // s2 = "aaa"
    s3.assign(s1,2,4);    // s3 = "CDEF"
    ```

## .append
* 문자열을 끝에 더한다.
* ( 문자열 ) : 문자열을 더한다.
* ( 개수, 문자 ) : 문자를 개수만큼 끝에 더한다.
* ( 문자열, 시작위치, 개수 ) : 매개변수 문자열의 시작 위치부터 개수 만큼을 호출한 문자열에 할당
    ```cpp
    string s1, s2;
    s1.append("ABCDEF");  // s1 = "ABCDEF"
    s1.append(3,'x');     // s1 = "ABCDEFxxx"
    s2.append(s,2,4);     // s2 = "CDEF"
    s2 += "X";            // s2 = "CDEFX"
    ```
    
## .clear
* 문자열의 내용을 모두 삭제
    ```
    s.clear();
    ```
    
## .emtpy
* 문자열이 비어있는 지 확인
    ```
    s.empty();
    ```

## .substr
* 문자열의 일부분을 문자열로 반환한다.
* ( 시작위치 ) : 시작위치부터 끝까지의 문자들을 문자열로 반환한다.
* ( 시작위치, 개수 ) : 시작위치부터 개수만큼의 문자를 문자열로 반환한다.
    ```
    string s = "ABCDEF";
    string s2 = s.substr(4);      // s2 = "EF"    ( index 4부터 끝까지의 문자열을 반환한다. )
    string s3 = s.substr(1,3);    // s3 = "BCD"   ( index 1부터 3까지의 문자열을 반환한다. )
    ```

## .strstr(char * a, char * b)
- 문자열 a에 문자열 b가 포함되어 있는지, 즉 부분 문자열인지 확인한다.
- 없다면 NULL을 리턴함
    ```cpp
    #include <bits/stdc++.h>
    using namespace std;

    char a[1000001], b[1000001];

    int main(){
        ios_base::sync_with_stdio(false);
        cin.tie(NULL);  cout.tie(NULL);

        cin >> a >> b;
        if (strstr(a, b) == NULL)   cout << 0;
        else                		cout << 1;
    }
    ```
