# 스프링 웹 MVC
## 스프링 웹 MVC란?
* 서블릿 API를 기반으로 구축된 웹 프레임워크
* 스프링 MVC 확장 : @Configuration + WebMvcConfiguration
  ```java
  @Configuration
  public class WebConfig implements WebMvcConfigurer {

  }
  ```
* 스프링 MVC 처음부터 재정의 : @Configuration + @EnableWebMvc
  ```java
  @Configuration
  @EnableWebMvc
  public class WebConfig implements WebMvcConfigurer {

  }
  ```
## HttpMessageConverters
> [참고](https://docs.spring.io/spring/docs/5.0.7.RELEASE/spring-framework-reference/web.html#mvc-config-message-converters)
* Spring Framework에서 제공하는 interface
* HTTP 요청 본문을 객체로 변경하거나, 객체를 HTTP 응답 본문으로 변경할 때 사용
* 여러 HttpMessageConverters 중에서 어떤 요청이 들어오고, 또는 어떤 응답을 보내야 할 지에 따라서 사용하는 HttpMessageConverters가 달라진다.
  * 요청이 json이고 응답이 json이라면 contentType에 json일 것이다.
  * 그렇다면 요청으로 들어온 json data를 개발자의 user 객체로 변환하고자 할 때 Converter는 어떻게 가져오고 어떻게 골라질까.
    * {"username":"glory", "password":"123"} ↔ User
    * [작성한 코드](https://github.com/96glory/whiteship-spring-boot/tree/af25b967eec33bd65ac49b9c083e30b8ba3a29c4/springwebmvc/src)
