# SpringApplication
* 기본 로그 레벨 : INFO
  * 로그 레벨을 DEBUG로 바꾸기 (아래 둘 중 하나 적용)
    * VM option : -Ddebug
    * program argument : --debug
* FailureAnalyzer : 오류 출력
* banner customizing
  * [참고](https://docs.spring.io/spring-boot/docs/current/reference/html/spring-boot-features.html#boot-features-banner)
* ApplicationEvent 등록
  * ApplicationContext를 만들기 전/후에 리스너를 추가시킬 수 있다.
    * [코드 참조](https://github.com/96glory/whiteship-spring-boot/tree/3ee80ca66b9ac9164979050edbc194a37ccfadfe)
    * 단, ApplicationContext가 만들어지기 전에 사용하는 listener는 Bean으로 등록할 수 없으므로 SpringApplication.addListeners(...)를 활용한다.
* WebApplicationType 설정
  ```java
  springApplication.setWebApplicationType(WebApplicationType.___);
  ```
  * default : SERVLET
  * webflux 사용 시 : REACTIVE
  * 둘 다 없다면 : NONE
  * 우선순위는 SERVLET이 더 높다. 따라서 SERVLET과 WEB FLUX가 동시에 있을 때 SERVLET으로 설정된다.
