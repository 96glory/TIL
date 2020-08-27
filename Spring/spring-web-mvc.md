# Spring Web MVC

## DispatcherServlet

-   스프링 부트로 사용하는 스프링 MVC
    -   자바 애플리케이션 내부에 존재하는 내장 톰캣을 애플리케이션에 종속된 형태로 구동시키고, `DispatcherServlet`을 등록한다. 스프링 부트의 자동 설정이 위 행위를 해준다.

### DispatcherServlet의 작동 순서

1. 사용자의 요청을 분석한다.
2. 요청을 처리할 핸들러를 찾는다.
3. 핸들러를 실행할 수 있는 핸들러 어댑터를 찾는다.
4. 핸들러 어댑터를 사용해서 핸들러의 응답을 처리한다.
    - 예외 발생 시, 예외 처리 핸들러에 요청 처리를 위임한다.
5. 핸들러의 리턴값을 보고 어떻게 처리할 지 판단한다.
    - 뷰 이름에 해당하는 뷰를 찾아서 모델 데이터를 랜더링한다.
    - `@ResponseEntity`가 있다면 Converter를 사용해서 응답을 가공한다.
6. 사용자에게 응답을 보낸다.

### DispathcerServlet의 구성 요소

#### MultipartResolver

-   파일 업로드 요청 처리에 필요한 인터페이스
-   `httpServletRequest`를 `MultipartHttpServletRequest`로 변환하여 요청이 담고 있는 파일을 `getFile()`이라는 메소드를 통해 쉽게 꺼낼 수 있는 API를 제공해준다.

#### LocaleResolver

-   클라이언트의 지역 정보를 파악하는 인터페이스로, 지역 정보에 따라 `MessageSource`에서 적절한 지역 언어로 화면에 보여 줄 수 있다.

#### ThemeResolver

-   애플리케이션에 설정된 테마를 파악하고 변경할 수 있는 인터페이스

#### HandlerMapping

-   요청을 처리할 핸들러를 찾는 인터페이스
-   annotation 기반으로 Mapping 시킨 경우 `RequestMappingHandlerMapping`이 사용된다.

#### HandlerAdapter

-   `HandlerMapping`이 찾아낸 Handler를 호출하고 처리하는 인터페이스로, 실제 요청을 처리하는 인터페이스
    ­- annotation을 기반으로 Mapping 시킨경우 `RequestMappingHandlerAdapter`가 사용된다.

#### HandlerExceptionResolver

-   요청 처리 중에 발생한 에러를 처리하는 인터페이스
-   `@ExceptionHandler`로 정의하는 방법이 있는데, 이 때 `ExceptionHandlerExceptionResolver`가 사용된다.

#### RequestToViewNameTranslator

-   핸들러에서 뷰 이름을 명시적으로 리턴하지 않은경우, 요청을 기반으로 뷰 이름을 판단하는 인터페이스

    ```java
    @GetMapping​(​"/test"​)
    public​ ​void​ ​test​() {

    }
    ```

#### ViewResolver

-   핸들러에서 반환하는 String에 해당하는 View를 찾아내는 인터페이스.
-   기본 전략으로 `InternalResourceViewResolver`가 등록되어 있다. `InternalResourceViewResolver`는 기본적으로 jsp를 지원해서 jsp 뷰를 사용할 수 있다.

#### FlashMapManager

-   Redirect(refresh할때 같은 데이터를 다시 보내지 않게)를 할 때 요청 매개변수를 사용하지 않고 데이터를 전달하고 정리할 때 사용
-   기본 전략으로 `SessionFlashMapManager`를 사용한다.

## 스프링 부트에서 제공하는 MVC customizing

1. `@Configuration` + `implements WebMvcConfigurer` (가장 많이 사용함)
    - `@Configuration`를 이용해 설정 파일로 지정하고 `WebMvcConfigurer`를 구현해 추가적으로 설정할 수 있다.
2. `@Configuration` + `@EnableWebMvc` + `implements WebMvcConfigurer`

    - `@EnableWebMvc`를 사용하면 `WebMvcAutoConfiguration`이 사용되지 않으므로 스프링 부트의 기본 설정을 사용할 수 없다. 즉, 개발자가 A to Z 다시 개발해야함.
    - `ViewResolver` 커스터마이징

        ```java
        @Configuration
        @ComponentScan
        @EnableWebMvc
        public class WebConfig implements WebMvcConfigurer {

            @Override
            public void configureViewResolvers(ViewResolverRegistry registry) {
                registry.jsp("/WEB_INF/", ".jsp");
            }

        }
        ```

3. `application.properties`
    - `WebMvcAutoConfiguration`은 `ResourceProperties`, `WebMvcProperties` 등의 클래스에 따라 설정을 한다.

## 스프링 MVC 구성 요소

### Formatter

-   String과 Object 사이를 변환해준다.
-   `Printer`와 `Parser`를 상속 받은 인터페이스다.
    -   `Printer` : 해당 객체를 문자열로 출력
    -   `Parser` : 문자열을 객체로 변환
-   `Formatter`가 없는 경우

    ```java
    public​ ​class​ ​Person​ {
        ​private​ String name;

        ​public​ String ​getName​() {
        ​   return​ name;
        }

        ​public​ ​void​ ​setName​(String name) {
        ​   this​.name = name;
        }
    }
    ```

    ```java
    @RestController
    public​ ​class​ ​SampleController​ {

        // "/hello/glory"
        ​@GetMapping​(​"/hello/{name}"​)
        ​public​ String ​hello​(@PathVariable(​"name"​) Person person) {
            ​return​ ​"hello "​ + person.getName();
        }

        /* "/hello?name=glory"
        ​@GetMapping​(​"/hello"​)
        public​ String ​hello​(@RequestParam(​"name"​) Person person) {
            return​ ​"hello "​ + person.getName();
        }
        */
    }
    ```

    ```java
    @ExtendWith​(SpringExtension.class)
    @WebMvcTest
    class​ ​SampleControllerTest​ {

        ​@Autowired
        MockMvc mockMvc;

        // String을 Person으로 변환할 수 없어서 test fail
        ​@Test
        ​public​ ​void​ ​hello​() ​throws​ Exception {
            ​this​.mockMvc.perform(get(​"/hello/glory"​))
                        .andDo(print())
                        .andExpect(content().string(​"hello glory"​));
        }
    }
    ```

