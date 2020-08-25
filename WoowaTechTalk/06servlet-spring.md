# Servlet & Spring

> [강의 링크](https://www.youtube.com/watch?v=cmwmamOQmPc)

### 서블릿과 스프링

- 서블릿
  - HTML 등의 웹 콘텐츠를 생성하고 전달하기 위해 Servlet 클래스의 구현 규칙을 지켜 자바로 만들어진 **프로그램**
  - 서블릿은 어떻게 동작하는가
    - 여러 서블릿들은 Servlet Interface를 상속받아 구현된다. 이 서블릿들을 관리하는 것이 Servlet Container다. Servlet Cotainer에서 Servlet은 Server와의 통신을 도와준다.
- 스프링
  - 자바 엔터프라이즈 개발을 편리하게 해주는 오픈소스 경량급 애플리케이션 **프레임워크**
  - 스프링은 DispatcherServlet을 사용하고 있으므로, 스프링은 Servlet을 구현 또는 포함하고 있다고 본다.
    - [관련 정보 링크](https://blog.woniper.net/369)

### CGI? Servlet? JSP?

- 과거에는 정적 웹 페이지만 요했지만, 현재는 동적 웹 페이지가 필수다. 하지만 서버와 동적 웹 페이지를 만들어주는 프로그램 간의 사용하는 언어, 환경의 차이 등이 있다.
- CGI (Common Gateway Interface)
  - 웹 서버와 동적 웹 페이지를 만들어주는 프로그램 사이에서 데이터를 주고 받는 처리 **규약**
  - 인터페이스이므로 여러 언어로 구현이 가능했음.
  - 예시 : C, PEAL
  - 단점
    - ![CGI](https://user-images.githubusercontent.com/52440668/91112736-de8a4600-e6be-11ea-8d19-0310f64e337c.JPG)
    - 한 클라이언트에 한 프로세스가 돌아가야 하기 때문에 대규모 서비스는 불가하다. solution : servlet
- Servlet
  - ![servlet](https://user-images.githubusercontent.com/52440668/91112741-dfbb7300-e6be-11ea-8ed1-74ef976240ce.JPG)
    - 서블릿이 컨테이너 내부에서 쓰레드 단위로 요청을 처리하고, 그것이 생명 주기를 가진다. 이 흐름은 개발자가 아니라 컨테이너가 제어한다. (IoC)
    - 객체지향 적용. 멀티 쓰레드, 보안 적용 가능
  - 서블릿도 CGI 규칙에 따라 데이터를 주고 받지만 이 권한을 서블릿을 가지고 있는 컨테이너에게 위임한다.
  - 대신 **서블릿 컨테이너와 서블릿 사이의 규칙이 존재**. 서블릿 규약이라고 부름
  - 서블릿의 생명 주기 (Life Cycle)
    - `init()` : 서블릿의 생성
    - `service()` : 클라이언트로부터 발생한 모든 요청을 다룸
    - `destroy()` : 서블릿이 서비스를 마치게 되면, 이 함수를 통해 서블릿이 종료가 되고, Garbage Collector에 의해 메모리 정리된다.
    - 한 서비스가 끝났다고 반드시 `destroy()`하고 `init()`되는 것이 아니라, 이미 생성된 Servlet 객체는 메모리에 남겨두어 재사용한다.
