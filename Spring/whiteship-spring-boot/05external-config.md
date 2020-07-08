# 외부 설정
> [작성한 코드](https://github.com/96glory/whiteship-spring-boot/tree/fef2b10b09354805abe8717b7daebe6779861fc4)

## 외부 설정 파일 : application에서 사용하는 여러 설정 값들을 설정
  * application.properties : key - value 형태로 삽입
  
## 프로퍼티 우선순위
  1. 유저 홈 디렉토리에 있는 spring-boot-dev-tools.properties
  2. 테스트에 있는 @TestPropertySource
    * @TestPropertySource(locations = "classpath:/test.properties")
    * 관리해야할 프로퍼티가 너무 많아서 파일로 따로 관리해야할 때 사용
  3. @SpringBootTest 애노테이션의 properties 애트리뷰트
    * @SpringBootTest(properties = "glory.name=youngkwangsong")
    * 보통 테스트의 application.properties의 우선순위가 높다.
  4. 커맨드 라인 아규먼트
    ```console
    mvn clean package -DskipTests
    java -jar target/externalconfig-0.0.1-SNAPSHOT.jar --glory.name=youngkwang
    ```
  5. SPRING_APPLICATION_JSON (환경 변수 또는 시스템 프로티) 에 들어있는 프로퍼티
  6. ServletConfig 파라미터
  7. ServletContext 파라미터
  8. java:comp/env JNDI 애트리뷰트
  9. System.getProperties() 자바 시스템 프로퍼티
  10. OS 환경 변수
  11. RandomValuePropertySource
  12. JAR 밖에 있는 특정 프로파일용 application properties
  13. JAR 안에 있는 특정 프로파일용 application properties
  14. JAR 밖에 있는 application properties
  15. JAR 안에 있는 application properties
    * 우리가 보통 사용하는 application.properties
  16. @PropertySource
  17. 기본 프로퍼티 (SpringApplication.setDefaultProperties)

## application.properties 우선 순위 (높은 순위가 낮은 순위를 덮어 쓴다.(겹치는 것만))
  1. file:./config/
  2. file:./
  3. classpath:/config/
  4. classpath:/

## @ConfigurationProperties
  * 외부 설정이 많고, 같은 key가 많을 경우 그것들을 묶어서 하나의 bean으로 등록하는 방법
  ```xml
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
  </dependency>
  ```
  * main application class에 추가, 혹은 gloryProperties.java에 @Component 추가
  ```java
  @EnableConfigurationProperties(GloryProperties.class)
  ```
  * gloryProperties.java
  ```java
  @Component
  @ConfigurationProperties("glory")
  public class GloryProperties {

      private String name;

      private int age;

      public String getName() {
          return name;
      }

      public void setName(String name) {
          this.name = name;
      }

      public int getAge() {
          return age;
      }

      public void setAge(int age) {
          this.age = age;
      }
  }
  ```
  * SpringRunner에 가져다 쓰기
  ```java
  @Component
  public class SampleRunner implements ApplicationRunner {
      @Autowired
      GloryProperties gloryProperties;

      @Override
      public void run(ApplicationArguments args) throws Exception {
          System.out.println("====================");
          System.out.println(gloryProperties.getName());
          System.out.println(gloryProperties.getAge());
          System.out.println("====================");
      }
  }
  ```
  