-   `Formatter`를 적용하면

    -   `Formatter` 만들기

        ```java
        @Component
        public​ ​class​ ​PersonFormatter​ ​implements​ ​Formatter​<​Person​> {

            ​@Override
            ​public​ Person ​parse​(String text, Locale locale) ​throws ParseException {
                Person person = ​new​ Person();
                person.setName(text);
                ​return​ person;
            }

            ​@Override
            ​public​ String ​print​(Person object, Locale locale) {
            ​   return​ object.toString();
            }

        }
        ```

    -   `Formatter` 등록하기

        ```java
        // 위에서 만든 Formatter에 @Component를 붙이지 않았다면, 아래와 같이 등록을 해주어야 함. @Component를 붙였다면, 이미 빈으로 스캔되면서 위 Formatter가 등록이 된 것이므로 굳이 아래와 같이 등록할 필요 없음.
        @Configuration
        public​ ​class​ ​WebConfig​ ​implements​ ​WebMvcConfigurer​ {
            ​@Override
            ​public​ ​void​ ​addFormatters​(FormatterRegistry registry) {
                registry.addFormatter(​new​ PersonFormatter());
            }
        }
        ```

        ```java
        // test code 수정
        @ExtendWith​(SpringExtension.class)
        @SpringBootTest
        @AutoConfigureMockMvc
        class​ ​SampleControllerTest​ {

            ​@Autowired
            MockMvc mockMvc;

            ​@Test
            ​public​ ​void​ ​hello​() ​throws​ Exception {
                ​this​.mockMvc.perform(get(​"/hello/glory"​))
                            .andDo(print())
                            .andExpect(content().string(​"hello glory"​));
            }

        }
        ```

### HandlerInterceptor

-   `HandlerInterceptor`
    -   `HandlerMapping`에 설정할 수 있는 인터셉터
    -   핸들러를 실행하기 전, 후, 완료 시점에 부가 작업을 하고 싶은 경우에 사용할 수 있다.
-   `HandlerInterceptor`의 메소드
    -   `boolean preHandle(request, response, handler)`
        -   핸들러가 실행되기 전에 호출됨.
        -   핸들러의 정보를 사용할 수 있음.
        -   리턴 값 : 핸들러로 요청/응답을 전달할 지, 응답 처리를 이곳에서 끝났는 지에 대한 여부를 알린다.
    -   `void postHandle(request, response, handler, modelAndView)`
        -   핸들러 실행이 끝나고, 뷰를 랜더링 하기 전에 호출
        -   뷰에 전달할 핸들러에 공통적으로 사용될 모델 정보를 담는 데 사용할 수 있다.
        -   핸들러 인터셉터가 여러 개 일때 인터셉터의 역순으로 호출된다.
        -   비동기적 요청 처리시에는 호출되지 않는다.
    -   `void afterCompletion(request, response, handler, ex)`
        -   요청 처리가 완전히 끝난 뒤에 호출된다. 즉, 뷰를 랜더링한 후에 호출된다.
        -   `preHandler`에서 true를 리턴한 경우에만 호출된다.
        -   이 메소드는 인터셉터 역순으로 호출된다.
        -   비동기적 요청 처리시에는 호출되지 않는다.
-   `HandlerInterceptor` 적용하기

    -   `HandlerInterceptor` 구현

        ```java
        public​ ​class​ ​GreetingInterceptor​ ​implements​ ​HandlerInterceptor​ {

            ​@Override
            ​public​ ​boolean​ ​preHandle​(HttpServletRequest request, HttpServletResponse response, Object handler) throws​ Exception {
                System.out.println(​"preHandle1"​);
                ​return​ ​true​;
            }

            ​@Override
            ​public​ ​void​ ​postHandle​(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) ​throws​ Exception {
                System.out.println(​"postHandle1"​);
            }

            ​@Override
            ​public​ ​void​ ​afterCompletion​(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) ​throws Exception {
                System.out.println(​"afterCompletion1"​);
            }

        }
        ```

    -   `HandlerInterceptor` 등록

        ```java
        @Configuration
        public​ ​class​ ​WebConfig​ ​implements​ ​WebMvcConfigurer​ {

            ​@Override
            ​public​ ​void​ ​addInterceptors​(InterceptorRegistry registry) {
                registry.addInterceptor(​new​ GreetingInterceptor());
            }

        }
        ```

### ResourceHandler

-   `ResourceHandler`
    -   이미지, JS, CSS, HTML 파일과 같은 정적인 리소스를 처리하는 핸들러
    -   스프링 부트의 리소스 핸들러 매핑
        -   `resources/static` 폴더 안에 정적 리소스를 넣으면 매핑 시켜준다.
-   `ResourceHandler` 설정
    -   요청 패턴 지정
    -   리소스 위치 : 변경, 추가 가능
    -   캐싱 설정
    -   `ResourceResolver` : 요청에 해당하는 리소스를 찾는 전략
    -   `ResourceTransformer` : 응답으로 보낼 리소스를 수정할 전략
-   `ResourceHandler` 적용하기

    -   `ResourceHandler` 구현

        ```html
        <!DOCTYPE html>
        <​html​ lang=​"en"​>
        <​head​>
            ​<​meta​ charset=​"UTF-8"​>
            ​<​title​>​Title​</​title​>
        </​head​>
        <​body​>
            <​h1​>​hello mobile​</​h1​>
        </​body​>
        </​html​>
        ```

        ```java
        @Configuration
        public​ ​class​ ​WebConfig​ ​implements​ ​WebMvcConfigurer​ {

            ​@Override
            ​public​ ​void​ ​addResourceHandlers​(ResourceHandlerRegistry registry) {
                registry
                        // 매핑될 url 설정
                        .addResourceHandler(​"/mobile/**"​)
                        // 정적 리소스 파일의 경로를 지정
                        .addResourceLocations(​"classpath:/mobile/"​)
                        // 캐싱 설정
                        .setCacheControl(CacheControl.maxAge(​10​, TimeUnit.MINUTES));
            }

        }
        ```

        ```java
        // addResourceHandlers 가 잘 적용되었는지 확인하는 테스트 코드
        @ExtendWith​(SpringExtension.class)
        @SpringBootTest
        @AutoConfigureMockMvc
        class​ ​SampleControllerTest​ {

            ​@Autowired
            MockMvc mockMvc;

            ​@Test
            ​public​ ​void​ ​mobileStatic​() ​throws​ Exception {
                ​this​.mockMvc.perform(get(​"/mobile/index.html"​))
                            .andDo(print())
                            .andExpect(status().isOk())
                            .andExpect(content().string(Matchers.containsString(​"hello mobile"​)))
                            .andExpect(header().exists(HttpHeaders.CACHE_CONTROL));
            }
        }
        ```

