# Thymeleaf (Getting started with the Standard dialects in 5 minutes)
> 다음 [링크](https://www.thymeleaf.org/doc/articles/standarddialect5minutes.html)를 번역 및 필자가 이해한 대로 내용을 추가함

## Standard dialects?
* Thymeleaf는 확장성이 매우 좋아서 template attribute의 집합을 정의 및 커스터마이징할 수 있다. 커스터마이징의 예로 개발자가 원하는 대로 attribute의 이름을 지을 수 있고, 원하는 syntax에서 원하는 expression을 평가하고 원하는 logic을 적용할 수 있다. Thymeleaf는 template engine framework에 가깝다.
* Thymeleaf는 standard dialect와 함께 쓰인다. _Stadard_ and _SpringStandard_ 라고 불린다. html 문서 내에 다른 template의 문법이나 html tag와 함께 쓰여도, 접두사 th를 갖는 속성은 모두 Thymeleaf이기에, standard dialect를 통해 Thymeleaf가 tempalte에 사용되는 시기를 확인할 수 있다.
  * 예시
    ```html
    <span th:text="...">
    ```
* _Stadard_ and _SpringStandard_ dialect는 _SpringStandard_ 에서 MVC 애플리케이션의 통합 과정 중 특정 상황을 제외하곤 동일하게 다뤄진다.
  * 특정 상황 : OGNL 대신 SpEL를 사용하는 애플리케이션일 경우

<br>

## Stadard Expression syntax
* 대부분의 Thymeleaf attribute는 attribute 값을 expression으로 설정하거나 포함하는 것을 가능한데, 이에 사용되는 dialect를 Standard Expression이라고 칭한다.
* 다섯가지 종류
  * ${...} : [Variable Expression](#variable-expression)
  * *{...} : [Selection Expression](#selection-expression)
  * \#{...} : [Message Expression](#message-expression)
  * @{...} : [Link Expression](#link-expression)
  * ~{...} : [Fragment Expression](#fragment-expression)
  
### Variable Expression
* (model attribute라고도 불리는) 변수 표현식은 context variable에서 실행되는 OGNL 표현식, 또는 Thymeleaf 통합 과정 중 Spring EL이다.
  ```html
  ${session.user.name}
  ```
* 변수 표현식은 attribute의 type에 따라 attribute의 value가 될 수도 있고, attribute의 한 part가 될 수도 있다.
  ```html
  <span th:text="${book.author.name}">
  ```
* 위의 HTML 코드는 아래 JAVA 코드와 동일하다.
  ```java
  ( (Book) context.getVariable("book")).getAuthor().getName();
  ```
* 분기나 반복 표현도 가능하다.
  ```html
  <li th:each="book : ${books}">
  ```
  * ${books}는 context에 존재하는 변수 books을 선택한다. th:each를 사용하여 books 내에 존재하는 모든 값을 book에 담아 반복 가능하게 한다.

### Selection Expression
* 선택 표현식은 변수 표현식과 비슷하다. 선택 표현식은 전체 context 변수 맵에서 지정되는 것이 아니라, 이전에 선택한 object에서 지정된다는 것만 제외하면 변수 표현식과 같다.
  ```html
  *{customer.name}
  ```
* 선택 표현식이 사용하려는 object는 attribute인 th:ojbect에 의해 지정된다
  ```html
  <div th:object="${book}">
      ...
      <span th:text="*{title}">...</span>
      ...
  </div>
  ```
* 위의 HTML 코드는 아래 JAVA 코드와 동일하다.
  ```java
  {
    // th:object="${book}"
    final Book selection = (Book) context.getVariable("book");
   
    // th:text="*{title}"
    output(selection.getTitle());
  }
  ```

### Message Expression
* 메시지 표현식은 _text externalization_, _internationalization_, _i18n_ 라고 불리기도 한다.
* 메시지 표현식을 사용하면 \*.properties와 같은 외부 소스 파일에서 locale별 메시지를 검색하여 그들을 key로 참조하고, 매개 변수의 집합을 적용(optional)할 수 있다.
* 스프링 애플리케이션에서 메시지 표현식은 스프링의 MessageSource 매커니즘과 자동으로 통합된다.
  ```html
  #{main.title}
  ```
  ```html
  #{mesage.entrycreated(${entryId})}
  ```
* template에서의 사용 예시
  ```html
  <table>
      ...
      <th th:text="#{header.address.city}">...</th>
      <th th:text="#{header.address.country}">...</th>
      ...
  </table>
  ```
* 메시지 표현식 안에 변수 표현식을 사용할 수 있다. 주로 메시지의 key가 context 변수의 값에 의해 결정되거나 특정 변수를 파라미터로 지정하고자 할 때 사용한다.
  ```html
  #{${config.adminWelcomKey}(${session.user.name})}
  ```
  
### Link Expression
* 링크 표현식이란 URL를 작성하고, URL에 유용한 context나 session 정보를 추가하기 위한 것이다. 이 과정을 _URL rewriting_ 이라고 한다.
* 웹 서버의 /myapp context에 배포된 웹 애플리케이션의 경우
    ```html
    <a th:href="@{/order/list}">...</a>
    ```
  와 같이 링크 표현식을 사용할 수 있다. Thymeleaf는 위 링크 표현식을 해석하여
    ```html
    <a href="/myapp/order/list">...</a>
    ```
  와 같이 변환한다.
  세션 및 쿠키를 사용 가능으로 설정하지 않은 경우이거나 서버가 아직 모르는 경우
    ```html
    <a href="/myapp/order/list;jsessionid=23fa31abd41ea093"></a>
    ```
  과 같은 요청을 보낸다면, 링크 표현식을 통해 위 URL을 파라미터로 받아들일 수 있다.
    ```html
    <a th:href="@{/order/details(id=${orderId},type=${orderType})}">...</a>
    ```
  아래는 링크 표현식을 통해 얻은 결과 중 하나이다.
    ```html
    <!-- 참고 : &는 tag attribute에서 escape charactor로 처리되어야 함 -->
    <a href="/myapp/order/details?id=23&amp;type=online">...</a>
    ```
* 링크 표현식의 relative link와 absolute link
  * __링크 표현식의 relative한 경우.__ 이 경우에는 URL의 접두사로 애플리케이션 context를 작성하지 않아도 된다.
    ```html
    <a th:href="@{../documents/report}">...</a>
    ```
  * __링크 표현식의 server-relative한 경우.__ URL의 접두사로 애플리케이션 context를 작성하지 않아도 된다.
    ```html
    <a th:href="@{~/contents/main}">...</a>
    ```
  * __링크 표현식의 protocol-relative한 경우.__ 아래 코드는 absolute URL처럼 보이지만, 브라우저는 페이지에서 사용되는 것과 동일한 프로토콜(HTTP or HTTPS)을 사용할 것이다.
    ```html
    <a th:href="@{//static.mycompany.com/res/initial}">...</a>
    ```
  * __링크 표현식의 absolute한 경우.__
    ```html
    <a th:href="@{http://www.mycompany.com/main}">...</a>
    ```
  * absolute나 protocol-relative URL의 경우 Thymeleaf Link Expression에 어떤 값이 더해지는가? 또는 어떤 값을 더해지게 할 수 있는가?
    * _response filter_ 가 _URL rewriting_ 의 여부를 판단한다.
    * 서블릿 기반 웹 애플리케이션에서, Thymeleaf는 애플리케이션에서 출력되는 모든 URL(context-relative, relative, absolute, ...)에 대해 URL을 표시하기 전에 항상 HttpServletResponse.encodeUrl(...)를 호출한다. _response filter_ 는 HttpServletResponse 객체를 감싸기 때문에, 애플리케이션에서 커스터마이징된 _URL rewriting_ 을 할 수 있다.

### Fragment Expression
* 단편 표현식은 markup의 단편을 나타내고, 그 단편들을 template로 옮겨주는 표현식이다. 표현식에 의해 단편들은 복제될 수 있고, argument로 다른 템플릿으로 전달될 수 있다.
* 단편 삽입을 위해 가장 많이 쓰이는 것은 th:insert와 th:replace가 있다.
  ```html
  <div th:insert="~{commons :: main}">...</div>
  ```
* 다른 변수처럼 어디에서나 사용할 수 있고, argument를 가진다.
  ```html
  <div th:with="frag=~{footer :: #main/text()}">
    <p th:insert="${frag}">
  </div>
  ```

### Literals and Opreations
* Literals (문자 그대로)
  * Text : 'one text', 'Another one!', ...
  * Number : 0, 34, 0.9, ...
  * Boolean : true, false
  * Null : null
  * Literal token : one, sometext, main, ...
* Text Operation
  * String concatenation : +
  * Literal substitutions : |The name is ${name}|
* Arithmetic Operation
  * Binary operators : +, -, *, /, %
  * Unary Operator : -
* Boolean Operation
  * Comparator : >, <, >=, <= (gt, lt, ge, le)
  * Equality operators : ==, != (eq, ne)
* Conditional Operators
  * If-then : (if) ? (then)
  * If-then-else : (if) ? (then) : (else)
  * Default : (value) ?: (defaultvalue)

### Expression Preprocessing
* _Expression Preprocessing_ 를 하고 싶은 표현식을 &#95;&#95;와 &#95;&#95;로 감싼다.
  ```html
  #{selection.__${sel.code}__}
  ```
* 변수 표현식 ${sel.code}는 제일 먼저 실행되고, 그 결과는 나중에 실행될 실제 표현식의 일부로 사용된다.
  * ${sel.code}를 _Expression Preprocessing_ 한 결과가 ALL이라고 가정하면, selection.ALL에 접근하여 메시지를 찾을 수 있다.
* 주로 internationalization에 쓰인다.

<br>

## Some Basic Attributes
* th:text : tag의 body를 교체한다.
  ```html
  <p th:text="#{msg.welcome}">Welcome everyone!</p>
  ```
* th:each : 변수 표현식에 의해 반환된 배열 또는 리스트의 원소를 접근하는 방식. 자료구조의 원소 갯수나 지정된 횟수만큼 반환할 수 있다.
  ```html
  <li th:each="book : ${books}" th:text="${book.title}">En las Orillas del Sar</li>
  ```
* 그 외
  ```html
  <form th:action="@{/createOrder}">
  ```
  ```html
  <input type="button" th:value="#{form.submit}" />
  ```
  ```html
  <a th:href="@{/admin/users}">
  ```
  
<br>

[더 많은 정보](https://www.thymeleaf.org/documentation.html)
