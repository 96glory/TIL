# 그런 REST API로 괜찮은가

> [강의 링크](https://www.youtube.com/watch?v=RP_f5dMoHFc&ab_channel=naverd2)

### WEB의 역사

-   어떻게 인터넷에서 정보를 공유할 것인가?
    -   정보들을 하이퍼 텍스트로 연결한다.
        -   표현 형식 : HTML
        -   식별자 : URI
        -   전송 방법 : HTTP
    -   WEB 체계를 부수지 않고 HTTP를 사용할 수 있을까?
        -   sol : **HTTP Object Model -> REST로 바뀜**

### REST (Representational State Transfer)

-   인터넷 상의 시스템 간의 상호 운용성을 제공하는 방법
-   시스템 제각각의 독립적인 진화를 보장하기 위한 방법
-   분산 하이퍼미디어 시스템(예 : 웹)을 위한 아키텍쳐 스타일
-   REST의 아키텍쳐 스타일 : 제약 조건의 집합
    -   client-server
    -   stateless
    -   cache
    -   **uniform interface**
        -   identification fof resources
        -   manipulation of resources through representations
        -   **self-descriptive messages** : 메시지는 스스로 설명해야 한다.
        -   **HATEOAS (hypermedia as the engine of application state)** : 애플리케이션의 상태는 Hyperlink를 이용해 전이되어야 한다.
    -   layered system
    -   code-on-demand (optional) : 서버에서 클라이언트로 코드를 보내서 작동할 수 있어야 한다(JS)

#### self-descriptive messages

-   클라이언트는 서버가 클라이언트에게 보낸 메시지를 해석 가능하고, 늘 대응할 수 있도록 메시지를 적절히 부가 설명을 추가해야한다.
-   sol
    -   미디어 타입을 정의하고 IANA에 등록하고 그 미디어 타입을 리소스 리턴할 때 Content-Type으로 사용한다.
    -   profile 링크 헤더를 추가한다. but, 브라우저들이 헤더를 잘 해석하지 못한다.
        -   대안으로 [HAL](http://stateless.co/hal_specification.html)의 링크 본문에 profile 링크 추가

#### HATEOAS (hypermedia as the engine of application state)

-   하이퍼미디어/링크를 통해 애플리케이션 상태 변화가 가능해야 한다.
-   링크 정보를 동적으로 바꿀 수 있다. 즉, 버저닝할 필요가 없다.
-   sol
    -   데이터에 링크 제공
        -   링크 정의? : [HAL](http://stateless.co/hal_specification.html)
    -   링크 헤더나 Location을 제공

### REST API

-   REST 아키텍쳐 스타일을 따르는 API