### HTTP Message Converter

-   HTTP Message Converter

    -   컨트롤러에서 `@RequestBody`를 이용해 요청 본문에서 메시지를 읽어 들이거나, `@ResponseBody`를 이용해 응답 본문에서 메시지를 작성할 때 사용한다.

        -   `@RequestBody`

            ```java
            @Controller
            public​ ​class​ ​SampleController​ {

                ​@GetMapping​(​"/message"​)
                ​@ResponseBody
                ​public​ String ​message​(@RequestBody Person person) {
                    ​return​ ​"hello "​ + person.getName();
                }

            }
            ```

        -   `@ResponseBody`

            ```java
            @RestController
            public​ ​class​ ​SampleController​ {

                ​@GetMapping​(​"/message"​)
                ​public​ String ​message​(@RequestBody String body) {
                ​   return​ body + ​" glory"​;
                }

            }
            ```

    -   HTTP Message Converter는 RequestHeader의 Content-Type에 따라 결정된다.
    -   기본으로 제공하는 Converter : ByteArray, String, Resource, Form, JAXB2, Jackson2, Jackson, Gson, Atom, RSS

        ```java
        // 기본적으로 제공하는 String Converter
        @Test
        ​public​ ​void​ ​stringMessage​() ​throws​ Exception {
            ​this​.mockMvc.perform(get(​"/message"​).content(​"hello "​))
                        .andDo(print())
                        .andExpect(status().isOk())
                        .andExpect(content().string(​"hello glory"​));
        }
        ```

-   HTTP Message Converter 등록하기

    1. `extendMessageConverters` : 기본적으로 등록해주는 컨버터에 새로운 컨버터 추가하기
    2. `configureMessageConverters` : 기본적으로 등록해주는 컨버터를 다 없애고 새로운 컨버터 추가

        ```java
        @Configuration
        public​ ​class​ ​WebConfig​ ​implements​ ​WebMvcConfigurer​ {

            ​@Override
            ​public​ ​void​ ​extendMessageConverters​(List<HttpMessageConverter<?>> converters) {

            }

            ​@Override
            ​public​ ​void​ ​configureMessageConverters​(List<HttpMessageConverter<?>> converters) {

            }

        }
        ```

    3. 의존성 추가로 Converter 등록하기 : `@EnableWebMvc`를 설정했을 때 의존성에 따라 HTTP Mesage Converter가 자동으로 등록된다.

-   HTTP Message Converter로 JSON 다루기

    -   스프링 부트를 사용하는 경우 기본적으로 jacksonJSON2가 의존성에 포함되어 있다.
    -   컨트롤러 만들기

        -   컨트롤러에 `@RequestBody`로 JSON을 받아 이를 객체 `person`으로 변환하고, 이를 다시 `person`으로 리턴하여 JSON으로 출력하고자 한다.

            ```java
            @RestController
            public​ ​class​ ​SampleController​ {

                //...

                ​@GetMapping​(​"/jsonMessage"​)
                ​public​ Person ​jsonMessage​(@RequestBody Person person) {
                    person.setName(​"change"​);
                ​    return​ person;
                }

            }
            ```

            ```java
            @ExtendWith​(SpringExtension.class)
            @SpringBootTest
            @AutoConfigureMockMvc
            class​ ​SampleControllerTest​ {

                ​@Autowired
                MockMvc mockMvc;

                ​@Autowired
                ObjectMapper objectMapper;

                ​@Test
                ​public​ ​void​ ​jsonMessage​() ​throws​ Exception {

                    Person person = ​new​ Person();
                    person.setId(​1l​);
                    person.setName(​"glory"​);

                    String jsonString = objectMapper.writeValueAsString(person);

                    ​this​.mockMvc.perform(get(​"/jsonMessage"​)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .content(jsonString))
                                .andDo(print())
                                .andExpect(status().isOk())
                                .andExpect(jsonPath(​"$.name"​).value(​"change"​));
                }
            }
            ```

## 스프링 MVC 활용 : 요청 맵핑하기

### HTTP Method (CRUD)

#### GET (R)

-   클라이언트가 서버의 리소스를 요청할 때 사용
-   캐싱, 북마크 기능
-   브라우저 기록에 남음. 민감 데이터에 사용하면 안됨.
-   응답을 보낼 때 캐시와 관련된 헤더를 응답에 넣어서 보낼 수 있다.

#### POST (U)

-   클라이언트가 서버의 리소스를 수정하거나 새로 만들 때 사용
    -   **POST의 URI는 보내는 데이터를 처리할 리소스를 지칭함.**
    -   즉, POST 요청을 보낼 때마다 다른 결과값이 나온다.
-   캐싱, 북마크 불가능
-   브라우저 기록에 남지 않음

#### PUT (C)

-   URI에 해당하는 데이터를 새로 만들거나 수정할 때 사용한다.
    -   **PUT의 URI는 보내는 데이터에 해당하는 리소스를 지칭함.**
    -   즉, PUT 요청을 보낼 때마다 같은 결과값이 나온다. **(idempotent)**

#### PATCH

-   모든 기능은 PUT과 동일하나, 기존 entity와 보내려는 새 데이터의 차이나는 점만 전송함.

#### DELETE (D)

-   URI에 해당하는 리소스를 삭제할 때 사용

#### 스프링 웹 MVC에서 제공하는 HTTP Method

##### HEAD

-   클라이언트는 응답 본문을 받지 않고 응답 헤더만 받아온다.
-   나머지는 GET과 동일

##### OPTIONS

-   사용할 수 있는 HTTP Method를 제공한다.
-   서버 또는 특정 리소스가 제공하는 HTTP Method를 확인
-   보통 서버나 리소스에 핑을 보내 사용할 수 있는 지 확인하는 용도

### 스프링 웹 MVC에서 HTTP Method 맵핑하기

-   `@RequestMapping(method={RequestMethod.GET, RequestMethod.POST})`
-   `@GetMapping`, `@PostMapping`, ...

```java
@Controller
public​ ​class​ ​SampleController​ {

    ​@GetMapping​(​"/hello"​)
    ​@ResponseBody
    ​// @ResponseBody : 요청 응답 본문에 return 값을 작성해줌.
    ​public​ String ​hello​() {
        ​return​ ​"hello"​;
    }

}
```

