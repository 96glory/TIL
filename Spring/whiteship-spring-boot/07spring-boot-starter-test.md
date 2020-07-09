# spring-boot-starter-test
> [작성한 코드](https://github.com/96glory/whiteship-spring-boot/tree/89cb809a5dd8f3139532a5a8227e4779dda614cc/springtesting/src)
```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-test</artifactId>
  <scope>test</scope>
</dependency>
```

## 기본적 형태의 test code
```java
@RunWith(SpringRunner.class)
@SpringBootTest // default: (webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SampleControllerTest {
  // ...
}
```
## @SpringBootTest
* MOCK
  * 서블릿 컨테이너를 테스트용으로 띄우지 않고 MockUp을 하여 서블릿을 Mocking한 것이 뜸.
  * DispatcherServlet이 만들어지긴 하지만 실제는 아니고 MockUp을 한 것이고, 단순 실험만 가능.
  * MockUp이 된 서블릿과 interaction을 하기 위해 MockMvc라는 client를 사용해야 한다.
  * MockMvc를 사용하기 위해 @AutoConfigureMockMvc 추가해야함.
  * 내장 톰캣을 구동하지 않음
  ```java
  @RunWith(SpringRunner.class)
  @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
  @AutoConfigureMockMvc
  public class SampleControllerTest {

      @Autowired
      MockMvc mockMvc;
      
      @Test
      public void hello() throws Exception {
          mockMvc.perform(get("/hello"))
                  .andExpect(status().isOk())
                  .andExpect(content().string("hello glory"))
                  .andDo(print())
          ;
      }
  }
  ```
* RANDOM_PORT, DEFINED_PORT
  * 내장 톰캣 사용함.
  * 실제 서블릿이 랜덤 포트/정의된 포트에 뜸.
  ```java
  import static org.assertj.core.api.Assertions.assertThat;

  @RunWith(SpringRunner.class)
  @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
  @AutoConfigureMockMvc
  public class SampleControllerTest2 {

      @Autowired
      TestRestTemplate testRestTemplate;

      @Test
      public void hello() throws Exception {
          String result = testRestTemplate.getForObject("/hello", String.class);
          assertThat(result).isEqualTo("hello glory");
      }
  }
  ```

## @MockBean
* 사용하는 이유
  * 서블릿/MockUp에 보내기에는 너무 큰 test다.
  * 한 Controller만 test하고 싶다.
* ApplicationContext에 들어있는 빈을 Mock으로 만든 객체로 교체함.
* 모든 @Test마다 자동으로 리셋.
```java
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SampleControllerTest2 {

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    SampleService mockSampleService;

    @Test
    public void hello() throws Exception {
        when(mockSampleService.getName()).thenReturn("youngkwang");

        String result = testRestTemplate.getForObject("/hello", String.class);
        assertThat(result).isEqualTo("hello youngkwang");
    }
}
```

## 슬라이스 테스트
* 수많은 빈이 등록되는 게 싫고 내가 테스트 하고자 하는 빈만 등록하고 싶을 때
* 레이어 별로 잘라서 테스트하게 됨.
  * @JsonTest
  * @WebMvcTest
  * @WebFluxTest
  * @DataJpaTest ...
```java
@RunWith(SpringRunner.class)
@WebMvcTest(SampleController.class)
public class SampleControllerTest3 {

    @MockBean
    SampleService mockSampleService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void hello() throws Exception {
        when(mockSampleService.getName()).thenReturn("youngkwang");

        mockMvc.perform(get("/hello"))
                .andExpect(content().string("hello youngkwang"))
        ;
    }
}
```
