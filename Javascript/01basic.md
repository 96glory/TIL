# JavaScript 기본 문법

## 변수 선언

- 상수
  ```javascript
  const a = '스트링1';
  ```
  
- 변수
  ```javascript
  let b1 = '스트링1';
  var b2 = '스트링2';
  ```
  - let과 var의 차이
    - **let은 변수 재선언이 되지 않는다. 하지만 var는 변수 재선언이 가능하다.**
      ```javascript
      let name = 'aaa';
      let name = 'bbb';  // Uncaught SyntaxError: Identifier 'name' has already been declared
      ```
    - Hoisting
      - var 선언문이나 function 선언문 등을 해당 스코프의 선두로 옮긴 것처럼 동작하는 특성
      - JS는 ES6에서 도입된 let, const를 포함하여 모든 선언(var, let, const, function, function*, class)를 호이스팅한다.
      - 하지만, ```var```로 선언된 변수와는 달리 ```let```으로 선언된 변수를 선언문 이전에 참조하면 ReferenceError가 발생한다.
        ```javascript
        console.log(foo); // undefined
        var foo;
        
        console.log(bar); // Error: Uncaught ReferenceError: bar is not defined
        let bar;
        ```
        변수는 선언 단계 -> 초기화 단계 -> 할당 단계에 걸쳐 생성되는데
        var로 선언된 변수는 선언 단계와 초기화 단계가 한번에 이루어진다.
        하지만, let으로 선언된 변수는 선언 단계와 초기화 단계가 분리되어 진행된다.
        
## 함수

- ES6의 템플릿 리터럴 사용하기
  - ' (Single quote) 대신에 ~와 같이 적혀진 ` (Grave accent)를 사용
  ```javascript
  function hello(name) {
    console.log(`Hello, ${name}!`); // not 'Hello, ' + name
  }
  
  hello(glory);
  ```
- 화살표 함수
  - 화살표 함수의 this는 자신이 속한 객체를 가리키지 않는다. function으로 선언한 함수의 this는 자신이 속한 객체를 가리킨다.
  ```javascript
  const add = (a, b) => {
    return a + b;
  };
  ```
  ```javascript
  const add = (a, b) => a+ b;
  ```

## 객체

- 선언
  ```javascript
  const dog = {
    name : '멍멍이',
    'age with' : 2
  };
  
  console.log(dog.name);
  console.log(dog.'age with');
  ```

- 함수에서 객체를 파라미터로 받기
  - case 1
    ```javascript
    const ironMan = {
      name: '토니 스타크',
      actor: '로버트 다우니 주니어',
      alias: '아이언맨'
    };

    const captainAmerica = {
      name: '스티븐 로저스',
      actor: '크리스 에반스',
      alias: '캡틴 아메리카'
    };

    function print(hero) {
      const text = `${hero.alias}(${hero.name}) 역할을 맡은 배우는 ${hero.actor} 입니다.`;
      console.log(text);
    }

    print(ironMan); // 아이언맨(토니 스타크) 역할을 맡은 배우는 로버트 다우니 주니어 입니다.
    print(captainAmerica); // 캡틴 아메리카(스티븐 로저스) 역할을 맡은 배우는 크리스 에반스 입니다.
    ```
  - case 2 with 객체 비구조화 할당
    ```javascript
    function print(hero) {
      const { alias, name, actor } = hero; // 객체에서 값들을 추출해서 새로운 상수로 선언
      const text = `${alias}(${name}) 역할을 맡은 배우는 ${actor} 입니다.`;
      console.log(text);
    }
    ```
  - case 3 with 객체 비구조화 할당
    ```javascript
    function print({ alias, name, actor }) {
      const text = `${alias}(${name}) 역할을 맡은 배우는 ${actor} 입니다.`;
      console.log(text);
    }
    ```

- 객체 안에 함수 넣기
  ```
  const dog = {
    name: '멍멍이',
    sound: '멍멍!',
    say: function say() { // say() 는 생략이 가능하다.
      console.log(this.sound);
    }
  };

  dog.say();
  ```

