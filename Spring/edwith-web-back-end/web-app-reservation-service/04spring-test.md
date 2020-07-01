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
> [작성한 코드]( )
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



