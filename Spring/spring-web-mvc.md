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
            ​this​.mockMvc.perform(get(​"/message"​)
                        .content(​"hello "​))
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
