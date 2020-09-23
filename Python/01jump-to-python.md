# 점프 투 파이썬

> [참조 링크](https://wikidocs.net/book/1)

## 파이썬 기초

### 1-5 파이썬 둘러보기

- 변수에 값 넣고 출력하기
    ```python
    a = "Python"
    print(a);
    ```
- 조건문 if
    ```python
    a = 3
    if a > 1:
        print("a is greater than 1")
    ```
- 반복문 for
    ```python
    for a in [1, 2, 3]:
        print(a)
    ```
- 반복문 while
    ```python
    i = 0
    while i < 3:
        i = i + 1
        print(i)
    ```
- 함수
    ```python
    def add(a, b):
        return a + b
    add(3, 4)
    # 7
    ```

## 자료형

### 2-1 숫자형

- 자료형
    - 정수형
        ```python
        a = 123
        ```
    - 실수형
        ```python
        a = 12.3
        ```
    - 8진수
        ```python
        a = 0o123
        ```
    - 16진수
        ```python
        a = 0x123
        ```
- 연산
    - 사칙연산 : `+`, `-`, `/`, `*`
    - 나머지 : `%`
    - 몫 : `//`
    - 제곱 : `**`

### 2-2 문자열 자료형

- 문자열을 초기화하는 타입 
    - 큰따옴표로 양쪽 둘러싸기
    - 작은따옴표로 양쪽 둘러싸기
    - 큰따옴표 3개로 양쪽 둘러싸기
    - 작은따옴표 3개로 양쪽 둘러싸기
- Case 분류
    - 문자열 안에 작은따옴표를 포함하고 싶을 때
        ```python
        # 큰따옴표로 감싸기
        food = "Python's favorite food is perl"

        # 백슬래시 이용
        food = 'Python\'s favorite food is perl'
        ```
    - 문자열 안에 큰따옴표를 포함하고 싶을 때
        ```python
        # 작은따옴표로 감싸기
        say = '"Python is very easy." he says.'

        # 백슬래시 이용
        say = "\"Python is very easy.\" he says."
        ```
    - 여러 줄인 문자열을 변수에 대입하고 싶을 때
        - `\n` 삽입
        - `"""`, `'''` 로 둘러싸기
        ```python
        multiline1 = "Life is too short\nYou need python"
        multiline2 = """
        Life is too short
        You need python
        """
        ```
- 문자열 연산
    - Concatenation
        ```python
        head = "Python"
        tail = " is fun!"
        head + tail # Python is fun!
        ```
    - 문자열 곱하기
        ```python
        a = "python"
        a * 2 # pythonpython

        print("=" * 50)
        print("My Program")
        print("=" * 50)
        ```
    - 문자열 길이 구하기
        ```python
        a = "python"
        len(a) # 6
        ```
- 문자열 인덱싱
    - 파이썬의 string 배열은 0부터 숫자를 센다.
    ```python
    a = "Life is too short, You need Python"
    a[0]    # 'L'
    a[12]   # 's'
    a[-1]   # 'n'
    ```
- 문자열 슬라이싱
    ```python
    a = "Life is too short, You need Python"
    b = a[0] + a[1] + a[2] + a[3]   # 'Life'
    ```
    - 아래 함수를 쓸 때, 마지막 인덱스를 포함하지 않는다. `0 <= a < 4` 까지 불러오기 때문.
    ```python
    a = "Life is too short, You need Python"
    a[0:4]  # 'Life'
    a[0:3]  # 'Lif'
    a[19:]  # 'You need Python'
    a[:17]  # 'Life is too short'
    a[:]    # 'Life is too short, You need Python'
    ```
    ```python
    a = "20010331Rainy"
    date = a[:8]    # '20010331'
    weather = a[8:] # 'Rainy'
    ```
- 문자열 포매팅
    - 숫자 바로 대입
        ```python
        "I eat %d apples." % 3  # 'I eat 3 apples.'
        ```
    - 문자열 바로 대입
        ```python
        "I eat %s apples." % "five" # 'I eat five apples.'
        ```
    - 변수 대입
        ```python
        number = 10
        day = "three"
        "I ate %d apples. so I was sick for %s days." % (number, day)
        # 'I ate 10 apples. so I was sick for three days.'
        ```
    - %는 `%%`로
        ```python
        "Error is %d%%." % 98
        # 'Error is 98%.'
        ```
    - `format` 함수를 사용한 포매팅 ([링크 참조](https://wikidocs.net/13))
        ```python
        number = 10
        day = "three"
        "I ate {0} apples. so I was sick for {1} days.".format(number, day)
        # 'I ate 10 apples. so I was sick for three days.'
        ```
    - `f` 문자열 포매팅
        ```python
        name = '홍길동'
        age = 30
        f'나의 이름은 {name}입니다. 나이는 {age}입니다.'
        # '나의 이름은 홍길동입니다. 나이는 30입니다.'
        ```
- 문자열 관련 함수들
    - `count` : 문자 개수 세기
        ```python
        a = "hobby"
        a.count('b')    # 2
        ```
    - `find`, `index` : 위치 알려주기
        ```python
        a = "Python is the best choice"
        a.find('b') # 14 : 맨 처음 찾게 된 b
        a.find('k') # -1 : 없으면 1
        ```
        ```python
        a = "Life is too short"
        a.index('t')    # 8
        a.index('k')    # 오류 발생
        # Traceback (most recent call last):
        # File "<stdin>", line 1, in <module>
        # ValueError: substring not found
        ```
    - `join` : 문자열 삽입
        ```python
        ",".join('abcd')    # 'a,b,c,d' : 각각 문자 사이에 ,를 넣는 코드
        ```
    - `upper`, `lower` : 대문자, 소문자로 바꾸기
    - `lstrip`, `rstrip`, `strip` : 왼쪽 공백 / 오른쪽 공백 / 양쪽 공백 지우기
    - `replace` : 문자열 바꾸기
        ```python
        a = "Life is too short"
        a.replace("Life", "인생")   # '인생 is too short'
        ```
    - `split` : 문자열 나누기
        ```python
        a = "Life is too short"
        a.split()       # ['Life', 'is', 'too', 'short']

        b = "a:b:c:d"
        b.split(':')    # ['a', 'b', 'c', 'd']
        ```

### 2-3 리스트 자료형

- 리스트의 생김새
    ```python
    a = []
    b = [1, 2, 3]
    c = ['Life', 'is', 'too', 'short']
    d = [1, 2, 'Life', 'is']
    e = [1, 2, ['Life', 'is']]
    ```
- 리스트 인덱싱
    ```python
    a = [1, 2, 3, ['a', 'b', 'c']]
    a[0]        # 1
    a[-1]       # ['a', 'b', 'c']
    a[-1][0]    # 'a'
    a[2][2]     # 'c'

    a = [1, 2, ['a', 'b', ['Life', 'is']]]
    a[2][2][0]  # 'Life'
    ```
- 리스트 슬라이싱
    ```python
    a = [1, 2, 3, 4, 5]
    a[0:2]  # [1, 2]
    a[:2]   # [1, 2]
    a[3:]   # [4, 5]
    ```
    ```python
    a = [1, 2, 3, ['a', 'b', 'c'], 4, 5]
    a[2:5]      # [3, ['a', 'b', 'c'], 4]
    a[3][:2]    # ['a', 'b']
    ```
- 리스트 연산
    - 리스트 더하기
        ```python
        a = [1, 2, 3]
        b = [4, 5, 6]
        a + b   # [1, 2, 3, 4, 5, 6]
        ```
    - 리스트 반복하기
        ```python
        a = [1, 2, 3]
        a * 3   # [1, 2, 3, 1, 2, 3, 1, 2, 3]
        ```
    - 리스트 길이 구하기
        ```python
        a = [1, 2, 3]
        len(a)  # 3
        ```
- 리스트 수정 및 삭제
    - 리스트 수정
        ```python
        a = [1, 2, 3]
        a[2] = 4
        a   # [1, 2, 4]
        ```
    - 리스트 요소 삭제
        ```python
        a = [1, 2, 3]
        del a[1]
        a   # [1, 3]
        ```
        ```python
        a = [1, 2, 3, 4, 5]
        del a[2:]
        a   # [1, 2]
        ```
- 리스트 함수
    - `append` : 리스트에 요소 추가
        ```python
        a = [1, 2, 3]
        a.append(4)
        a   # [1, 2, 3, 4]
        ```
    - `sort` : 리스트 정렬
        ```python
        a = [1, 4, 3, 2]
        a.sort()
        a   # [1, 2, 3, 4]
        ```
    - `reverse` : 리스트 뒤집기
        ```python
        a = [1, 2, 3]
        a.reverse()
        a   # [3, 2, 1]
        ```
    - `index` : 위치 반환
        ```python
        a = [1, 2, 3]
        a.index(3)  # 2
        a.index(0)  # 오류 발생
        ```
    - `insert` : 리스트에 요소 삽입
        ```python
        a = [1, 2, 3]
        a.insert(0, 4)
        a   # [4, 1, 2, 3]
        ```
    - `remove` : 리스트 요소 제거
        ```python
        a = [1, 2, 3, 1, 2, 3]
        a.remove(3)
        a   # [1, 2, 1, 2, 3] : 첫번째 3 만 제거된다.
        ```
    - `pop` : 리스트 요소 끄집어내기
        ```python
        a = [1, 2, 3, 4, 5]
        a.pop()     # 5
        a           # [1, 2, 3, 4]
        a.pop(0)    # 1
        a           # [2, 3, 4]
        ```
    - `count` : 리스트에 포함된 요소 x의 개수 세기
        ```python
        a = [1, 2, 3, 1]
        a.count(1)  # 2
        ```

### 2-4 튜플 자료형

- 리스트는 `[ ]`으로 둘러싸지만 튜플은 `( )`으로 둘러싼다.
- 리스트는 그 값의 생성, 삭제, 수정이 가능하지만 **튜플은 그 값을 바꿀 수 없다.**

<br>

- 튜플의 형태
    ```python
    t1 = ()
    t2 = (1,)
    t3 = (1, 2, 3)
    t4 = 1, 2, 3    # 괄호를 생략해도 된다!
    t5 = ('a', 'b', ('ab', 'cd'))
    ```

### 2-5 딕셔너리 자료형

- **딕셔너리는 Key를 통해 Value를 얻는다.**
- 딕셔너리 형태
    ```python
    dic = {'name': 'glory', 'phone': '01000000000', 'birth': '1226', 'a': [1, 2, 3]}
    ```
- 딕셔너리 key-value 추가하기
    ```python
    a = {1: 'a'}
    a[2] = 'b'
    a   # {1: 'a', 2: 'b'}
    a['name'] = [1, 2, 3]
    a   # {1: 'a', 2: 'b', 'name': [1, 2, 3]}
    ```
- 딕셔너리 요소 삭제하기
    ```python
    del a[1]
    a   # {2: 'b', 'name': [1, 2, 3]}
    ```
- 딕셔너리 관련 함수
    - `keys` : Key 객체 / 리스트 만들기
        ```python
        a = {'name': 'pey', 'phone': '0119993323', 'birth': '1118'}
        a.keys()        # dict_keys(['name', 'phone', 'birth'])
        ```
        ```python
        for k in a.keys():
            print(k)
        # name
        # phone
        # birth
        ```
        ```python
        # 객체를 리스트로 변환
        list(a.keys())  # ['name', 'phone', 'birth']
        ```
    - `values` : Values 객체 / 리스트 만들기
        ```python
        a = {'name': 'pey', 'phone': '0119993323', 'birth': '1118'}
        a.values()        # dict_keys(['pey', '0119993323', '1118'])
        ```
        ```python
        for k in a.values():
            print(k)
        # pey
        # 0119993323
        # 1118
        ```
        ```python
        # 객체를 리스트로 변환
        list(a.values())  # ['pey', '0119993323', '1118']
        ```
    - `items` : Key, Value 쌍으로 얻기
        ```python
        a.items()   # dict_items([('name', 'pey'), ('phone', '0119993323'), ('birth', '1118')])
        ```
    - `clear` : Key, Value 쌍 모두 지우기

- 특이사항
    - 딕셔너리의 Key에 리스트는 사용 불가하지만, 튜플은 사용 가능하다

### 2-6 집합 자료형

- 집합은 중복을 허용하지 않고, 순서가 없다.
- 집합의 형태
    ```python
    s0 = set()
    s0  # {}

    s1 = set([1,2,3])
    s1  # {1, 2, 3}

    s2 = set("Hello")
    s2  # {'e', 'H', 'l', 'o'}
    ```
- 집합을 리스트 / 튜플로 변환하기
    ```python
    s1 = set([1,2,3])
    l1 = list(s1)
    l1  # [1, 2, 3]

    t1 = tuple(s1)
    t1  # (1, 2, 3)
    ```
- 교집합 / 차집합 / 합집합 구하기
    - `&` : 교집합
    - `|` : 합집합
    - `-` : 차집합
- 값 추가 / 제거하기
    - `add` :  값 1개 추가하기
        ```python
        s1 = set([1, 2, 3])
        s1.add(4)
        s1  # {1, 2, 3, 4}
        ```
    - `update` : 값 여러 개 추가하기
        ```python
        s1 = set([1, 2, 3])
        s1.update([4, 5, 6])
        s1  # {1, 2, 3, 4, 5, 6}
        ```
    - `remove` : 특정 값 제거하기
        ```python
        s1 = set([1, 2, 3])
        s1.remove(2)
        s1  # {1, 3}

### 2-7 불 자료형

```python
a = True
b = False
```
- 자료형의 참과 거짓
    - 숫자 0, None, 문자열, 리스트, 튜플, 딕셔너리, 집합이 비어있으면 거짓이다.

### 2-8 변수

- 파이썬에서의 `=`는 주소값을 공유하므로, 
    ```python
    a = [1, 2, 3]
    b = a
    ```
    와 같은 코드가 있으면, `a`와 `b`는 같은 `[1, 2, 3]`을 가리키게 된다.
- 리스트를 복사하고자 할 때
    - `[:]`
        ```python
        a = [1, 2, 3]
        b = a[:]
        ```
    - `copy`
        ```python
        from copy import copy
        b = copy(a)
        ```

## 제어문

### 3-1 if문

- 기본 형태
    ```python
    money = 2000
    if money >= 3000:
        print("택시를 타고 가라")
    else:
        print("걸어가라")

    # 걸어가라
    ```
- `and`, `or`, `not`
    ```python
    money = 2000
    card = True
    if money >= 3000 or card:
        print("택시를 타고 가라")
    else:
        print("걸어가라")

    # 택시를 타고 가라
    ```
- `x in s`, `x not in s` (x는 요소, s는 리스트, 튜플, 문자열)
    ```python
    1 in [1, 2, 3]
    # True
    
    1 not in [1, 2, 3]
    # False
    ```
- `pass`
    - 해당 조건문에서 아무 일을 수행하지 않는다.
        ```python
        pocket = ['paper', 'money', 'cellphone']
        if 'money' in pocket:
            pass 
        else:
            print("카드를 꺼내라")
        ```
- `elif`
    ```python
    pocket = ['paper', 'cellphone']
    card = True
    if 'money' in pocket:
         print("택시를 타고가라")
    elif card: 
         print("택시를 타고가라")
    else:
         print("걸어가라")

    # 택시를 타고가라
    ```
- 조건부 표현식
    ```python
    # 조건문이 참인 경우 if 조건문 else 조건문이 거짓인 경우

    score = 70
    message = "success" if score >= 60 else "failure"
    # success
    ```

### 3-2 while문

- 기본 `while`
    ```python
    treeHit = 0
    while treeHit < 10:
        treeHit = treeHit +1
        print("나무를 %d번 찍었습니다." % treeHit)
        if treeHit == 10:
            print("나무 넘어갑니다.")
    ```

- `break`
    ```python
    coffee = 10
    while True:
        money = int(input("돈을 넣어 주세요: "))
        if money == 300:
            print("커피를 줍니다.")
            coffee = coffee - 1
        elif money > 300:
            print("거스름돈 %d를 주고 커피를 줍니다." % (money - 300))
            coffee = coffee - 1
        else:
            print("돈을 다시 돌려주고 커피를 주지 않습니다.")
            print("남은 커피의 양은 %d개 입니다." % coffee)
        if coffee == 0:
            print("커피가 다 떨어졌습니다. 판매를 중지 합니다.")
            break
    ```

- `continue`

### 3-3 for문

- for문의 형태
    ```python
    test_list = ['one', 'two', 'three'] 
    for i in test_list: 
        print(i)
    
    # one 
    # two 
    # three
    ```
    ```python
    a = [(1, 2), (3, 4), (5, 6)]
    for (first, last) in a:
        print(first + last)
    
    # 3
    # 7
    # 11
    ```
- `range(시작 숫자, 끝 숫자)`
    - 숫자 리스트를 자동으로 만들어주는 함수
    - 끝 숫자는 포함되지 않는다.
    ```python
    add = 0 
    for i in range(1, 11): 
        add = add + i 
    
    print(add)  # 55
    ```
    - 다음 예시와 같이 배열의 인덱스가 필요할 때 사용한다.
        ```python
        marks = [90, 25, 67, 45, 80]
        for number in range(len(marks)):
            if marks[number] < 60: 
                continue
            print("%d번 학생 축하합니다. 합격입니다." % (number + 1))
        ```
- 리스트 내포
    ```python
    a = [1, 2, 3, 4]
    result = []

    for num in a:
        result.append(num * 3)

    print(result)   # [3, 6, 9, 12]
    ```
    ```python
    a = [1, 2, 3, 4]
    result = [num * 3 for num in a]

    print(result)   # [3, 6, 9, 12]
    ```

## 파이썬 입출력

### 4-1 함수

- 함수의 기본 구조
    ```python
    def add(a, b): 
        return a + b

    print(add(3, 4))            # 7
    print(add(a = 3, b = 7))    # 10
    print(add(b = 7, a = 3))    # 10
    ```
- 함수의 파라미터 갯수가 따로 정해지지 않았을 떄
    ```python
    # *args : 입력값을 전부 모아서 튜플 형태로 만들어냄.
    def add_many(*args): 
        result = 0 
        for i in args: 
            result = result + i 
        return result

    print(add_many(1, 2, 3, 4, 5, 6, 7, 8, 9, 10))    # 55
    ```

    ```python
    def add_mul(choice, *args): 
        if choice == "add": 
            result = 0 
            for i in args: 
                result = result + i 
        elif choice == "mul": 
            result = 1 
            for i in args: 
                result = result * i 
        return result

    print(add_mul("mul", 1, 2, 3, 4))   # 24
    print(add_mul("add", 1, 2, 3, 4))   # 10
    ```
- `kwargs` : 키워드 파라미터, 입력값을 전부 모아서 딕셔너리 형태로 만들어냄
    ```python
    def print_kwargs(**kwargs):
        print(kwargs)
    
    print_kwargs(a = 1)                 # {'a': 1}
    print_kwargs(name = 'foo', age = 3) # {'age': 3, 'name': 'foo'}
    ```
- 반환값은 늘 한 개지만, 여러 요소들은 튜플로 반환된다.
    ```python
    def add_and_mul(a,b): 
        return a+b, a*b

    result = add_and_mul(3, 4)              # result = (7, 12)
    add_res, mul_res = add_and_mul(4, 5)    # add_res = 9, mul_res = 20
    ```
- 파라미터의 초깃값 미리 설정하기
    - 단, 초깃값이 설정된 파라미터는 맨 뒤부터 배치해야 한다.
        ```python
        #   say_myself(name, man = True, old): 오류 발생
        def say_myself(name, old, man = True): 
            print("나의 이름은 %s 입니다." % name) 
            print("나이는 %d살입니다." % old) 
            if man: 
                print("남자입니다.")
            else: 
                print("여자입니다.")
        ```
- 함수 안에서 함수 밖의 변수를 변경하는 방법
    1. `return`
        ```python
        a = 1 
        def vartest(a): 
            a = a + 1 
            return a

        a = vartest(a)
        ```
    2. `global`
        ```python
        a = 1 
        def vartest(): 
            global a 
            a = a+1

        vartest()
        ```
- `lambda`
    ```python
    add = lambda a, b: a + b
    result = add(3, 4)
    print(result)   # 7
    ```

### 4-2 사용자 입출력

#### 사용자 입력

- `input`
    ```python
    a = input() # 콘솔에 Life is too short, you need python 입력
    print(a)    # 콘솔에 Life is too short, you need python 출력
    ```
    ```python
    number = input("숫자를 입력하세요: ")
    # 콘솔에 숫자를 입력하세요: 출력. 그리고 숫자 하나를 입력하면 number에 저장됨
    ```

#### 사용자 출력

- `print`  
    ```python
    print("life" "is" "too short")      # lifeistoo short
    print("life" + "is" + "too short")  # lifeistoo short
    print("life", "is", "too short")    # life is too short
    ```
- 한 줄에 결과값 출력하기
    ```python
    for i in range(10):
        print(i, end=' ')

    # 0 1 2 3 4 5 6 7 8 9
    ```

### 4-3 파일 읽고 쓰기

- 파일 생성하기
    ```python
    f = open("새파일.txt", 'w')
    # f = open("C:/doit/새파일.txt", 'w')
    # do your job
    f.close()
    ```
    - 파일 열기 모드
        - `r` : 읽기 모드
        - `w` : 쓰기 모드 (기존에 파일이 존재한다면, 모두 지워버림)
        - `a` : 추가 모드
- 파일을 쓰기 모드로 열어 출력값 적기
    ```python
    f = open("C:/새파일.txt", 'w')
    for i in range(1, 11):
        data = "%d번째 줄입니다.\n" % i
        f.write(data)
    f.close()
    ```
- 프로그램의 외부에 저장된 파일을 읽는 방법
    1. `readline()`
        ```python
        # 한 줄만 읽기
        f = open("C:/새파일.txt", 'r')
        line = f.readline()
        print(line)
        f.close()
        ```
        ```python
        # 파일 전부 읽기
        f = open("C:/새파일.txt", 'r')
        while True:
            line = f.readline()
            if not line: break
            print(line)
        f.close()
        ```
    2. `readlines` : 읽어온 파일의 각각 줄이 한 요소가 되어 리스트를 만들어 주는 함수
        ```python
        f = open("C:/새파일.txt", 'r')
        lines = f.readlines()   # lines = ["1번째 줄입니다.", "2번째 줄입니다.", ...]
        for line in lines:
            print(line)
        f.close()
        ```
    3. `read` : 파일의 전체 내용을 문자열로 바꿔줌
        ```python
        f = open("C:/새파일.txt", 'r')
        data = f.read()
        print(data)
        f.close()
        ```
- 파일에 새로운 내용 추가하기
    ```python
    f = open("C:/doit/새파일.txt",'a')
    for i in range(11, 20):
        data = "%d번째 줄입니다.\n" % i
        f.write(data)
    f.close()
    ```
- 파일 자동으로 `close` 해주기
    ```python
    # f = open("foo.txt", 'w')
    # f.write("Life is too short, you need python")
    # f.close()

    with open("foo.txt", "w") as f:
        f.write("Life is too short, you need python")
        # 자동으로 f.close()
    ```

## 파이썬 확장

### 5-1 클래스

- 클래스 예시 : 계산기
    ```python
    class Calculator:
        def __init__(self):
            self.result = 0

        def add(self, num):
            self.result += num
            return self.result

        def sub(self, num):
            self.result -= num
            return self.result

    cal1 = Calculator()
    cal2 = Calculator()

    print(cal1.add(3))
    print(cal1.add(4))
    # 3 7

    print(cal2.add(3))
    print(cal2.add(7))
    # 3 10
    ```
- 클래스 예시 : +, -, /, *
    ```python
    class FourCal:
        def setdata(self, first, second):
            self.first = first
            self.second = second
        def add(self):
            result = self.first + self.second
            return result
        def mul(self):
            result = self.first * self.second
            return result
        def sub(self):
            result = self.first - self.second
            return result
        def div(self):
            result = self.first / self.second
            return result

    a = FourCal()
    b = FourCal()
    a.setdata(4, 2)
    b.setdata(3, 8)
    a.add()    # 6
    a.mul()    # 8
    a.sub()    # 2
    a.div()    # 2
    b.add()    # 11
    b.mul()    # 24
    b.sub()    # -5
    b.div()    # 0.375
    ```
- `__init__` : 생성자
    ```python
    class FourCal:
        def __init__(self, first, second):
            self.first = first
            self.second = second

        # ...

    a = FourCal(4, 2)
    a.add()    # 6
    a.mul()    # 8
    a.sub()    # 2
    a.div()    # 2
    ```
- 클래스의 상속
    ```python
    # 기존 FourCal 클래스에서 pow 계산 함수를 추가
    class MoreFourCal(FourCal):
        def pow(self):
            result = self.first ** self.second
            return result
    ```
- 메서드 오버라이딩
    ```python
    class SafeFourCal(FourCal):
        def div(self):
            if self.second == 0:
                return 0
            else:
                return self.first / self.second
    ```
- 클래스 변수와 객체 변수
    - 객체 변수는 다른 객체들에 영향받지 않고 독립적으로 값을 유지한다.
    - 클래스 변수는 같은 클래스로 선언된 객체들끼리 값을 공유한다.
        ```python
        class Family:
            lastname = "김"
        ```

### 5-2 모듈

- 모듈 
    - 함수나 변수 또는 클래스를 모아 놓은 파일
    - 다른 파이썬 프로그램에서 불러와 사용할 수 있게끔 만든 파이썬 파일
- 모듈 만들기
    ```python
    # mod1.py
    def add(a, b):
        return a + b

    def sub(a, b): 
        return a - b
    ```
- 모듈 불러오기
    ```python
    import mod1             # 파일 확장자는 생략한다.

    print(mod1.add(3, 4))   # 7
    print(mod1.sub(4, 2))   # 2
    ```
    ```python
    from mod1 import *      # 모듈 이름 생략 가능

    add(3, 4)               # 7
    sub(4, 2)               # 2
    ```
- 클래스나 변수 등을 포함한 모듈
    ```python
    # mod2.py 
    PI = 3.141592

    class Math: 
        def solv(self, r): 
            return PI * (r ** 2) 

        def add(a, b): 
            return a+b 
    ```
    ```python
    import mod2
    print(mod2.PI)      # 3.141592

    a = mod2.Math()
    print(a.solve(2))   # 12.566368
    ```
- 다른 디렉토리에서 모듈 불러오기 ([링크](https://wikidocs.net/29#_4))

### 5-3 패키지

> [참고 링크](https://wikidocs.net/1418)

- 패키지
    - 도트(.)를 사용하여 파이썬 모듈을 계층적(디렉터리 구조)으로 관리할 수 있게 해준다

### 5-4 예외 처리

- 오류 발생 원인
    - `FileNotFoundError` : 없는 파일을 열려고 시도
    - `ZeroDivisionError` : 0으로 나누려고 함
    - `IndexError` : 리스트에서 없는 인덱스에 접근함
- 오류 예외 처리 기법
    1. `try`, `except`
        1. `try`, `except` 만 사용
            ```
            try:
                ...
            except:
                ...
            ```
        2. 발생 오류만 포함한 `except`
            ```
            try:
                ...
            except 발생 오류:
                ...
            ```
        3. 발생 오류와 오류 메시지 변수까지 포함한 `except`
            ```
            try:
                ...
            except 발생 오류 as 오류 메시지 변수:
                ...
            ```
            ```python
            try:
                4 / 0
            except ZeroDivisionError as e:
                print(e)    
            # devision by zero
            ```
    2. `try` ... `finally`
        - finally절은 try문 수행 도중 예외 발생 여부에 상관없이 항상 수행된다. 보통 finally절은 사용한 리소스를 close해야 할 때에 많이 사용한다.
            ```python
            f = open('foo.txt', 'w')
            try:
                # 무언가를 수행한다.
            finally:
                f.close()
            ```
    3. 다중 오류 처리
        ```python
        try:
            a = [1,2]
            print(a[3])
            4/0
        except ZeroDivisionError:
            print("0으로 나눌 수 없습니다.")
        except IndexError:
            print("인덱싱 할 수 없습니다.")
        ```
        ```python
        try:
            a = [1,2]
            print(a[3])
            4/0
        except (ZeroDivisionError, IndexError) as e:
            print(e)
        ```
- 오류 회피하기
    ```python
    try:
        f = open("나없는파일", 'r')
    except FileNotFoundError:
        pass
    ```
- 오류 강제로 발생시키기
    ```python
    class Bird:
        def fly(self):
            raise NotImplementedError
    ```
- 예외 만들기 ([링크](https://wikidocs.net/30#_6))

### 5-5 내장 함수

- `abs` : 절댓값 반환
- `all` : 반복 가능한 자료형 x를 입력 인수로 받으며 이 x의 요소가 모두 참이면 True, 하나라도 거짓이 있으면 False를 반환
    ```python
    all([1, 2, 3, 0])   # False
    all([])             # True
    ```
- `any` : 반복 가능한 자료형 x를 입력 인수로 받으며 이 x의 요소가 하나라도 참이 있으면 True, 모두 거짓이면 False를 반환
    ```python
    any([0, ""])        # False
    any([])             # False
    ```
- `chr` : 아스키 코드 값을 입력받아 그 코드에 해당하는 문자를 출력하는 함수
    ```python
    chr(48)     # '0'
    ```
- `rod` : 문자의 아스키 코드 값을 출력하는 함수
    ```python
    ord('0')    # 48
    ```
- `dir` : 객체가 자체적으로 가지고 있는 변수나 함수를 보여줌.
    ```python
    dir([1, 2, 3])  # ['append', 'count', 'extend', 'index', 'insert', 'pop',...]
    dir({'1':'a'})  # ['clear', 'copy', 'get', 'has_key', 'items', 'keys',...]
    ```
- `divmod` : 2개의 숫자를 입력으로 받아 a를 b로 나눈 몫과 나머지를 튜플 형태로 반환
    ```python
    divmod(7, 3)    # (2, 1)
    ```
- `enumerate` : 순서가 있는 자료형을 입력으로 받아 인덱스 값을 포함하는 `enumerate` 객체를 반환
    ```python
    for i, name in enumerate(['body', 'foo', 'bar']):
        print(i, name)
    
    # 0 body
    # 1 foo
    # 2 bar
    ```
- `eval` : 실행 가능한 문자열을 입력받아 문자열을 실행한 결과값으로 반환
    ```python
    eval('1+2')             # 3
    eval("'hi' + 'a'")      # 'hia'
    eval('divmod(4, 3)')    # (1, 1)
    ```
- `filter` : 첫 번째 인수로 함수 이름을, 두 번째 인수로 그 함수에 차례로 들어갈 반복 가능한 자료형을 받는다. 그리고 두 번째 인수인 반복 가능한 자료형 요소가 첫 번째 인수인 함수에 입력되었을 때 반환 값이 참인 것만 묶어서 돌려준다.
    ```python
    def positive(x):
        return x > 0

    print(list(filter(positive, [1, -3, 2, 0, -5, 6]))) # [1, 2, 6]
    ```
- `hex` : 정수를 16진수 문자열로 변환해줌
- `oct` : 정수를 8진수 문자열로 변환해줌
- `id` : 객체를 입력받아 객체 고유 주소값을 반환
- `input` : 사용자의 입력을 받는 함수
- `int` : 문자열 형태의 숫자나 소수점이 있는 숫자를 정수 형태로 변환
    - 2진수나 16진수의 수를 10진수로 변환해주기도 함
        ```python
        int('11', 2)    # 3
        int('1A', 16)   # 26
        ```
- `isinstance` : 첫번째 인수로 인스턴스, 두번째 인수로 클래스 이름을 받는다. 입력받은 인스턴스가 그 클래스의 인스턴스인지를 판단해 참/거짓을 반환한다.
    ```python
    class Person: pass
    ...
    a = Person()
    isinstance(a, Person)   # True
    ```
- `list` : 반복 가능한 자료형을 입력받아 리스트로 만들어 반환
    ```python
    list("python")  # ['p', 'y', 't', 'h', 'o', 'n']
    list((1,2,3))   # [1, 2, 3]
    ```
- `map` : `map(f, iterable)`은 함수(`f`)와 반복 가능한자료형을 입력으로 받는다. `map`은 입력받은 자료형의 각 요소를 함수 `f`가 수행한 결과를 묶어서 돌려주는 함수이다.
    ```python
    def two_times(numberList):
        result = [ ]
        for number in numberList:
            result.append(number * 2)
        return result

    result = two_times([1, 2, 3, 4])
    print(result)   # [2, 4, 6, 8]
    ```
    ```python
    def two_times(x): 
        return x * 2

    list(map(two_times, [1, 2, 3, 4]))  # [2, 4, 6, 8]
    ```
    ```python
    list(map(lambda a: a * 2, [1, 2, 3, 4]))  # [2, 4, 6, 8]
    ```
- `max` : 반복 가능한 자료형을 입력받아 그 최댓값을 돌려주는 함수
- `max` : 반복 가능한 자료형을 입력받아 그 최솟값을 돌려주는 함수
- `pow(x, y)` : x의 y 제곱한 결과를 반환
- `range`
    - 인수가 하나일 경우
        ```python
        list(range(5))          # [0, 1, 2, 3, 4]
        ```
    - 인수가 2개일 경우
        ```python
        list(range(5, 10))      # [5, 6, 7, 8, 9]
        ```
    - 인수가 3개일 경우
        ```python
        list(range(1, 10, 2))   # [1, 3, 5, 7, 9]
        list(range(0, -10, -1)) # [0, -1, -2, -3, -4, -5, -6, -7, -8, -9]
- `round` : 숫자를 입력받아 반올림해 주는 함수
    ```python
    round(4.2)      # 4
    round(5.678, 2) # 5.68
    ```
- `sorted` : 입력값을 정렬한 후 그 결과를 리스트로 돌려주는 함수
    ```python
    sorted("zero")      # ['e', 'o', 'r', 'z']
    sorted((3, 2, 1))   # [1, 2, 3]
    ```
- `str` : 문자열 형태로 객체를 변환
- `sum` : 입력받은 리스트의 모든 요소 합을 반환
- `tuple` : 반복 가능한 자료형을 입력받아 튜플 형태로 반환
- `type` : 입력값의 자료형이 무엇인지 반환
- `zip` : 동일한 개수로 이루어진 자료형을 묶어주는 역할을 하는 함수
    ```python
    list(zip([1, 2, 3], [4, 5, 6]))             # [(1, 4), (2, 5), (3, 6)]
    list(zip([1, 2, 3], [4, 5, 6], [7, 8, 9]))  # [(1, 4, 7), (2, 5, 8), (3, 6, 9)]
    list(zip("abc", "def"))                     # [('a', 'd'), ('b', 'e'), ('c', 'f')]
    ```

### 5-6 라이브러리

 - [링크](https://wikidocs.net/33)

 #### threading 모듈

- 컴퓨터에서 동작하고 있는 프로그램을 프로세스(Process)라고 한다. 보통 1개의 프로세스는 한 가지 일만 하지만 스레드(Thread)를 사용하면 한 프로세스 안에서 2가지 또는 그 이상의 일을 동시에 수행할 수 있다.

```python
# thread_test.py
import time

def long_task():  # 5초의 시간이 걸리는 함수
    for i in range(5):
        time.sleep(1)  # 1초간 대기한다.
        print("working:%s\n" % i)

print("Start")

for i in range(5):  # long_task를 5회 수행한다.
    long_task()

print("End")
```
- `long_task` 함수는 수행하는 데 5초의 시간이 걸리는 함수이다. 위 프로그램은 이 함수를 총 5번 반복해서 수행하는 프로그램이다. 이 프로그램은 5초가 5번 반복되니 총 25초의 시간이 걸린다.
- 하지만 앞에서 설명했듯이 스레드를 사용하면 5초의 시간이 걸리는 `long_task` 함수를 동시에 실행할 수 있으니 시간을 줄일 수 있다.

```python
# thread_test.py
import time
import threading  # 스레드를 생성하기 위해서는 threading 모듈이 필요하다.

def long_task():
    for i in range(5):
        time.sleep(1)
        print("working:%s\n" % i)

print("Start")

threads = []
for i in range(5):
    t = threading.Thread(target=long_task)  # 스레드를 생성한다.
    threads.append(t) 

for t in threads:
    t.start()  # 스레드를 실행한다.

print("End")
```

- 이와 같이 프로그램을 수정하고 실행해 보면 25초 걸리던 작업이 5초 정도에 수행되는 것을 확인할 수 있다. `threading.Thread`를 사용하여 만든 **스레드 객체가 동시 작업을 가능하게 해 주기 때문이다.**
- 하지만 위 프로그램을 실행해 보면 "Start"와 "End"가 먼저 출력되고 그 이후에 스레드의 결과가 출력되는 것을 확인할 수 있다. 그리고 프로그램이 정상 종료되지 않는다. 우리가 기대하는 것은 "Start"가 출력되고 그다음에 스레드의 결과가 출력된 후 마지막으로 "End"가 출력되는 것이다.

```python
# thread_test.py
import time
import threading

def long_task():
    for i in range(5):
        time.sleep(1)
        print("working:%s\n" % i)

print("Start")

threads = []
for i in range(5):
    t = threading.Thread(target=long_task)
    threads.append(t)

for t in threads:
    t.start()

for t in threads:
    t.join()  # join으로 스레드가 종료될 때까지 기다린다.

print("End")
```

- 스레드의 join 함수는 해당 스레드가 종료될 때까지 기다리게 한다. 따라서 위와 같이 수정하면 우리가 원하던 출력을 보게 된다.