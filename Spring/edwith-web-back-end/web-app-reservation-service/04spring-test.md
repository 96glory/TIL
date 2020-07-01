# Spring Test

## 1) Test 개요
* 어플리케이션 또는 시스템의 동작과 성능, 안정성이 요구하는 수준을 만족하는지 확인하기 위해 결함을 발견하는 과정
* 테스팅의 가이드 라인
  * 테스팅은 결함이 존재함을 밝히는 활동이다.
    * 테스팅은 결함이 존재함을 밝힐 수 있지만, 결함이 없다는 것을 증명할 수 없다.
  * 완벽한 테스팅(Exhaustive testing)은 불가능하다.
    * 모든 가능성을 테스팅하는 것은 시간과 돈을 소비하는 과정이다.
    * 완벽한 테스팅 대신 리스크 분석과 결정된 우선순위에 따라 테스팅의 선택과 집중을 해야 한다.
  * 테스팅을 개발 초기에 시작한다.

## 2) JUnit : 자바의 테스트를 위한 프레임 워크
> [작성한 코드](https://github.com/96glory/boostcourse-web-be/tree/0c3da2d2f3eae08b78181b8db68e423ed7bb6687)
```xml
<dependency>
  <groupId>junit</groupId>
  <artifactId>junit</artifactId>
  <version>버전</version>
  <scope>test</scope>
</dependency> 
```
### 어노테이션 종류
* @BeforeClass : 테스트 클래스가 실행되기 전에 딱 한번 실행됩니다.
* @AfterClass : 테스트 클래스의 모든 테스트 메소드가 실행이 끝나고 딱 한번 실행됩니다.
* @Before : 테스트 메소드가 실행되기 전에 실행됩니다.
* @After : 테스트 메소드가 실행된 후에 실행됩니다.
* @Test : @Test 어노테이션이 붙은 메소드는 테스트 메소드입니다.

### JUnit의 주요 assert
* assertEquals(x, y) : 객체 x와 y가 일치하면 테스트 성공. 주로 예상값이 x, 실제값이 y
* assertArrayEquals(a, b) : 배열 a와 b가 일치하면 테스트 성공
* assertFalse(x) : x가 false면 테스트 성공
* assertTrue(x) : x가 true면 테스트 성공
* assertTrue(message, condition) : condition이 true이면 message를 표시하고 테스트 성공
* assertNull(o) : 객체 o가 null이면 테스트 성공
* assertNotNull(o) : 객체 o가 null이 아니면 테스트 성공
* assertSame(ox, oy) : 객체 ox와 객체 oy가 같은 객체임을 확인한다.
* assertNotSame(ox, oy) : ox와 oy가 같은 객체를 참조하고 있지 않다면 테스트 실패

## 3) Spring test annotation 사용하기
> [작성한 코드](https://github.com/96glory/boostcourse-web-be/tree/93be72730df7e31329e33b928d99b069af8086ef)
### 스프링 프레임워크를 사용하도록 기존 코드 변경하기
* @ComponentScan, @Configuration, @Component 추가
 * 스프링 빈 컨테이너에서 관리한다는 것은 개발자가 직접 인스턴스를 생성하지 않는다는 것을 의미합니다.
 * 스프링 빈 컨테이너가 인스턴스를 생성해 관리한다는 것을 뜻합니다.
 * 스프링 빈 컨테이너가 CalculatorService클래스를 찾아 빈으로 등록할 수 있도록 클래스 위에 @Component를 붙입니다.

### 테스트 클래스를 스프링 빈 컨테이너를 사용하도록 수정하기
* 테스트 클래스에서 스프링 빈 컨테이너를 사용할 때 개발자가 직접 인스턴스를 생성하면 안됨.
 * 스프링 빈 컨테이너가 빈을 생성하고 관리하도록 하고, 그 빈을 테스트 해야 함.
* @RunWith(SpringJUnit4ClassRunner.class)
 * RunWith는 JUnit이 제공하는 어노테이션이다.
 * JUnit이 테스트 코드를 실행할 때 스프링 빈 컨테이너가 내부적으로 생성되도록 한다.
* @ContextConfiguration(classes = {ApplicationConfig.class})
 * 내부적으로 생성된 스프링 빈 컨테이너가 사용할 설정파일을 지정할 때 사용한다.
* 위와 같은 코드를 통해 테스트 클래스 자체가 Bean 객체가 되어 스프링에서 관리하게 된다.
 * 테스트 클래스가 빈으로 관리되면서, 스프링 빈 컨테이너는 CalculatorService를 @Autowired를 통해 주입받게 된다.
 
## 4) 로직 단위 테스트 구현하기
> [작성한 코드](https://github.com/96glory/boostcourse-web-be/tree/48a7131bf262d0224f0149b3fda18265836f8074)
### 테스트 분류
* 통합 테스트(integration test) : 하나의 빈을 테스트할 때 관련된 빈들이 모두 잘 동작하는지 테스트
* 단위 테스트(unit test) : 빈과 빈 사이의 관계를 Mock 객체를 이용하여 끊고, 테스트하고자 하는 객체에만 집중하여 테스트
### 단위 테스트를 수행하기 위해 추가한 MyService
* CalculatorService를 가져다 쓴 MyService. 하지만, CalculatorService.plus 메소드에 버그가 있다면....?
 * Mock 객체를 이용해 문제 해결
* Mock 객체를 쓰기 위한 준비
```xml
<dependency>
 <groupId>org.mockito</groupId>
 <artifactId>mockito-core</artifactId>
 <version>1.9.5</version>
 <scope>test</scope>
</dependency>
```
 * 자세한 이야기는 위 작성한 코드의 MyServiceTest.java 주석 참고 바람.
