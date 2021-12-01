# JS Promise와 async, await

## 1. 콜백

- `setTimeout(function() { // Code here }, delay);`
  - 일정 시간 후에 특정 코드, 함수를 의도적으로 지연한 뒤 실행하고 싶을 때 사용하는 함수
- callback-based 비동기 프로그래밍

  - 특정 함수가 끝난 뒤에 특정 함수를 실행하여야 할 때, 즉, 함수의 순서를 보장해야 할 때 콜백 함수를 활용한다.

    ```javascript
    function loadScript(src, callback) {
      let script = document.createElement("script");
      script.src = src;

      script.onload = () => callback(script);

      document.head.append(script);
    }

    // https://cdnjs.cloudflare.com/ajax/libs/lodash.js/3.2.0/lodash.js
    loadScript("./lodash.js", (script) => {
      alert(`${script.src}가 로드되었습니다.`);
      alert(_); // 스크립트에 정의된 함수
    });
    ```

- error handling

  - 스크립트 로딩이 실패하면, `callback(error)`를 호출하고, 스크립트 로딩에 성공하면 `callback(null, script)`를 호출한다.

    ```javascript
    function loadScript(src, callback) {
      let script = document.createElement("script");
      script.src = src;

      script.onload = () => callback(null, script);
      script.onerror = () =>
        callback(new Error(`${src}를 불러오는 도중에 에러가 발생했습니다.`));

      document.head.append(script);
    }

    loadScript("/my/script.js", function (error, script) {
      if (error) {
        // 에러 처리
      } else {
        // 스크립트 로딩이 성공적으로 끝남
      }
    });
    ```

- 콜백의 콜백의 콜백의 콜백을 사용해야할 경우, promise 방식을 채택하자.

## 2. Promise

```javascript
let promise = new Promise(function (resolve, reject) {
  // executor
});
```

- 객체 `Promise`
  - 자바스크립트 비동기 처리에 사용되는 객체
  - `executor` : 객체 `Promise`에 전달되는 함수
  - `resolve` & `reject`
    - `resolve(value)` : executor가 성공적으로 끝난 경우, 그 결과를 나타내는 `value`와 함께 호출
    - `reject(error)` : executor 실행 중 에러 발생 시, 에러 객체를 나타내는 `error`와 함께 호출
    - `executor`는 반드시 `resolve`나 `reject` 중 하나를 호출하게 된다.
- Promise의 state

  - Pending : executor가 실행되기 전이나 수행 중인 경우
  - Fulfilled : executor가 완료되어 `resolve`가 실행된 경우
  - Rejected : executor가 실패하거나 `reject`가 실행된 경우

- Promise의 후처리
  - `then`
    - `.then`의 첫 번째 인수는 Fulfilled되었을 때 실행되는 함수이고, 여기서 실행 결과를 받음
    - `.then`의 두 번째 인수는 Rejected되었을 때 실행되는 함수이고, 여기서 에러를 받음.
  - `catch`
    - 에러 처리
  - `finally`
    - Promise가 처리되고, `then` 및 `catch`가 모두 수행된 후에 실행됨.

```javascript
function loadScript(src) {
  return new Promise(function (resolve, reject) {
    let script = document.createElement("script");
    script.src = src;

    script.onload = () => resolve(script);
    script.onerror = () =>
      reject(new Error(`${src}를 불러오는 도중에 에러가 발생함`));

    document.head.append(script);
  });
}

let promise = loadScript(
  "https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.11/lodash.js"
);

promise.then(
  (script) => alert(`${script.src}을 불러왔습니다!`),
  (error) => alert(`Error: ${error.message}`)
);

promise.then((script) => alert("또다른 핸들러..."));
```

```javascript
function increaseAndPrint(n) {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      const value = n + 1;
      if (value === 5) {
        const error = new Error();
        error.name = "ValueIsFiveError";
        reject(error);
        return;
      }
      console.log(value);
      resolve(value);
    }, 1000);
  });
}

increaseAndPrint(0)
  .then((n) => {
    return increaseAndPrint(n);
  })
  .then((n) => {
    return increaseAndPrint(n);
  })
  .then((n) => {
    return increaseAndPrint(n);
  })
  .then((n) => {
    return increaseAndPrint(n);
  })
  .then((n) => {
    return increaseAndPrint(n);
  })
  .catch((e) => {
    console.error(e);
  });
```

## 3. async & await

### `async`

- function 앞에 `async`를 붙이면 해당 함수는 항상 Promise를 반환한다.

  ```javascript
  async function f() {
    return Promise.resolve(1);
  }

  f().then(alert); // 1
  ```

- Promise를 반환하지 않더라도, Fulfilled 상태의 Promise로 값을 감싸 Promise를 반환하도록 한다.

  ```javascript
  async function f() {
    return 1;
  }

  f().then(alert); // 1
  ```

### `await`

- `await`는 `async` 함수 내에서만 동작한다.
- JS가 `await` 키워드를 만나면 Promise가 처리될 때까지 기다린다. 결과는 처리된 후 반환된다.

```js
async function showAvatar() {
  // JSON 읽기
  let response = await fetch("/article/promise-chaining/user.json");
  let user = await response.json();

  // github 사용자 정보 읽기
  let githubResponse = await fetch(`https://api.github.com/users/${user.name}`);
  let githubUser = await githubResponse.json();

  // 아바타 보여주기
  let img = document.createElement("img");
  img.src = githubUser.avatar_url;
  img.className = "promise-avatar-example";
  document.body.append(img);

  // 3초 대기
  await new Promise((resolve, reject) => setTimeout(resolve, 3000));

  img.remove();

  return githubUser;
}

showAvatar();
```