```java
@RunWith​(SpringRunner.class)
@WebMvcTest
public​ ​class​ ​SampleControllerTest​ {

    ​@Autowired
    MockMvc mockMvc;

    ​@Test
    ​public​ ​void​ ​helloTest​() ​throws​ Exception {
    mockMvc.perform(get(​"/hello"​))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(​"hello"​));
    }

}
```

### URI 패턴

1. 배열로 여러 URI 패턴 한 번에 처리하기

    ```java
    @Controller
    public​ ​class​ ​SampleController​ {

        ​@GetMapping​({​"/hello"​, ​"/hi"​})
        ​@ResponseBody
        ​public​ String ​hello​() {
        ​   return​ ​"hello"​;
        }

    }
    ```

2. 요청 식별자로 맵핑하기

    - `?` : 한 글자
    - `*` : 여러 글자
    - `**` : 여러 패스

3. 클래스에 선언한 `@RequestMapping`과 조합

    - 한 클래스 내에 존재하는 모든 `@*Mapping`에 prefix를 넣고 싶을 때 사용

    ```java
    // "/hello/**" 처럼 사용됨
    @Controller
    @RequestMapping​(​"/hello"​)
    public​ ​class​ ​SampleController​ {

        ​@RequestMapping​(​"/**"​)
        ​@ResponseBody
        ​public​ String ​hello​() {
            ​return​ ​"hello"​;
        }

    }
    ```

4. 정규식 표현으로 매핑

    ```java
    @Controller
    @RequestMapping​(​"/hello"​)
    public​ ​class​ ​SampleController​ {

        ​@RequestMapping​(​"/{name:[a-z]+}"​)
        ​@ResponseBody
        ​public​ String ​hello​(@PathVariable String name) {
        ​   return​ ​"hello "​ + name;
        }

    }
    ```

5. 패턴이 중복되는 경우 가장 구체적인 핸들러로 처리한다.

    ```java
    @Controller
    @RequestMapping​(​"/hello"​)
    public​ ​class​ ​SampleController​ {

        ​/*
            아래 ** 보다 /glory로 하는 것이 더 구체적이므로,
            "/hello/glory"라는 URI 패턴이 들어오게 되면 ​helloGlory()로 처리하게 됨
        */
        ​@RequestMapping​(​"/glory"​)
        ​@ResponseBody
        ​public​ String ​helloGlory​() {
            ​return​ ​"hello, specific "​ + name;
        }

        ​@RequestMapping​(​"/**"​)
        ​@ResponseBody
        ​public​ String ​hello​() {
            ​return​ ​"hello "​ + name;
        }

    }
    ```

### 미디어 타입

1. 특정 타입의 데이터를 담고 있는 요청만 처리하는 컨트롤러
    - `consumes = MediaType.*`
2. 특정 타입의 응답을 만드는 컨트롤러
    - `produces = MediaType.*`

```java
@Controller
public​ ​class​ ​SampleController​ {

    ​@GetMapping​(
        value = ​"/hello"​,
        consumes = MediaType.APPLICATION_JSON_UTF8,
        produces = MediaType.APPLICATION_JSON_UTF8
    )
    ​@ResponseBody
    ​public​ String ​hello​() {
        ​return​ ​"hello"​;
    }

}
```

```java
@RunWith​(SpringRunner.class)
@WebMvcTest
public​ ​class​ ​SampleControllerTest​ {

    ​@Autowired
    MockMvc mockMvc;

    ​@Test
    ​public​ ​void​ ​helloTest​() ​throws​ Exception {
    mockMvc.perform(get(​"/hello"​)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().string(​"hello"​));
    }

}
```

### Header Mapping

1. 특정 헤더가 있는 요청을 처리하고 싶은 경우

    - `@RequestMapping(header = "key")`
    - 예시 : `header = HttpHeaders.FROM`

2. 특정 헤더가 없는 요청을 처리하고 싶은 경우

    - `@RequestMapping(header = "!key")`
    - 예시 : `header = "!" + HttpHeaders.FROM`

3. 특정 헤더 key/value가 있는 요청을 처리하고 싶은 경우

    - `@RequestMapping(header = "key=value")`
    - 예시 : `header = HttpHeaders.AUTHORIZATION + "=" + "111"`

### Parameter Mapping

-   여기서 언급되는 파라미터 : 요청 매개변수 / Query Parameter의 값을 의미한다.

1. 특정 파라미터 키를 가지고 있는 요청을 처리하고 싶은 경우

    - `@RequestMapping(params = "key")`
    - 예시 : `params = "name"`

2. 특정 파라미터가 없는 요청을 처리하고 싶은 경우

    - `@RequestMapping(params = "!key")`
    - 예시 : `params = "!name"`

3. 특정 파라미터 key/value를 가지고 있는 요청을 처리하고 싶은 경우
    - `@RequestMapping(header = "key")`
    - 예시 : `params = "key1=value1"`

## 스프링 MVC 활용 - 핸들러 메서드

1. Handler Method Argument
    - 주로 요청 그 자체 또는 요청에 들어있는 정보를 받아오는 데 사용
    - `@PathVariable`, `@MatrixVariable`, `@RequestParam`, `@RequestHeader`
2. Handler Method Return
    - `@ResponseBody`, `@ModelAttribute`, `HttpEntity`, `ResponseEntity`, `String`, `View`, `Map`, `Model`, ...

### URI 패턴으로부터 값 가져오기

#### @PathVariable

-   요청 URI 패턴의 일부를 핸들러 메소드 아규먼트로 받는 방법
-   타입 변환 지원 (string to int, ...)
-   default값이 존재해야 한다.
-   Optional 설정 가능
    -   `@PathVariable(require = false)` : 없어도 됨
    -   `@PathVariable(require = true)` : 반드시 있어야 함. 기본값.

```java
@Controller
public​ ​class​ ​SampleController​ {

    ​@GetMapping​(​"/events/{id}"​)
    ​@ResponseBody
    ​public​ Event ​getEvent​(@PathVariable Integer id) {
        Event event = ​new​ Event();
        event.setId(id);
        ​return​ event;
    }

}
```

```java
@RunWith​(SpringRunner.class)
@WebMvcTest
public​ ​class​ ​SampleContollerTest​ {

    ​@Autowired
    MockMvc mockMvc;

    ​@Test
    ​public​ ​void​ ​deleteEvents​() ​throws​ Exception {
    mockMvc.perform(get(​"/events/1"​))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath(​"id"​).value(​1​));
    }
}
```

