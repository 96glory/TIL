# API vs Library vs Framework

> [강의 링크](https://www.youtube.com/watch?v=We8JKbNQeLo)

### API (Application Programming Interface)

- 응용 프로그램에서 OS나 PL이 제공하는 기능을 제어할 수 있게 만든 **인터페이스**
- 프로그램들 사이에서 다리 역할을 한다고 생각하면 된다.
- ![그림2](https://user-images.githubusercontent.com/52440668/91011246-ed212080-e61e-11ea-816e-d6edb65a2ae8.png)
  - 노란색으로 쳐진 부분이 API라고 할 수 있다.
- API의 특징
  - 실제 구현 내용과 독립적으로, 사양만 정의되어 있다.
  - API에 따라 접근 권한이 필요할 수 있다.
- API의 예시
  - java, google map api
  - [https://console.cloud.google.com/](https://console.cloud.google.com/)

### Library

- 응용 프로그램 개발을 위해 **필요한 기능/함수를 모아 놓은** 소프트웨어
- Library의 특징
  - 독립성을 가진다. 즉, 한 라이브러리는 다른 라이브러리에 의존하지 않는다.
  - 응용 프로그램이 **능동적으로** 라이브러리를 사용한다. 응용 프로그램이 라이브러리가 필요하다고 느낄 때, 능동적으로 가져와서 사용한다.
- Library의 예시
  - Apache Commons, Guava, Lombok, jQuery

### Framework

- 응용 프로그램이나 소프트웨어의 솔루션 개발을 수월하게 하기 위해 **제공된 소프트웨어 환경**
- Framework의 특징
  - 상호 협력하는 클래스와 인터페이스의 집합니다.
  - 응용 프로그램이 **수동적으로** 프레임워크에 의해 사용된다. 프레임워크가 할 일을 다 한 뒤, 혹은 할 일들 사이에서 개발자가 작성한 비즈니스 로직을 호출해준다.
- Framework의 예시
  - Spring Framework, Junit, Ruby on Rails

### 결론

- Library와 API의 차이점은 **구현 로직의 유무** 이다.
- Library와 Framework의 차이점은 **응용 프로그램의 흐름 주도권을 누가 가지고 있느냐** 이다.
