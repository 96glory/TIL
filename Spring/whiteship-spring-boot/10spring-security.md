# 스프링 시큐리티
* 시큐리티 시작 전
  * 아래 두 코드는 같은 역할을 한다. "/hello" 매핑에 대해 뷰 hello를 리턴하는 코드
    ```java
    @Configuration
    public class WebConfig implements WebMvcConfigurer {
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/hello").setViewName("hello");
        }
    }
    ```
    ```java
    @Controller
    public class HomeController {
        @GetMapping("/hello")
        public String hello(){
            return "hello";
        }
    }
    ```
  * 원하는 뷰가 잘 뜨는 지에 대한 test code
    ```java
    @Test
    public void hello() throws Exception {
      mockMvc.perform(get("/hello"))
              .andExpect(status().isOk())
              .andExpect(view().name("hello"))
            .andDo(print())
      ;
    }
    ```
* 시큐리티 적용 이후
  * 의존성 추가
    ```xml
    <dependency>
      <groupId>org.springframework.security</groupId>
      <artifactId>spring-security-test</artifactId>
      <version>${spring-security.version}</version>
      <scope>test</scope>
    </dependency>
    ```
  * @WithMockUser 추가
    * [작성한 코드](https://github.com/96glory/whiteship-spring-boot/blob/9d50cd21b1ff17584e2f08e5d78c06b25f9e16f2/springsecurity/src/test/java/me/glory/springsecurity/HomeControllerTest.java)
## Starter-Security
* 의존성 추가
  ```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
  ```
* 의존성을 추가하게 되면 위의 test code가 실패한다.
  * Error-message : Unauthorized
  * 모든 요청이 스프링 시큐리티에 의한 인증이 필요로 하기 때문
* 스프링 시큐리티
  * 다양한 인증 방법 지원 : LDAP, 폼 인증, Basic 인증, OAuth, ...
  * 모든 요청에 인증이 필요함. -> 애플리케이션에 접속할 때 로그인 폼 페이지가 자동으로 뜨게 됨
  * 기본 사용자 생성
    * Username : user
    * password : 애플리케이션을 실행할 때마다 console에 랜덤 값이 출력됨.
    * spring.security.user.name이나 spring.security.user.password로 변경 가능
* 스프링 시큐리티 자동 설정 파일
  * SecurityAutoConfiguration : 모든 요청을 가로채 인증을 강제하게 함
  * UserDetailsServiceAutoConfiguration : 기본 사용자 생성
* 인증 관련 각종 이벤트 발생
  * DefaultAuthenticationEventPublisher 빈 등록
  * 다양한 인증 에러 핸들러 등록 가능
