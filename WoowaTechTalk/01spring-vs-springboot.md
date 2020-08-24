# Spring vs Spring Boot

> [강의 링크](https://www.youtube.com/watch?v=6h9qmKWK6Io)

### Dependecy

- Spring의 의존성은 상당히 김. 모든 의존성을 버전까지 정확하게 명시해주어야 함.
- Spring Boot에서는 파라미터를 지원하고, **버전 관리도 권장 버전으로 자동으로 설정해준다.**

### Configuration

- Spring의 설정은 상당히 김
  - 예시 : `implements WebMvcConfigurer`
- Spring Boot에서는 간단히 `application.properties`나 `application.yml`를 사용하면 됨.
  - ![image](https://user-images.githubusercontent.com/52440668/90995455-feeecd80-e5f6-11ea-959a-4d96372526f0.png)
  - yml이 Human-Friendly해서 yml를 사용하는 추세.
- Thymeleaf 예시
  - ![image](https://user-images.githubusercontent.com/52440668/90995623-6f95ea00-e5f7-11ea-85d4-84b5df9eae1e.png)
  - ![image](https://user-images.githubusercontent.com/52440668/90995652-8e947c00-e5f7-11ea-98fa-5330b5d6706f.png)
    - 사실 thymeleaf의 suffix와 prefix는 thymeleaf에 자동 설정되어 있다.

### Embedded Server

- 스프링 부트에서 간편하게 tomcat을 사용할 수 있고, 코드 단 두줄로 jetty로 바꿀 수 있다.
  - ![image](https://user-images.githubusercontent.com/52440668/90995761-e4692400-e5f7-11ea-8be5-6a31abe58862.png)
- 스프링 부트에서 내장 서블릿 컨테이너 덕분에 jar 파일로 간단 배포할 수 있음.
  - `java -jar $REPOSITORY/$JAR_NAME &`

### 결론

- Spring Boot는
  - 간편한 설정을 도와준다.
  - 편리한 의존성 관리와 자동 권장 버전 관리가 가능하다.
  - 내장 서버로 인한 간단한 배포 서버 구축이 가능하다.
  - Spring Security, JPA 등의 다른 스프링 프레임워크 요소를 쉽게 사용하도록 도와준다.
- Spring Boot makes it easy to create stand-alone, production-grade, Spring based Applications that you can "just run".
