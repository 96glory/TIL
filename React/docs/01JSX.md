# JSX

- 가장 단순한 React 예시
  ```JavaScript
  ReactDOM.render(
    <h1>Hello, world!</h1>,
    document.getElementById('root')
  );
  ```
  
## JSX란?

- JS를 확장한 문법으로, React Element를 생성한다.
- React에서는 본질적으로 렌더링 로직이 UI 로직(이벤트가 처리되는 방식, 시간에 따라 state가 변하는 방식, 화면에 표시하기 위해 데이터가 준비되는 방식 등)과 연결된다는 사실을 받아들인다.
- `const element = <h1>Hello, {name}</h1>;` 와 같이 HTML과 유사하게 표현되는 Object -> JSX
  ```javascript
  function formatName(user) {
    return user.firstName + ' ' + user.lastName;
  }

  const user = {
    firstName: 'Harper',
    lastName: 'Perez'
  };

  const element = (
    <h1>
      Hello, {formatName(user)}!
    </h1>
  );

  ReactDOM.render(
    element,
    document.getElementById('root')
  );
  ```
- JSX는 표현식이라 JS 로직 내에서 다른 표현식처럼 사용될 수 있다.
- JSX는 자식 JSX를 포함할 수 있다.
- React DOM은 JSX에 삽입된 모든 값을 렌더링 하기 전에 escape하므로, APP에서 명시적으로 작성되지 않은 내용은 주입되지 않는다. 모든 항목은 렌더링되기 전에 문자열로 변환되며, 이 특성으로 XSS 공격을 방지할 수 있다.
- JSX는 `React.createElement()` 호출로 컴파일 되며, 컴파일 된 값은 Object로 생성된다. 이 객체를 React Element라고 한다.
  ```javascript
  // 동일한 두 예시.
  const element = (
    <h1 className="greeting">
      Hello, world!
    </h1>
  );
  
  const element = React.createElement(
    'h1',
    {className: 'greeting'},
    'Hello, world!'
  );
  
  // createElement를 통해 아래와 같은 객체가 생성됨.
  const element = {
    type: 'h1',
    props: {
      className: 'greeting',
      children: 'Hello, world!'
    }
  };
  ```
