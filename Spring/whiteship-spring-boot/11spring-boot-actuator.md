# 스프링 부트 Actuator
* 의존성 추가
  ```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
  ```
* Actuator는 Endpoint를 제공한다.
  * Endpoint : 애플리케이션의 각종 정보를 확인할 수 있는 ID
  * [다양한 Endpoint](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints)
  * 의존성으로 actuator를 추가하는 순간, Actuator들이 활성화된다.
    * ID=shutdown을 제외한 모든 Endpoint는 기본적으로 활성화
    * 활성화 옵션 조정
      * management.endpoints.enabled-by-default=false
      * 특정 Endpoint를 공개 여부를 작성
        ```properties
        management.endpoint.info.enabled=true
        ```
* Actuator 확인하기
  * JConsole 사용하기
    * 콘솔에서 jconsole 입력하면 새로운 창이 뜸. 그곳에서 내가 띄운 애플리케이션을 선택해서 접속하면 됨.
      ![image](https://user-images.githubusercontent.com/52440668/88282979-865dce00-cd25-11ea-88ae-8bda67b7c276.png)
  * VisualVM 사용하기
    * [설치해서 사용하기](https://visualvm.github.io/download.html)
  * http://localhost:8080/actuator 로 접속
    * 일부 Endpoint만 공개되어 있는 default 상황
      ![image](https://user-images.githubusercontent.com/52440668/88282344-57932800-cd24-11ea-9d51-91d186bf5e3b.png)
    * 모든 Endpoint를 공개하는 프로퍼티 설정
      ```properties
      management.endpoints.web.exposure.include=*
      # management.endpoints.web.exposure.exclude=env,beans
      ```
  * __위 세가지 방식은 USER-FREINDLY하지 않아!__

* Spring-Boot-Admin
  * 스프링 부트 Actuator UI를 제공 (제3자가 [오픈 소스](https://github.com/codecentric/spring-boot-admin)로 제공함)
  * 어드민 서버 설정
    ```xml
    <dependency>
      <groupId>de.codecentric</groupId>
      <artifactId>spring-boot-admin-starter-server</artifactId>
      <version>2.0.1</version>
    </dependency>
    ```
    ```java
    @SpringBootApplication
    @EnableAdminServer
    public class Application{
      public static void main(String[] args){
        SpringApplication.run(Application.class);
      }
    }
  * 클라이언트 설정
    ```xml
    <dependency>
      <groupId>de.codecentric</groupId>
      <artifactId>spring-boot-admin-starter-client</artifactId>
      <version>2.0.1</version>
    </dependency>
    ```
    ```properties
    server.port=18080 # admin 서버의 주소와 겹치면 안됨
    spring.boot.admin.client.url=http://localhost:8080 # admin 서버의 주소
    management.endpoints.web.exposure.include=*
    ```
