# MobX

## MobX 란?

- 리액트 상태 관리 라이브러리

## MobX 핵심 개념

- MobX는 `observable`을 사용하면 properties, entire objects, arrays, Maps, Sets 등을 모두 자동으로 observable하게 만들 수 있다.
  - `observable` : 추적 가능한 state 정의
  - `action` : state를 변경하는 메서드
  - `computed` : state와 캐시로부터 새로운 결과를 반환

### observable

- 추적 가능한 state 정의
- `makeObservable` : 주로 class, this와 많이 쓰인다.
- `makeAutoObservable` : `makeObservable`와 유사하나, class에서 super나 subclassed가 있을 경우 사용할 수 없다.
- `make(Auto)Observable`과 `observable`과의 차이점
  - `make(Auto)Observable` : 인자로 들어온 object를 바로 observable하게 변경함.
  - `observable` : clone 후 observable하게 만든다. Proxy Object를 생성한다.

### action

- state를 변경하는 메서드
- `makeObservable`을 사용하면 action을 따로 작성해주어야 하지만, `makeAutoObservable`은 이를 대신해준다.

### computed

- state와 캐시로부터 새로운 결과를 반환
- 다른 observable 들에서 어떤 정보를 도출하는데 사용할 수 있다.

## MobX 사용하기

### MobX의 라이브러리

- `mobx-react` : 클래스형 컴포넌트와 훅을 지원함
- `mobx-react-lite` : 훅만 지원함.

### Store 구축

- Flux 아키텍쳐에서의 Store.
- 비즈니스 로직과 state를 컴포넌트에서 추출하여 단독으로 프론트, 백에서 모두 사용할 수 있도록 만드는 것

### Domain Stores

- Domain Store는 하나 이상으로 구성된다.
- 하나의 Domain Store는 해당 애플리케이션에서 하나의 역할을 책임지고 수행해야 한다.
- 하나의 Domain Store 안에는 하나 이상의 Domain Objects로 구성된다.
- Domain Objects 안에서 간단하게 state를 모델링할 수 있다.

#### 예시

- `src/store/count_class.ts`

  ```typescript
  /* makeObservable 을 이용하여 class store 구축하기 */
  import { action, makeObservable, observable } from "mobx";

  class Count {
    number: number = 0;

    constructor() {
      makeObservable(this, {
        number: observable,
        increase: action,
        decrease: action,
      });
    }

    increase = () => {
      this.number++;
    };
    decrease = () => {
      this.number--;
    };
  }

  const countStore = new Count();
  export default countStore;
  ```

  ```typescript
  // makeAutoObservable 을 이용하여 class store 구축하기
  import { makeAutoObservable } from "mobx";

  class Count {
    number: number = 0;

    constructor() {
      makeAutoObservable(this);
    }

    increase = () => {
      this.number++;
    };

    decrease = () => {
      this.number--;
    };
  }

  const countStore = new Count();
  export default countStore;
  ```

- `src/store/count_object.ts`

  ```typescript
  import { observable } from "mobx";

  const countObject = observable({
    num: 0,
    increase() {
      this.num++;
    },
    decrease() {
      this.num--;
    },
  });

  export default countObject;
  ```

- `src/store/double_class.ts`

  ```typescript
  import { makeObservable, observable, computed, action } from "mobx";

  class Doubler {
    value;

    constructor(value: number) {
      makeObservable(this, {
        value: observable,
        double: computed,
        increment: action,
      });
      this.value = value;
    }

    get double() {
      return this.value * 2;
    }

    increment() {
      this.value++;
    }
  }

  const doubleClass = new Doubler(1);
  export default doubleClass;
  ```

  ```typescript
  // makeAutoObservable로 만들기
  import { makeAutoObservable } from "mobx";

  class Doubler {
    value;

    constructor(value: number) {
      makeAutoObservable(this);
      // makeAutoObservable이 다른 action, computed를 자동으로 선언
      this.value = value;
    }

    get double() {
      return this.value * 2;
    }

    increment() {
      this.value++;
    }
  }

  const doubleClassAuto = new Doubler(1);
  export default doubleClassAuto;
  ```

- `src/store/double_class.ts`

  ```typescript
  import { observable } from "mobx";

  const doubleObject = observable({
    value: 1,
    get double() {
      return (this.value *= 2);
    },
    increment() {
      this.value++;
    },
  });

  export default doubleObject;
  ```

- `src/store/index.ts`

  ```typescript
  import countClass from "./count_class";
  import countObject from "./count_object";
  import doubleClass from "./double_class";
  import doubleObject from "./double_object";

  const store = { countClass, countObject, doubleClass, doubleObject };

  export default store;
  ```

- `src/app.tsx`

  ```typescript
  import React from "react";
  import { autorun } from "mobx";
  import { observer } from "mobx-react";
  import store from "./store";

  // 컴포넌트를 observer로 감싸주어 state가 실시간으로 변경되는 것을 감지한다.
  const App: React.FC = observer(() => {
    const { countClass, countObject, doubleClass, doubleObject } = store;
    autorun(() => {
      if (doubleClass.double) {
        console.log("Double " + doubleClass.double);
      }
      if (doubleClass.double === 8) {
        console.log("만약 value가 8이라면 0으로 초기화");
        doubleClass.value = 0;
      }
    });

    return (
      <div style={{ padding: "50px" }}>
        <div style={{ marginBottom: "50px" }}>
          <h1>Count (Class)</h1>
          <div>number: {countClass.number}</div>
          <button onClick={() => countClass.increase()}>plus</button>
          <button onClick={() => countClass.decrease()}>minus</button>
        </div>

        <div style={{ marginBottom: "50px" }}>
          <h1>Count (Object)</h1>
          <div>num: {countObject.num}</div>
          <button onClick={() => countObject.increase()}>increment</button>
          <button onClick={() => countObject.decrease()}>decrement</button>
        </div>

        <div>
          <h1>Computed</h1>
          <div>double number : {doubleClass.value}</div>
          <button onClick={() => doubleClass.increment()}>
            double increment
          </button>
        </div>
      </div>
    );
  });

  export default App;
  ```

### UI Stores
