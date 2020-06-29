> [참고](https://www.edwith.org/boostcourse-web-be/lecture/58969/)

# 1) Spring이란?

## Framework란?
* 제품이나 재료가 아닌, 반제품

## Spring Framework란?
* 엔터프라이즈급 어플리케이션을 구축할 수 있는 가벼운 솔루션
* IoC 컨테이너
* 선언적으로 트랜잭션 관리할 수 있음
* 완전한 기능을 갖춘 MVC Framework를 제공
* AOP 지원
* 모듈화가 잘 되어있음
![springframeworkroutine](https://user-images.githubusercontent.com/52440668/85989759-ef566c80-ba2b-11ea-8007-32519c0035fe.JPG)

## AOP 와 인스트루멘테이션 (Instrumentation)
* spring-AOP : AOP 얼라이언스(Alliance)와 호환되는 방법으로 AOP를 지원합니다.
* spring-aspects : AspectJ와의 통합을 제공합니다.
* spring-instrument : 인스트루멘테이션을 지원하는 클래스와 특정 WAS에서 사용하는 클래스로 더 구현체를 제공합니다. 참고로 BCI(Byte Code Instrumentations)은 런타임이나 로드(Load) 때 클래스의 바이트 코드에 변경을 가하는 방법을 말합니다.
 

## 메시징(Messaging)
* spring-messaging : 스프링 프레임워크 4는 메시지 기반 어플리케이션을 작성할 수 있는 Message, MessageChannel, MessageHandler 등을 제공합니다. 또한, 해당 모듈에는 메소드에 메시지를 맵핑하기 위한 어노테이션도 포함되어 있으며, Spring MVC 어노테이션과 유사합니다.

## 데이터 엑서스(Data Access) / 통합(Integration)
* spring-jdbc : 자바 JDBC프로그래밍을 쉽게 할 수 있도록 기능을 제공합니다.
* spring-tx : 선언적 트랜잭션 관리를 할 수 있는 기능을 제공합니다.
* spring-orm : JPA, JDO및 Hibernate를 포함한 ORM API를 위한 통합 레이어를 제공합니다.
* spring-oxm : JAXB, Castor, XMLBeans, JiBX 및 XStream과 같은 Object/XML 맵핑을 지원합니다.
* spring-jms : 메시지 생성(producing) 및 사용(consuming)을 위한 기능을 제공, Spring Framework 4.1부터 spring-messaging모듈과의 통합을 제공합니다.

## 웹(Web)
* spring-web : 멀티 파트 파일 업로드, 서블릿 리스너 등 웹 지향 통합 기능을 제공한다. HTTP클라이언트와 Spring의 원격 지원을 위한 웹 관련 부분을 제공합니다.
* spring-webmvc : Web-Servlet 모듈이라고도 불리며, Spring MVC 및 REST 웹 서비스 구현을 포함합니다.
* spring-websocket : 웹 소켓을 지원합니다.
* spring-webmvc-portlet : 포틀릿 환경에서 사용할 MVC 구현을 제공합니다.


# 2) Spring IoC/DI 컨테이너
## Container
* 인스턴스의 생명주기를 관리하며, 생성된 인스턴스에게 추가적인 기능을 제공.

## IoC (Inversion of Control) : 제어의 역전
* 보통 개발자는 프로그램의 흐름을 제어하는 코드를 작성한다.
* 프로그램의 흐름을 제어를 개발자가 하는 것이 아니라 다른 프로그램이 그 흐름을 제어하는 것을 IoC라고 한다.
  * 예를 들어, 서블릿 클래스는 개발자가 만들지만, 그 서블릿의 메소드를 알맞게 호출하는 것은 WAS
* 모든 제조사의 TV 리모콘의 모양이 통일되면, 사용자의 입장에서 TV를 교체하더라도 리모콘을 사용하는 데 적응 기간이 필요없다.
* 인터페이스를 만들어서 구현하면 객체는 바뀌더라도 내부적으로 사용하는 메서드들은 같기 때문에 한 인터페이스에 대해 익숙해지면 다른 객체를 쓰더라도 편하게 사용할 수 있다.
  * Spring에서는 객체를 만드는 공장을 매번 만들어주는 BeanFactory나 ApplicationContext가 그 일을 대신해준다.
  
## DI (Dependency Injection) : 의존성 주입
* 쓰려고 하는 객체를 받아오는 일이 필요할 것이다.
* DI는 클래스 사이의 의존 관계를 Bean 설정 정보를 바탕으로 컨테이너가 자동으로 연결해주는 것을 말한다.
  * Factory가 만들어준 인스턴스를 내 프로그램에서 사용하려면?
  ``` // DI가 적용되지 않은 예
  class 엔진 {

  }

  class 자동차 {
       엔진 v5 = new 엔진();
  }
  
  // DI가 적용된 예
  @Component
  class 엔진 {

  }

  @Component
  class 자동차 {
       @Autowired
       엔진 v5;
  }

## Spring에서 제공하는 IoC/DI 컨테이너
* BeanFactory : IoC/DI에 대한 기본 기능을 가지고 있다.
* ApplicationContext : BeanFactory의 모든 기능을 포함하며, 일반적으로 BeanFactory보다 복잡함. 보통 BeanFactory보다 ApplicationContext를 사용한다. BeanFactory 기능 + (트랜잭션처리, AOP 등에 대한 처리, BeanPostProcessor, BeanFactoryPostProcessor등을 자동으로 등록하고, 국제화 처리, 어플리케이션 이벤트 등을 처리)
* BeanPostProcessor : 컨테이너의 기본로직을 오버라이딩하여 인스턴스화 와 의존성 처리 로직 등을 개발자가 원하는 대로 커스터마이징
* BeanFactoryPostProcessor : 설정된 메타 데이터를 커스터마이징
