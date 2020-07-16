# 스프링 부트가 제공하는 스프링 웹 MVC를 위한 편의기능
> 목차
> 1. [스프링 웹 MVC란?](#스프링-웹-MVC란?)
> 2. [HttpMessageConverters](#httpmessageconverters)
> 3. [ViewResolver](#viewresolver)
> 4. [정적 리소스 지원](#정적-리소스-지원)
> 5. [웹 JAR](#웹-jar)
> 6. [index 페이지와 favicon](#index-페이지와-favicon)
> 7. [thymeleaf](#thymeleaf)
> 8. [HtmlUnit](#htmlunit)
> 9. [ExceptionHandler](#exceptionhandler)
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

## 웹 JAR
* 웹 JAR란?
  * 클라이언트에서 사용하는 JS Library(bootstrap, react, jQuery, ...)를 jar 파일로 추가할 수 있다.
* 예시 : jQuery를 사용자 컴퓨터에 Web Jar 형식으로 추가하기
  ```xml
  <!-- jQuery -->
  <dependency>
      <groupId>org.webjars.bower</groupId>
      <artifactId>jquery</artifactId>
      <version>3.5.1</version>
  </dependency>

  <!-- html 문서에서 version 생략 기능 -->
  <dependency>
    <groupId>org.webjars</groupId>
    <artifactId>webjars-locator-core</artifactId>
    <version>0.46</version>
  </dependency>
  ```
  ```html
  <!DOCTYPE HTML>
  <html>
  <head>
      <meta charset="UTF-8">
      <title>Title</title>
  </head>
  <body>
  Hello, static resource.

  <script src="/webjars/jquery/dist/jquery.min.js"></script>
  <script>
      $(function() {
          alert("ready!");
      });
  </script>

  </body>
  </html>
  ```

## index 페이지와 favicon
* index 페이지 (welcome page)
  * root page를 요청했을 때 보여지는 페이지
  * 기본 리소스 위치에서
    * index.html 찾아 보고, 있으면 제공
    * index.템플릿 찾아 보고, 있으면 제공
    * 위의 두가지 경우가 해당하지 않으면 에러 페이지 제공
* favicon
  * 인터넷 웹 브라우저의 주소창에 표시되는 웹사이트나 웹페이지를 대표하는 아이콘
  * [favicon.io](https://favicon.io/)에서 favicon을 만들 수 있다.
  * 기본 리소스 위치에 두면 알아서 탐색한다. (파일 이름 : favicon.ico)
  * favicon이 보이지 않을 경우?
    1. localhost:8080/favicon.ico 에 접속.
    2. enter 후 ctrl + f5
    3. 브라우저 재시작
    
## thymeleaf
* 웹 mvc로 동적 콘텐츠(view)를 생성하는 템플릿 엔진 중 하나
  * FreeMarker, Groovy, Thymeleaf, Mustache, ...
* thymeleaf 사용하기
  ```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
  </dependency>
  ```
  * 위 의존성을 추가하면 템플릿 파일의 위치를 /src/main/resources/template/ 에 두어야 한다.
  * tip : intellij에서 html 문서 자동완성을 위해서 ! 이후 tab 키를 누르면 된다.
  * 참고할 문서
    * [깊게 배우기](https://www.thymeleaf.org/)
    * [짧게 배우기](https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html)
  * 시작하기
    ```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    ```
  * thymeleaf를 활용해 만든 html
    ```html
    <!doctype html>
    <html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>test thymeleaf</title>
    </head>
    <body>
      <h1 th:text="${name}">Name</h1>
    </body>
    </html>
    ```
  * Controller
    ```java
    @Controller
    public class SampleController {
        @GetMapping("/hello")
        public String hello(Model model){
            model.addAttribute("name", "glory");
            return "hello";
        }
    }
    ```
  * test code
    ```java
    @RunWith(SpringRunner.class)
    @WebMvcTest(SampleController.class)
    public class SampleControllerTest {
        @Autowired
        MockMvc mockMvc;

        @Test
        public void hello() throws Exception{
            // 요청 "/hello"
            // 응답
            //      - 모델 name : glory
            //      - 뷰 이름 : hello
            mockMvc.perform(get("/hello"))
                    // 요청 결과가 200이니?
                    .andExpect(status().isOk())
                    // view의 이름이 hello니?
                    .andExpect(view().name("hello"))
                    // model 안에 ("name", "glory")라는 attribute가 있니?
                    .andExpect(model().attribute("name", "glory"))
                    // view 안에 glory라는 content가 있니?
                    .andExpect(content().string(containsString("glory")))
                    .andDo(print())
            ;
        }
    }
    ```
  * [작성한 코드 전문](https://github.com/96glory/whiteship-spring-boot/tree/61e41445c1bd58b8f448a81061c6554327e5438a/springwebmvc2/src)
  
## HtmlUnit
* 좀 더 전문적인 HTML template view test, html을 unit test 하기 위한 tool
* [참고할 문서](https://htmlunit.sourceforge.io/gettingStarted.html)
* 의존성
  ```xml
  <dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>htmlunit-driver</artifactId>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>net.sourceforge.htmlunit</groupId>
    <artifactId>htmlunit</artifactId>
    <scope>test</scope>
  </dependency>
  ```
  
## ExceptionHandler
* 기본적으로 제공해주는 예외 처리기
  * BasicErrorController : HTML과 JSON 응답 지원
  * customizing : ErrorController 구현
* 스프링 MVC 예외 처리 방식
  * @ControllerAdvice
  * @ExchangeHandler
    * SampleController.class
    ```java
    @Controller
    public class SampleController {
        @GetMapping("/hello")
        public String hello(){
            throw new SampleException();
        }

        @ExceptionHandler(SampleException.class)
        public @ResponseBody AppError sampleError(SampleException e){
            AppError appError = new AppError();
            appError.setMessage("error.app.key");
            appError.setReason("I don't know");
            return appError;
        }
    }
    ```
    * AppError.class
    ```java
    package me.glory.springwebmvc3;

    public class AppError {
        private String message;
        private String reason;

        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }
        public String getReason() {
            return reason;
        }
        public void setReason(String reason) {
            this.reason = reason;
        }
    }
    ```
    * SampleError.class
    ```java
    public class SampleException extends RuntimeException {

    }
    ```
* custom error page 
  * 상태 코드 값에 따라 에러 페이지 보여주기
  * src/main/resources/static|template/error/
    * 404.html
    * 5xx.html
    * [작성한 코드](https://github.com/96glory/whiteship-spring-boot/tree/dd1009e0b597e1f111e91a09ae96f8f7b5c50778/springwebmvc3/src)
