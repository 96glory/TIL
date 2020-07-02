# Spring JDBC

## 1) Spring JDBC 소개
? [참고](https://www.edwith.org/boostcourse-web-be/lecture/58973/)
### Spring JDBC
* JDBC의 반복적 요소를 스프링 프레임워크가 처리해줌.
  * 스프링의 역할 : 연결 오픈, statement 준비와 실행, 결과를 반복하는 루프 설정, 모든 예외 처리, tx 제어, 연결/statement/resultset 닫기
  * 개발자의 역할 : 연결 params 정의, SQL문 지정, 파라미터 선언과 값 제공, 각 이터레이션에 대한 작업 수행
* Spring JDBC 패키지
  * org.springframework.jdbc.core : JdbcTemplate 및 관련 Helper 객체 제공
  * org.springframework.jdbc.datasource : DataSource를 쉽게 접근하기 위한 유틸 클래스, 트랜젝션매니져 및 다양한 DataSource 구현을 제공
  * org.springframework.jdbc.object : RDBMS 조회, 갱신, 저장등을 안전하고 재사용 가능한 객제 제공
  * org.springframework.jdbc.support : jdbc.core 및 jdbc.object를 사용하는 JDBC 프레임워크를 지원
* JDBC template
  * 리소스 생성, 해지를 처리해서 연결을 닫는 것을 잊어 발생하는 문제 등을 피할 수 있도록 한다.
  * Statement의 생성과 실행을 처리
  * SQL 조회, 업데이터, 저장 프로시저 호출, resultSet 반복호출 등을 실행
  * JDBC 예외 발생시 org.springframework.dao 패키지에 정의되어 있는 일반적인 예외로 변환시켜줌.

## 2) Spring JDBC 실습
### DTO (Data Transfer Object)
* 계층 간 데이터 교환을 위한 자바 beans
  * 계층 : 컨트롤러 뷰, 비즈니스 계층, 퍼시스턴스 계층
* DTO는 보통 로직을 가지고 있지 않고, 순수한 데이터 객체다
* 필드와 getter, setter를 가진다. 추가적으로 toString(). equals(). hashCode() 등의 Object 메소드를 오버라이딩 할 수 있다.

```java
// DTO 예시
public class ActorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public Long getId() {
        return this.id;
    }
    // ......
}
```

### DAO (Data Access Object)
* 데이터를 조회하거나 조작하는 기능을 전담하도록 만든 객체
* 보통 DB를 조작하는 기능을 전담하는 목적

### ConnectionPool
* 고비용이 드는 DB 연결에 대처하기 위해 커넥션 풀은 미리 커넥션을 여러 개 맺어둔다.
* 커넥션이 필요하면 커넥션 풀에게 빌려서 사용한 후 반납한다.

### DataSource
* 커넥션 풀을 관리하는 목적으로 사용되는 객체
* 이를 이용해 커넥션을 얻어오고 반납하는 등의 작업을 수행.