#### @MatrixVariable

-   요청 URI 패턴에서 key/value 쌍의 데이터를 아규먼트로 받는 방법
    -   예시 : `/events/1;name=glory` 에서 `name=glory`를 가져올 수 있음
-   타입 변환 지원
-   기본값 필수
-   Optional 지원
    -   `@MatrixVariable(require = false)` : 없어도 됨
    -   `@MatrixVariable(require = true)` : 반드시 있어야 함. 기본값.
-   사용하기 위해서 Configuration 설정이 필요하다

    ```java
    @Configuration
    public​ ​class​ ​WebConfig​ ​implements​ ​WebMvcConfigurer​ {

        ​@Override
        ​public​ ​void​ ​configurePathMatch​(PathMatchConfigurer configurer) {
            UrlPathHelper urlPathHelper = ​new​ UrlPathHelper();
            urlPathHelper.setRemoveSemicolonContent(​false​);
            configurer.setUrlPathHelper(urlPathHelper);
        }

    }
    ```

```java
@Controller
public​ ​class​ ​SampleController​ {

    ​@GetMapping​(​"/events/{id}"​)
    ​@ResponseBody
    ​public​ Event ​getEvent​(@PathVariable Integer id, @MatrixVariable String name) {
        Event event = ​new​ Event();
        event.setId(id);
        ​return​ event;
    }

}
```

```java
@RunWith​(SpringRunner.class)
@WebMvcTest
public​ ​class​ ​SampleContollerTest​ {

    ​@Autowired
    MockMvc mockMvc;

    ​@Test
    ​public​ ​void​ ​deleteEvents​() ​throws​ Exception {
    mockMvc.perform(get(​"/events/1;name=glory"​))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath(​"id"​).value(​1​));
    }

}
```

### 요청 매개변수 받아오기

-   요청 매개변수
    -   쿼리 파라미터
        -   URI 패턴 뒤에 붙는 `?name=glory&gender=male`과 같은 경우
    -   폼 데이터
        -   클라이언트가 서버로 HTML 문서의 submit 버튼을 통해 폼에 있던 데이터를 전송받는 경우

#### @RequestParam

-   요청 매개변수에 들어있는 **단순 타입 데이터** 를 메소드 아규먼트로 받아올 수 있다.
-   Optional 지원
    -   `@RequestParam(require = false)` : 없어도 됨
    -   `@RequestParam(require = false, defaultValue = "glory")` : 값이 없다면 기본값을 `glory`로 설정
-   `Map<String, String>` 또는 `MultiValueMap<String, String>`으로 모든 요청 매개변수를 받아올 수 있다.

```java
@Controller
public​ ​class​ ​SampleController​ {

    ​@GetMapping​(​"/events"​)
    ​@ResponseBody
    ​public​ Event ​getEvent​(@RequestParam String name) {
        Event event = ​new​ Event();
        event.setName(name);
        ​return​ event;
    }

}
```

```java
@RunWith​(SpringRunner.class)
@WebMvcTest
public​ ​class​ ​SampleContollerTest​ {

    ​@Autowired
    MockMvc mockMvc;

    ​@Test
    ​public​ ​void​ ​deleteEvents​() ​throws​ Exception {
    mockMvc.perform(get(​"/events"​)
                .param(​"name"​, glory))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath(​"name"​).value(​"glory"​));
    }

}
```

#### @ModelAttribute

-   여러 곳에 있는 단순 타입 데이터를 복합 타입 객체로 받아올 때(Binding), 또는 해당 객체를 새로 만들 때 사용
-   여러 곳에 있는 단순 타입 데이터를 URI 패턴, 요청 매개변수, 세션 등에서 가져올 수 있다.

```java
@Controller
public​ ​class​ ​SampleController​ {

    ​@GetMapping​(​"/events/form"​)
    ​public​ String ​eventsForm​(Model model) {
        Event newEvent = ​new​ Event();
        newEvent.setLimit(​50​);

        model.addAttribute(​"event"​, newEvent());
        ​return​ ​"events/form"​;
    }

    ​@PostMapping​(​"/events"​)
    ​@ResponseBody
    ​public​ Event ​postEvent​(@ModelAttribute Event event) {
        ​return​ event;
    }

}
```

```java
@RunWith​(SpringRunner.class)
@WebMvcTest
public​ ​class​ ​SampleContollerTest​ {

    ​@Autowired
    MockMvc mockMvc;

    ​@Test
    ​public​ ​void​ ​eventForm​() ​throws​ Exception {
        mockMvc.perform(get(​"/events/form"​))
                .andDo(print())
                .andExpect(view().name(​"/events/form"​))
                .andExpect(model().attributeExists(​"events"​));
    }

    ​@Test
    ​public​ ​void​ ​postEvent​() ​throws​ Exception {
        mockMvc.perform(post(​"/events"​)
                    .param(​"name"​, ​"glory"​)
                    .param(​"limit"​, ​"20"​))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath(​"name"​).value(​"glory"​));
    }

}
```

