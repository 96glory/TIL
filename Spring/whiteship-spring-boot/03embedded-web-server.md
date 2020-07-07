# 내장 웹 서버
## 내장 웹 서버 이해
* 스프링 부트는 서버가 아니다.
  * 웹 서버가 없이 스프링 실행하기
```java
@SpringBootApplication
public class EmbeddedWebServerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(EmbeddedWebServerApplication.class);
        application.setWebApplicationType(WebApplicationType.NONE);
    }

}
```
* 스프링 부트는 내장 서블릿 컨테이너를 쉽게 사용하게 도와주는 tool이다.
  * 서버는 tomcat, jetty, undertoe, ...
  * tomcat 객체 생성
  * 포트 설정
  * tomcat에 context 추가
  * servlet 만들기
  * tomcat에 servlet 추가
  * context에 servlet mapping
  * tomcat 실행 및 대기
* 스프링 부트의 자동 설정
  * ServletWebServerFactoryAutoConfiguration : 서블릿 웹 서버 생성
  * TomcatServletWebServerFactoryCustomizer : 서버 커스터마이징
  * DispatcherServletAutoConfiguration : 서블릿 만들고 등록함
  
  ## 내장 웹 서버 응용 : 컨테이너와 포트
  * default인 tomcat 대신에 다른 내장 서버 사용하기
    * spring-boot-starter-web 은 default로 tomcat을 가져오므로 exclusion tag를 사용해서 제외한 뒤, 원하는 서버를 의존성 추가한다.
    * [참고](https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto-embedded-web-servers)
  * 포트 번호 바꾸기
    * application.properties 에서 server.port=원하는포트번호
  
## 내장 웹 서버 응용 : HTTPS 적용하기
* 1. keystore 생성
* 2. keystore의 정보를 application.properties에 update
  * [참고](https://gist.github.com/keesun/f93f0b83d7232137283450e08a53c4fd)
* but, HTTPS만 사용 가능해진다. 새 connector를 만들어야 함
  * [참고](https://github.com/spring-projects/spring-boot/blob/v2.0.3.RELEASE/spring-boot-samples/spring-boot-sample-tomcat-multi-connectors/src/main/java/sample/tomcat/multiconnector/SampleTomcatTwoConnectorsApplication.java)
  * [작성한 코드](https://github.com/96glory/whiteship-spring-boot/tree/06652d11fcfa8f4eab025f9d59a0d005e26955ca)
  
  
