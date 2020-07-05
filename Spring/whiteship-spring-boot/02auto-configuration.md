# 1. 자동 설정 이해

* @SpringBootApplication 내의 주요 어노테이션
  * @EnableAutoConfiguration
  * @SpringBootConfiguration
  * @ComponentScan

* 빈을 두 단계에 거쳐 읽어들임
  * 1단계 : @ComponentScan
    * @Component, @Configuration, @Repository, @Service, @Controller, @RestController
  * 2단계 : @EnableAutoConfiguration
    * spring.factories (스프링에서 제공하는 여러 빈을 자동으로 등록한다.)
    * @Configuration
    * ConditionalOnXxxYyyZzz
    