-   값을 바인딩할 수 없는 경우

    -   예시 : int에 String 값을 넣으려고 하는 경우
    -   400 Error (BindException) 발생
    -   `bindingResult`를 출력해서 무슨 오류인지 확인하기

        ```java
        @Controller
        public​ ​class​ ​SampleController​ {

            ​@PostMapping​(​"/events"​)
            ​@ResponseBody
            ​public​ Event ​postEvent​(@ModelAttribute Event event, BindingResult bindResult) {
                ​if​(bindingReuslt.hasError()) {
                    System.out.println(​"===================="​);
                    bindingResult.getAllErrors().forEach(c -> {
                        System.out.println(c.toString());
                    });
                }
                ​return​ event;
            }

        }
        ```

        ```java
        @RunWith​(SpringRunner.class)
        @WebMvcTest
        class​ ​SampleControllerTest​ {

            ​@Autowired
            MockMvc mockMvc;

            ​@Test
            ​public​ ​void​ ​eventsTest​() ​throws​ Exception{
            mockMvc.perform(post(​"/events?name=hahaha&id=notnum"​))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath(​"name"​).value(​"hahaha"​));
            }

            // 요청 매개변수에 id가 String으로 들어왔지만, bindingResult 덕에 테스트는 통과된다.
        }
        ```

    -   JPA를 활용하여 값 검증 작업하기

        -   `@Valid`로 `Event` 객체 내에 선언되어 있는 JPA 애노테이션을 검증한다.
        -   JPA 애노테이션에 따라 `Event`를 받아올 수 없는 경우, 바인딩하지 않고 바인딩 오류가 발생되어 `bindingResult`에 저장된다.

        ```java
        @Controller
        public​ ​class​ ​SampleController​ {

            ​@PostMapping​(​"/events"​)
            ​@ResponseBody
            ​public​ Event ​postEvent​(@Valid @ModelAttribute Event event, BindingResult bindResult) {
                ​if​(bindingReuslt.hasError()) {
                    bindingResult.getAllErrors().forEach(c -> {
                        System.out.println(c.toString());
                    });
                }
                ​return​ event;
            }

        }
        ```

        ```java
        public​ ​class​ ​Event​ {

            Integer id;
            ​
            ​@NotBlank // name은 NULL이면 안된다.
            String name;

            ​@Min​(​0​) ​// limit은 0보다 크거나 같아야 한다.
            Integer limit;

            // getter, setter 생략
        }
        ```

        -   `@Validated` : `@Valid`의 확장

            -   JPA 애노테이션끼리 그룹을 만들 수 있고, 그 그룹에 해당하는 JPA 애노테이션만 검증 체크할 수 있다.

            ```java
            @Controller
            public​ ​class​ ​SampleController​ {
                ​@PostMapping​(​"/events"​)
                ​@ResponseBody
                ​public​ Event ​postEvent​(@Validated(Event.ValidateName.class) @ModelAttribute Event event, BindingResult bindResult) {
                    ​if​(bindingReuslt.hasError()) {
                        bindingResult.getAllErrors().forEach(c -> {
                            System.out.println(c.toString());
                        });
                    }
                    ​return​ event;
                }
            }
            ```

            ```java
            public​ ​class​ ​Event​ {

                ​interface​ ​ValidateLimit​ {}

                ​interface​ ​ValidateName​ {}

                Integer id;

                ​@NoBlank​(groups = ValidateName.class)
                String name;

                ​@Min​(value = ​0​, groups = ValidateLimit.class)
                Integer limit;

            }
            ```

### @SessionAttributes

