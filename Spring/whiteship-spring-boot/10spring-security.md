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

## 시큐리티 설정 커스터마이징
> [작성한 코드](https://github.com/96glory/whiteship-spring-boot/tree/27270da6c1ea24744451d9d74815c2351ade4c58/springsecurity2/src)
* 웹 시큐리티 설정
  ```java
  @Configuration
  public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/", "/hello").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .and()
            .httpBasic();
    }
  }
  ```
* UserDetailsService 구현
  ```java
  @Service
  public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(String username, String password){
      Account account = new Account();
      account.setUsername(username);
      account.setPassword(password);
      return accountRepository.save(account);
    }

    // 로그인할 떄 username을 가져와 username에 해당하는 UserDetails를 확인한다. (주로 password)
    // password가 같은지 확인하고 같으면 승인, 틀리면 예외처리
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Optional<Account> byUsername = accountRepository.findByUserName(username);
      Account account = byUsername.orElseThrow(() -> new UsernameNotFoundException(username));
      
      // username, password, 권한 부여 순서
      return new User(account.getUsername(), account.getPassword(), authorities());
    }

    private Collection<? extends GrantedAuthority> authorities() {
      return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
  }
  ```
* 내 인증 정보 만들기
  ```java
  @Component
  public class AccountRunner implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
      Account glory = accountService.createAccount("glory", "1234");
      System.out.println(glory.getUsername() + " password: " + glory.getPassword());
    }
  }
  ```
* PasswordEncoder 설정 및 사용
  * 스프링 시큐리티 상위 버전에서는 password에 encoding을 해야 로그인이 가능해짐
  * SecurityConfig.java
    ```java
    @Bean
    public PasswordEncoder passwordEncoder(){
      return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    ```
  * AccountService.java
    ```java
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account createAccount(String username, String password){
      Account account = new Account();
      account.setUsername(username);
      account.setPassword(passwordEncoder.encode(password));
      return accountRepository.save(account);
    }
    ```
