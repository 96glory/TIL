# Flux

> [참고 : Flux 공식 문서](https://haruair.github.io/flux/docs/overview.html)
> [참고 : Flux로의 카툰 가이드](https://bestalign.github.io/translation/cartoon-guide-to-flux/)

## 동기

- MVC 모델에서 Model과 View 사이의 양방향 통신이 복잡하게 얽힌 것을 보고, 이를 해결하기 위해 **_단방향 데이터 흐름 개념_**의 Flux 모델을 제시함.

- 액션 생성자 (`Action Creator`)
  - 모든 변경 사항과 사용자와의 상호작용이 거쳐가야 하는 `Action`의 생성을 담당한다.
  - 언제든 애플리케이션의 상태를 변경하거나 뷰를 업데이트하고 싶다면 `Action`을 생성해야 한다.
  - `Action Creator`는 `type`과 `payload`를 포함한 `Action`을 생성한다.
    - `type` : 시스템에 정의된 `Action` 중의 하나
  - `Action Creator`가 `Action`을 생성한 뒤에 이를 `Dispatcher`로 넘겨준다.
- 디스패쳐 (`Dispatcher`)
  - `callback`이 등록되어 있는 곳
  - `Action`을 보낼 필요가 있는 모든 `store`를 가지고 있고, `Action Creator`로부터 `Action`이 넘어오면 여러 `store`에게 `Action`을 보낸다.
  - 동기적으로 실행되어 의존성이 있는 `store`에서 순서 보장이 된다.
  - `store`가 무슨 `Action`을 구독할 지 구분하지 않고, 모든 `store`에게 모든 `Action`을 보낸다. 이를 받은 `store`는 처리할 `Action`만 골라서 처리한다.
- `Store`
  - 애플리케이션 내의 모든 상태와 그와 관련된 로직을 가지고 있다.
  - 모든 상태 변경은 반드시 `store`에 의해 결정되어야 하며, 상태 변경을 위한 요청을 `store`에서 직접 보낼 수 없다.
  - `store`에는 setter가 존재하지 않으므로, 상태 변경을 요청하기 위해서 반드시 모든 정해진 절차를 따라야 한다. 즉, 무조건 `Action Creator`나 `Dispatcher`의 파이프라인을 거쳐서 `Action`을 보내야 한다.
  - `store`의 내부에서는 보통 switch 문을 사용해서 처리할 `Action`과 무시할 `Action`을 결정한다. 만약 처리해야하는 `Action`이라면, 주어진 `Action`에 따라 로직 처리를 하고, 상태를 변경하게 된다.
  - `store`에서 상태 변경을 완료하고 나면, `Controller View`에게 변경 이벤트를 내보냄으로써 상태가 변경되었다는 것을 알려주게 된다.
- `Controller View`와 `View`
  - `View`는 상태를 가져와서 이를 유저에게 보여줄 화면을 렌더링하는 역할이다.
  - `View`는 React 컴포넌트가 된다.
  - `Controller View`는 `store`와 `view` 사이의 중간관리자가 된다. `store`에서 상태가 변경되었다는 이벤트를 받으면, 자신의 아래에 있는 자식 뷰들에게 새로운 상태를 알려준다.

## 동작 과정

### 준비 (setup)

> 애플리케이션이 초기화될 때 한번 실행됨.

1. `store`는 `dispatcher`에 `action`이 들어오면 알려 달라고 말해둔다.
2. `Controller View`는 `store`에게 최신 상태를 묻는다.
3. `store`가 `Controller View`에게 상태를 주면 렌더링하기 위해 모든 자식 뷰에게 상태를 넘겨준다.
4. `Controller View`는 `store`에게 상태가 바뀔 때 알려달라고 다시 부탁한다.

### 데이터 흐름 (data flow)

> setup이 끝나면 애플리케이션은 유저 입력을 위한 준비가 완료된다. 사용자의 입력으로 인한 액션이 생겼을 경우 하기 내용을 따른다.

1. `view`는 `action creator`에게 `action`을 준비하라고 말한다.
2. `action creator`는 `action`을 포맷에 맞게 만든 후, 이를 `dispatcher`에게 넘긴다.
3. `dispatcher`는 들어온 `action`의 순서에 따라 알맞은 `store`로 보낸다. 각 `store`는 모든 `action`을 받게 되지만 필요한 `action`만을 골라서 상태를 필요에 맞게 변경한다.
4. 상태 변경이 완료되면 `store`는 자신을 구독하고 있는 `controller view`에게 그 사실을 알린다.
5. 연락을 받은 `controller view`들은 `store`에게 변경된 상태를 요청한다.
6. `store`가 새로운 상태를 넘겨주면, `controller view`는 자신 아래의 모든 `view`에게 새로운 상태에 맞게 랜더링하라고 알린다.
