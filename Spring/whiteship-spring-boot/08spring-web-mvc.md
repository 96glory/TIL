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

## ViewResolver
* 요청이 들어오면 그 요청의 응답을 만들어낼 수 있는 뷰를 모두 탐색한 뒤, 적절한 ViewResolver를 선택함
* 스프링 부트는 ViewResolver의 설정을 제공함 (HttpMessageConvertersAutoConfiguration.java)
* XML msg converter
  ```xml
  <dependency>
     <groupId>com.fasterxml.jackson.dataformat</groupId>
     <artifactId>jackson-dataformat-xml</artifactId>
     <version>2.9.6</version>
  </dependency>
  ```
  ```java
  @Test
  public void createUser_XML() throws Exception {
    String userJson = "{\"username\":\"glory\", \"password\":\"123\"}";

    mockMvc.perform(post("/users/create")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_XML)
        .content(userJson))
          .andExpect(status().isOk())
          .andExpect(xpath("/User/username").string("glory"))
          .andExpect(xpath("/User/password").string("123"))
          .andDo(print())
        ;
    }
  ```

## 정적 리소스 지원
* 리소스 종류
  * 정적 리소스 : 요청에 해당하는 리소스가 이미 만들어져 있어서 단순히 보내기만 하면 되는 리소스
  * 동적 리소스 : 요청이 들어왔을 때 요청에 해당하는 뷰를 만들어냄
* 정적 리소스 맵핑 "/**"
  * 기본 리소스 위치
    * classpath:/static
    * classpath:/public
    * classpath:/resources/
    * classpath:/META-INF/resources
    * 예시
      * 요청 "/hello.html"이 들어오면, /static/hello.html이라는 파일이 있다면 .html 파일을 보낸다.
  * 리소스 위치 customizing (in application.properties)
    * spring.mvc.static-path-pattern : 맵핑 설정 변경 가능
      * 예시 : spring.mvc.static-path-pattern=/static/** : static 내부에 있는 것만 요청이 가능함.
    * spring.mvc.static-locations : 리소스 찾을 위치 변경 가능
      * 기본 리소스 위치가 모두 사라지게 됨. addResourceHandlers를 통해 resource 위치를 추가할 수 있음
    * WebMvcConfigurer의 addResourceHandlers
      ```java
      @Configuration
      public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
          registry.addResourceHandler("/m/**") // m으로 시작하는 요청이 들어오면
            .addResourceLocations("classpath:/m/") // classpath 기준으로 m 디렉토리 내에서 리소스를 찾겠다.
            .setCachePeriod(20); // 초 단위
            // 주의사항, "" 마지막에 /를 반드시 붙여야 한다.
        }
      }
      ```