-   모델 정보를 HTTP Session에 저장해주는 애노테이션
-   애노테이션에 설정한 이름에 해당하는 모델 정보를 자동으로 세션에 넣어준다.
-   여러 화면 또는 여러 요청으로부터 값을 받고 사용하는 객체를 사용할 때 사용한다.
-   `SessionStatus`를 사용해서 세션 처리 완료를 알려줄 수 있다. 즉, 폼 처리가 끝나고 세션을 비울 때 사용한다.
-   ![image](https://user-images.githubusercontent.com/52440668/91411059-c6fbba80-e882-11ea-825f-941865dadaa5.png)

```java
@Controller
@SessionAttributes​(​"event"​)
public​ ​class​ ​SampleController​ {

    ​@GetMapping​(​"/events/form"​)
    ​public​ String ​eventsForm​(Model model) {
        Event newEvent = ​new​ Event();
        newEvent.setLimit(​50​);
        model.addAttribute(​"event"​, newEvent); ​// httpSession.setAttribute("event", newEvent);
        ​return​ ​"/events/form"​;
    }

    ​@PostMapping​(​"/events"​)
    ​@ResponseBody
    ​public​ String ​createEvent​(@Validated @ModelAttribute Event event, BindingResult bindResult, SessionStatus sessionStatus) {
        ​if​(bindingReuslt.hasError()) {
        ​   return​ ​"/events/form"​;
        }
        sessionStatus.setComplete();
        List<Event> eventList = ​new​ ArrayList<>();
        eventList.add(event);
        model.addAttribute(​"eventList"​, eventList); ​// model.addAttribute(eventList); 와 같은 코드
        ​return​ ​"/events/list"​;
    }

}
```

```java
@RunWith​(SpringRunner.class)
@WebMvcTest
public​ ​class​ ​SampleContollerTest​ {

    ​@Autowired
    MockMvc mockMvc;

    ​@Test
    ​public​ ​void​ ​eventForm​() ​throws​ Exception {
        mockMvc.perform(get(​"/events/form"​))
                .andDo(print())
                .andExpect(view().name(​"/events/form"​))
                .andExpect(model().attributeExists(​"event"​))
                .andExpect(request().sessionAttribute(​"event"​, notNullValue()))
                .andReturn().getRequest();

        Object event = request().getSession().getAttribute(​"event"​);
        System.out.println(event);
    }

}
```

-   멀티 폼 서브밋
    1. `/events/form/name`에서 `name`을 입력받는다.
    2. `/events/form/name`에서 세션에 등록된 `Event`를 `@ModelAttribute`를 통해 받는다.
    3. `/events/form/limit`에서 redirect하여 `limit`을 입력받는다.
    4. `limit`을 입력하여 submit하면 Event List Page로 이동한다.

```java
@Controller
@SessionAttributes​(​"event"​)
public​ ​class​ ​SampleController​ {

    ​@GetMapping​(​"/events/form/name"​)
    ​public​ String ​eventsForm​(Model model) {
        Event newEvent = ​new​ Event();
        newEvent.setLimit(​50​);
        model.addAttribute(​"event"​, newEvent);
        ​return​ ​"/events/form/name"​;
    }

    ​@PostMapping​(​"/events/form/name"​)
    ​public​ String ​eventsFormNameSubmit​(@Validated @ModelAttribute Event event, BindingResult bindResult) {
        ​if​(bindingReuslt.hasError()) {
            ​return​ ​"/events/form-name"​;
        }
        ​return​ ​"redirect:/events/form/limit"​;
    }

    ​@GetMapping​(​"/events/form/limit"​)
    ​public​ String ​eventFromLimit​(@ModelAttribute Event event, Model model) {
        model.addAttribute(​"event"​, event);
        ​return​ ​"/events/form/limit"​;
    }

    ​@PostMapping​(​"/events/form/limit"​)
    ​public​ String ​eventsFormLimitSubmit​(@Validated @ModelAttribute Event event, BindingResult bindResult, SessionStatus sessionStatus) {
        ​if​(bindingReuslt.hasError()) {
        ​   return​ ​"/events/form-limit"​;
        }
        sessionStatus.setComplete();
        ​return​ ​"redirect:/events/list"​;
    }

    ​@GetMapping​(​"events/list"​)
    ​public​ String ​getEvents​(Model model) {
        Event event = ​new​ Event();
        event.setName(​"spring"​);
        event.setLimit(​10​);

        List<Event> eventList = ​new​ ArrayList<>();
        eventList.add(event);
        model.addAttribute(eventList);
        ​return​ ​"/events/list"​;
    }
}
```

```html
<!-- form-name.html -->
<!DOCTYPE html>
<​html​ lang=​"en"​ xmlns:th=​"http://www.thymeleaf.org"​>
<​head​>
    ​<​meta​ charset=​"UTF-8"​>
    ​<​title​>​Create New Event​</​title​>
</​head​>
<​body​>
    <​form​ action=​"#"​ th:action=​"@{/events/form/name}"​ method=​"post" th:object=​"${event}"​>
        ​<​p​ th:if=​"${#fields.hasErrors('name')}" th:errors=​"*{name}"​>​Incorrect name​</​p​>
        Name : ​<​input​ type=​"text"​ title=​"name"​ th:field=​"*{name}"​/>
        ​<​input​ type=​"submit"​ value=​"Create"​/>
    </​form​>
</​body​>
</​html​>
```

```html
<!-- form-limit.html -->
<!DOCTYPE html>
<​html​ lang=​"en"​ xmlns:th=​"http://www.thymeleaf.org"​>
<​head​>
    ​<​meta​ charset=​"UTF-8"​>
    ​<​title​>​Create New Event​</​title​>
</​head​>
<​body​>
    <​form​ action=​"#"​ th:action=​"@{/events/form/limit}"​ method=​"post" th:object=​"${event}"​>
        ​<​p​ th:if=​"${#fields.hasErrors('limit')}" th:errors=​"*{limit}"​>​Incorrect date​</​p​>
        Limit : ​<​input​ type=​"text"​ title=​"limit"​ th:field=​"*{limit}"​/>
        <​input​ type=​"submit"​ value=​"Create"​/>
    </​form​>
</​body​>
</​html​>
```

```html
<!-- list.html -->
<!DOCTYPE html>
<​html​ lang=​"en"​ xmlns:th=​"http://www.thymeleaf.org"​>
<​head​>
    ​<​meta​ charset=​"UTF-8"​>
    ​<​title​>​Event list​</​title​>
</​head​>
<​body​>
    <​a​ th:href=​"@{/events/form}"​>​Create New Event​</​a​>
    <​div​ th:unless=​"${#lists.isEmpty(eventList)}"​>
        ​<​ul​ th:each=​"event: ${eventList}"​>
            ​<​p​ th:text=​"${event.Name}"​>​Event Name​</​p​>
        ​</​ul​>
    </​div​>
</​body​>
</​html​>
```

### @SessionAttribute

-   세션에 있는 값을 참조하거나 읽어옴.
-   `@SessionAttributes`는 해당 컨트롤러 안에서 다루는 특정 모델 객체를 세션에 넣고 공유할 때만 사용하지만, `@SessionAttribute`는 다른 컨트롤러의 세션 값이나 인터셉터, 필터 등에서 값을 가져올 수 있다.

### RedirectAttributes

-   redirect할 때 모델에 들어있는 primitive type data는 URI 쿼리 파라미터에 추가된다.
-   스프링 부트에서는 기본적으로 비활성화되어 있으므로, `application.properties`에 `spring.mvc.ignore-default-model-on-redirect=false`를 추가한다.
-   **원하는 값만 redirect하고 싶다면 Model 대신에 RedirectAttributes를 사용. 원하는 값을 명시적으로 추가할 수 있다.**
    -   명시적으로 추가한 데이터를 받는 컨트롤러에서
        -   `@RequestParam`을 이용해 **하나씩** 받아오기
        -   `@ModelAttribute`를 이용해 **모두** 받아오기

```java
@Controller
@SessionAttributes​(​"event"​)
public​ ​class​ ​SampleController​ {

    ​@GetMapping​(​"/events/form/name"​)
    ​public​ String ​eventsForm​(Model model) {
        Event newEvent = ​new​ Event();
        newEvent.setLimit(​50​);
        model.addAttribute(​"event"​, newEvent);
        ​return​ ​"/events/form/name"​;
    }

    ​@PostMapping​(​"/events/form/name"​)
    ​@ResponseBody
    ​public​ String ​eventsFormNameSubmit​(@Validated @ModelAttribute Event event, BindingResult bindResult) {
        ​if​(bindingReuslt.hasError()) {
            ​return​ ​"/events/form-name"
        }
        ​return​ ​"redirect:/events/form/limit"​;
    }

    ​@GetMapping​(​"/events/form/limit"​)
    ​public​ String ​eventFromLimit​(@ModelAttribute Event event, Model model) {
        model.addAttribute(​"event"​, event);
        ​return​ ​"/events/form/limit"​;
    }

    ​@PostMapping​(​"/events/form/limit"​)
    ​@ResponseBody
    ​public​ String ​eventsFormLimitSubmit​(@Validated @ModelAttribute Event event, BindingResult bindResult, SessionStatus sessionStatus, RedirectAttributes RedirectAttributes attributes) {
        ​if​(bindingReuslt.hasError()) {
            ​return​ ​"/events/form-limit"
        }
        sessionStatus.setComplete();

        ​// RedirectAttributes에 Attribute 추가
        attributes.addAttribute(​"name"​, event.getName());
        attributes.addAttribute(​"limit"​, event.getLimit());
        ​return​ ​"redirect:/events/list"​;
    }

    ​@GetMapping​(​"events/list"​)
    ​public​ String ​getEvents​(Model model) {
        Event event = ​new​ Event();
        event.setName(​"spring"​);
        event.setLimit(​10​);

        List<Event> eventList = ​new​ ArrayList<>();
        eventList.add(event);
        model.addAttribute(eventList);
        ​return​ ​"/events/list"​;
    }
}
```

### FlashAttributes

-   `RedirectAttributes`를 통해 전달하는 데이터는 쿼리 파라미터로 전달되므로 전부 문자열로 변환 가능해야한다. 따라서 복합 객체를 넘기기에는 부적합하다.
-   `FlashAttributes`는 redirect시 객체 넘기기가 가능하고, 요청이 지난 후 세션에서 자동으로 제거되는 일회성을 가지고 있다.
-   `FlashAttributes`로 넘긴 데이터는 redirect된 곳에서 `@ModelAttributes`로 받을 수 있지만, `Model` 객체를 이용하여 데이터를 받을 수 있다.

```java
@Controller
@SessionAttributes​(​"event"​)
public​ ​class​ ​SampleController​ {

    ​@GetMapping​(​"/events/form/name"​)
    ​public​ String ​eventsForm​(Model model) {
        Event newEvent = ​new​ Event();
        newEvent.setLimit(​50​);
        model.addAttribute(​"event"​, newEvent);
        ​return​ ​"/events/form/name"​;
    }

    ​@PostMapping​(​"/events/form/name"​)
    ​@ResponseBody
    ​public​ String ​eventsFormNameSubmit​(@Validated @ModelAttribute Event event, BindingResult bindResult) {
        ​if​(bindingReuslt.hasError()) {
        ​   return​ ​"/events/form-name"
        }
        ​return​ ​"redirect:/events/form/limit"​;
    }

    ​@GetMapping​(​"/events/form/limit"​)
    ​public​ String ​eventFromLimit​(@ModelAttribute Event event, Model model) {
        model.addAttribute(​"event"​, event);
        ​return​ ​"/events/form/limit"​;
    }

    ​@PostMapping​(​"/events/form/limit"​)
    ​@ResponseBody
    ​public​ String ​eventsFormLimitSubmit​(@Validated @ModelAttribute Event event, BindingResult bindResult, SessionStatus sessionStatus, RedirectAttributes attributes) {
        ​if​(bindingReuslt.hasError()) {
            ​return​ ​"/events/form-limit"
        }
        sessionStatus.setComplete();

        ​// Flash Attributes를 이용한 Attribute 추가
        attributes.addFlashAttributes(​"newEvent"​, event());
        ​return​ ​"redirect:/events/list"​;
    }

    ​@GetMapping​(​"events/list"​)
    ​public​ String ​getEvents​(Model model) {
        Event event = ​new​ Event();
        event.setName(​"spring"​);
        event.setLimit(​10​);

        ​// Model 객체를 이용하여 데이터 받기
        Event newEvent = (Event) model.asMap().get(​"newEvent"​);
        List<Event> eventList = ​new​ ArrayList<>();
        eventList.add(event);
        eventList.add(newEvent);
        model.addAttribute(eventList);
        ​return​ ​"/events/list"​;
    }
}
```

### @RequestBody

-   요청 본문에 들어있는 데이터를 `HttpMessageConverter`를 통해 변환한 객체로 받아올 수 있다.
    -   빈으로 등록된 `MessageConverter`들은 `HandlerAdapter`가 아규먼트를 resolving할 때 적절한 `Converter`를 선택해서 사용한다.
-   `@Valid`나 `@Validated`를 사용해서 값 검증을 할 수 있다.

```java
@RestController
@RequestMapping​(​"/api/events"​)
public​ ​class​ ​EventApi​ {

    ​@PostMapping
    ​public​ Event ​createEvent​(@RequestBody @Valid Event event, BindingResult bindingResult) {
        ​// save event
        ​if​(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println(error);
            });
        }

        ​return​ event;
    }
}
```

```java
@RunWith​(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public​ ​class​ ​EventApiTest​ {

    ​@Autowired
    ObjectMapper objectMapper;

    ​@Autowired
    MockMvc mockMvc;

    ​@Test
    ​public​ ​void​ ​createEvent​() ​throws​ Exception {
        Event event = ​new​ Event();
        event.setName(​"glory"​);
        event.setLimit(​20​);
        String json = objectMapper.writeValueAsString(event);

        mockMvc.perform(post(​"/api/events"​)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(json))
                .andDo(print())
                .andExpect(status().isOk)
                .andExpect(jsonPath(​"name"​).value(​"glory"​))
                .andExpect(jsonPath(​"limit"​).value(​20​));
    }
}
```

### @ModelAttribute의 다른 사용법

-   기존 사용법
    -   여러 곳에 있는 단순 타입 데이터를 복합 타입 객체로 받아오거나 해당 객체를 새로 만들 때 사용
-   새로운 사용법
    -   `@Controller`나 `@ControllerAdvice`를 사용한 클래스에서 모델 정보를 초기화할 때 사용
    -   모든 핸들러에서 공통적으로 참조해야 하는 model 정보가 있는 경우 사용한다.

```java
// 첫 번째 방식
@Controller
public​ ​class​ ​EventController​ {

    ​@ModelAttribute
    ​public​ ​void​ ​categories​(Model model) {
        model.addAttribute(​"categories"​, list.of(​"study"​, ​"seminar"​, "hobby"​, ​"social"​));
    }
}

// 두 번째 방식
@Controller
public​ ​class​ ​EventController​ {

    ​@ModelAttribute​(​"categories"​)
    ​public​ List<String> ​categories​(Model model) {
    ​   return​ list.of(​"study"​, ​"seminar"​, ​"hobby"​, ​"social"​);
    }

}
```

-   `@RequestMapping`과 같이 사용하면 해당 메서드를 리턴하는 객체를 모델에 넣어준다.

    ```java
    @Controller
    public​ ​class​ ​EventController​ {

        ​@ModelAttribute​(​"categories"​)
        ​public​ List<String> ​categories​(Model model) {
        ​   return​ list.of(​"study"​, ​"seminar"​, ​"hobby"​, ​"social"​);
        }

        ​@GetMapping​(​"/events/form/name"​)
        ​@ModelAttribute​ ​// @ModelAttribute Annotation 생략 가능
        ​public​ Event ​eventsFormName​() {
        ​   return​ ​new​ Event();
        }
    }
    ```

### 데이터 바인더 @InitBinder

-   특정 컨트롤러에서 바인딩 또는 검증 설정을 변경하고 싶을 때 사용
-   그 컨트롤러에 대한 모든 요청 전에 `@InitBinder`가 부여된 메서드가 실행된다.
-   바인딩 설정

    ```java
    @Controller
    public​ ​class​ ​EventController​ {

        ​@InitBinder
        ​public​ ​void​ ​initEventBinder​(WebDataBinder webDataBinder) {
            webDataBinder.setDisallowedFileds(​"id"​);
            ​// 받고 싶지 않은 field 설정 (blacklist)
            ​// 해당 field를 입력 받더라도 바인딩하지 않는다.

            ​// webDataBinder.setAllowedFields (whitelist 방식)
        }

    }
    ```

-   Formatter 설정

    ```java
    public​ ​class​ ​Event​ {

        ​@DateTimeFormat​(iso = DateTimeFormat.iso.DATE)
        ​private​ LocalDate startDate;
        ​// 이러한 변환이 이루어지는 이유는 해당 역할 할 수 있는 formatter 기본적으로 들어있기 때문이다.

        // getter, setter 생략
    }
    ```
