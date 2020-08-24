# Web Server vs WAS

> [강의 링크](https://www.youtube.com/watch?v=NyhbNtOq0Bc)

### Web Server

- Web
  - 인터넷을 기반으로 한 정보를 공유, 검색할 수 있게하는 서비스
  - URL(주소), HTTP(통신 규칙), HTML(내용)
- Server
  - 네트워크를 통해 클라이언트에게 정보나 서비스를 제공하는 컴퓨터 시스템
- Web Server
  - 인터넷을 기반으로 클라이언트에게 웹 서비스를 제공하는 컴퓨터
    - 클라이언트가 웹 서버에게 url를 가지고 http에 맞게 요청하면, 알맞은 html을 응답받음.
    - 서버는 클라이언트의 요청을 기다리고, http에 대한 데이터를 만들어서 응답한다. 이 때 데이터는 웹에서 처리할 수 있는 html, css, 이미지 등 정적인 데이터로 한정된다.
  - 예시 : 아파치, MS IIS, nginx, ...
- 한계
  - 동적인 데이터 처리를 못한다.
  - 예시 : DB의 데이터를 html에 입히기.
  - 솔루션 : WAS

### WAS (Web Application Server)

- Web Application
  - 웹에서 실행되는 응용 프로그램
- WAS
  - 웹 애플리케이션과 서버 환경을 만들어 동작시키는 기능을 제공하는 SW 프레임워크
  - 웹 애플리케이션을 실행시켜 필요한 기능을 수행하고 그 결과를 웹 서버에게 전달.
  - php, jsp, asp와 같은 언어들을 사용해 동적인 페이지를 생성할 수 있는 서버
  - 프로그램 실행 환경과 DB 접속 기능을 제공
  - 비즈니스 로직 수행 가능
  - 웹 서버 + 웹 컨테이너
    - 컨테이너 : jsp, servlet 등을 실행시킬 수 있는 소프트웨어
  - java에서는 웹 애플리케이션 컨테이너라고도 불림 (웹 애플리케이션이 배포되는 공간)
  - 예시 : Tomcat, JEUS, IBM WebSphere, ...

### Web Server vs WAS

- ![image](https://user-images.githubusercontent.com/52440668/91007353-b0512b80-e616-11ea-86dd-126b2ba5fc02.png)
- 상황에 따라 변하는 정보를 제공할 수 있는가?
  - Web Server : 아니오
  - WAS : 예
