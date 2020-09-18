# JPA 시작 및 세팅하기

> [JPA 시작 및 세팅하기 출처](https://engkimbs.tistory.com/811?category=772527)
> [JPA Entity Mapping 출처](https://engkimbs.tistory.com/812?category=772527)

- JPA (Java Persistent API)
    - 자바의 영속성 관리와 ORM을 위한 표준 기술
- ORM (Object Relational Mapping)
    - 관계 DB 테이블을 객체 지향적으로 사용하기 위한 기술

### JPA 세팅

#### PostgreSQL 도커 구동

- [링크 참조](https://engkimbs.tistory.com/789)

#### 의존성

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### application.properties

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/springboot
spring.datasource.username=glory
spring.datasource.password=123

# JPA를 통해 DBMS에 쿼리를 요청할 경우 DBMS 상에서 이 쿼리가 실행되는 테이블이 드랍되었다가 새로 생성된 후 쿼리가 실행된다는 것을 의미합니다.
spring.jpa.hibernate.ddl-auto=create

# createLob()을 구현하지 않았다는 경고 메시지가 나오지 않게 설정합니다.
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

# JPA가 생성하는 SQL문을 콘솔창에 출력하는 옵션
spring.jpa.show-sql=true

# 콘솔 창에 출력되는 SQL문을 포맷팅된 형태로 출력
spring.jpa.properties.hibernate.format_sql=true
```

- `spring.jpa.hibernate.ddl-auto`의 옵션
    - create : 기존 테이블을 삭제하고 새로 생성한다. DROP + CREATE
    - create-drop : create 속성에 추가로 애플리케이션을 종료할 때 생성한 DDL을 제거한다. DROP + CREATE + DROP
    - update : 데이터베이스 테이블과 엔티티 매핑정보를 비교해서 변경 사항만 수정한다.
    - validate : 데이터베이스 테이블과 엔티티 매핑정보를 비교해서 차이가 있으면 경고를 남기고 애플리케이션을 실행하지 않는다. 이 설정은 DDL을 수정하지 않는다.
    - none : 자동 생성 기능을 사용하지 않으려면 `hibernate.hbm2ddl.auto` 속성 자체를 삭제하거나 유효하지 않은 옵션 값을 주면 된다 (참고로 none은 유효하지 않은 옵션 값)
    - 상황에 따른 옵션값
        - 개발 초기 단계는 create 또는 update
        - 테스트 서버는 update 또는 validate
        - 스테이징과 운영 서버는 validate 또는 none

#### Entity

- `@Entity`
    - 자바에서 객체지향적인 코드를 작성할 때 네이밍된 DB와 대응되는 클래스의 이름
    - 특정 DB Table과 매핑되는 클래스를 의미
    - 보통 클래스와 같은 이름을 사용하기 때문에 값을 변경하지 않는다. 값을 변경할 때는 `@Entity(name = "myAccount")` 같은 형식의 어노테이션을 붙여서 변경할 수 있다.
- `@Table`
    - 엔티티와 매핑할 테이블을 지정한다. 생략하면 매핑한 엔티티 이름을 테이블 이름으로 사용한다.
    - `@Entity`의 이름이 기본 값이다.
    - `@Table(name = "myAccount")` 같은 형식의 어노테이션을 붙여서 엔티티와 대응되는 테이블명을 명시할 수 있다.
- `@Id`
    - DB Table에서 PRIMARY KEY에 해당되는 필드
- `@GeneratedValue`
    - PRIMARY KEY의 생성 방법을 매핑하는 어노테이션
    - [링크 참조](https://gmlwjd9405.github.io/2019/08/12/primary-key-mapping.html)
- `@Column`
    - 멤버 변수와 DB Table의 컬럼에 매핑하는 어노테이션
    - `@Entity`가 붙어있는 클래스의 멤버 변수에는 기본적으로 모두 `@Column` 어노테이션이 붙은 효과가 부여된다.
- `@Temporal`
    - 시간을 나타내는 데이터를 각 DBMS에서 제공하는 시간 관련 데이터와 매핑합니다.
- `@Transient`
    - 클래스의 멤버 변수 중 DB 컬럼과 매핑하고 싶지 않은 멤버 변수에 추가하여 매핑 대상에서 제외할 수 있다.

```java
@Entity
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```

#### Runner

- `@Transactional`
    - 트랜잭션 속성을 클래스 내의 모든 메서드에게 부여하는 어노테이션
- 인터페이스 `EntityManager`
    - JPA의 핵심.
    - 이 인터페이스의 구현체를 통해서 DB와 자바 객체 사이의 데이터 교환이 이루어진다.

```java
@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
        account.setUsername("saelobi");
        account.setPassword("jpa");

        entityManager.persist(account);
    }

}
```