- Getter와 Setter 를 활용하여 객체 활용하기. ![왜 Getter/Setter를 쓰는가? LINK](https://mygumi.tistory.com/161)
  ```javascript
  const numbers = {
    _a : 1, _b : 2, sum : 3,
    calculate() {
      console.log('calculate');
      this.sum = this._a + this._b;
    },
    get a() {
      return this._a;
    }.
    get b() {
      return this._b;
    },
    set a(value) {
      console.log('set a');
      this._a = value;
      this.calculate();
    },
    set b(value) {
      console.log('set b');
      this._b = value;
      this.calculate();
    }    
  };
  
  console.log(numbers.sum);
  numbers.a = 5;
  numbers.b = 7;
  numbers.a = 9;
  console.log(numbers.b);
  console.log(numbers.a);
  console.log(numbers.sum);
  ```
  
## 배열

- 선언
  ```javascript
  // 숫자 배열
  const arr = [1, 2, 3, 4, 5];
  
  // 객체 배열
  const zoo = [{ name : 'dog'}, {name : 'cat'}];
  ```
- push
  ```javascript
  zoo.push({
    name : 'rabbit'
  });
  ```
- length
  ```javascript
  console.log(zoo.length());
  ```
- forEach
  ```javascript
  const superheroes = ['아이언맨', '캡틴 아메리카', '토르', '닥터 스트레인지'];

  for (let i = 0; i < superheroes.length; i++) {
    console.log(superheroes[i]);
  }
  
  // 아래와 같이 변환 가능
  superheroes.forEach(hero => {
    console.log(hero);
  });
  ```
- map
  - map의 파라미터로는 변화함수를 넣는다.
  - array.map 함수를 사용할 때 변화함수를 사용하여 새로운 배열을 반환해준다.
  ```javascript
  const array = [1, 2, 3, 4, 5, 6, 7, 8];

  const square = n => n * n;
  const squared = array.map(square);
  console.log(squared);
  
  // 변화함수 직접 넣기
  const newSquared = array.map(n => n * n);
  console.log(newSquared);
  ```

- indexOf
  - 배열의 값이 숫자, 문자열, 또는 불이라면 원하는 항목이 몇번째 원소인지 찾아주는 함수
  ```javascript
  const superheroes = ['아이언맨', '캡틴 아메리카', '토르', '닥터 스트레인지'];
  const index = superheroes.indexOf('토르');
  console.log(index);
  ```
- findIndex
  - 배열 안에 있는 값이 객체나 배열일 때 `findIndex`를 사용. 해당하는 Index 값을 반환
  ```
  const todos = [
    {id: 1, text: '자바스크립트 입문', done: true},
    {id: 2, text: '함수 배우기', done: true},
    {id: 3, text: '객체와 배열 배우기', done: true},
    {id: 4, text: '배열 내장함수 배우기', done: false}
  ];

  const index = todos.findIndex(todo => todo.id === 3);
  console.log(index); // 2
  ```
- find
  - `findIndex`와 유사하나, 찾아낸 값 자체를 반환한다.
  ```javascript
  const todo = todos.find(todo => todo.id === 3);
  console.log(todo); // {id: 3, text: "객체와 배열 배우기", done: true}
  ```

- filter
  - 배열에서 특정 조건을 만족하는 값들만 따로 추출하여 새로운 배열을 만든다.
  ```javascript
  const tasksNotDone = todos.filter(todo => todo.done === false);
  console.log(tasksNotDone);
  
  const simpleTasksNotDone = todos.filter(todo => !todo.done);
  console.log(simpleTasksNotDone);
  ```

- splice
  - 배열에서 특정 항목을 제거할 때 사용
  - 파라미터로 
    - 첫번째 : 어떤 인덱스부터 지울 지
    - 두번째 : 그 인덱스부터 몇 개를 지울 지
  ```javascript
  const numbers = [10, 20, 30, 40];
  const index = numbers.indexOf(30);
  numbers.splice(index, 1);
  console.log(numbers);
  ```
  
- slice
  - 배열을 잘라낼 때 사용. 기존 배열을 건드리지 않음.
  ```javascript
  const numbers = [10, 20, 30, 40];
  const sliced = numbers.slice(0, 2); // 0부터 시작해서 2전까지

  console.log(sliced); // [10, 20]
  console.log(numbers); // [10, 20, 30, 40]
  ```
- shift == `vector.pop_front();`
- pop == `vector.pop_back();`
- unshift == `vector.push_front(var);`
- concat
  - 두 배열을 합침.
  ```javascript
  const arr1 = [1, 2, 3];
  const arr2 = [4, 5, 6];
  const concated = arr1.concat(arr2);

  console.log(concated);
  ```
- reduce ![LINK](https://han.gl/GQQ1f)

## 반복문
- for...of 문
  ```javascript
  let nums = [10, 20, 30, 40, 50];
  for (let num of nums) {
    console.log(num);
  }
  ```
- for...in 문
  ```javascript
  const zoo = [{ name : 'dog'}, {name : 'cat'}, {name : 'rabbit'}];
  
  console.log(Object.entries(zoo)); // [[키, 값], [키, 값]] 형태의 배열로 변환
  console.log(Object.keys(zoo));    // [키, 키, 키] 형태의 배열로 변환
  console.log(Object.values(zoo));  // [값, 값, 값] 형태의 배열로 변환
  
  for (let key in zoo) {
    console.log(`${key}: ${zoo[key]}`);
  }
  ```
