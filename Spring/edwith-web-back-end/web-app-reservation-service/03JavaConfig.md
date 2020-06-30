# Java Config를 이용한 설정
> [참고](https://www.edwith.org/boostcourse-web-be/lecture/58972/)
> [작성한 코드](https://github.com/96glory/boostcourse-web-be/tree/ebba82d68814d29c7f145f8b8f80ce83cdb5bb89)

## Java Config를 이용한 설정을 위한 어노테이션

### @Configuration
* 스프링 설정 클래스를 선언하는 어노테이션

### @Bean
* bean을 정의하는 어노테이션

### @ComponentScan
* @Controller, @Service, @Repository, @Component 어노테이션이 붙은 클래스를 찾아 컨테이너에 등록

### @Component
* 컴포넌트 스캔의 대상이 되는 어노테이션 중 하나로써 주로 유틸, 기타 지원 클래스에 붙이는 어노테이션

### @Autowired
* 주입 대상이 되는 bean을 컨테이너에 찾아 주입하는 어노테이션
