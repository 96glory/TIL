# 스프링 데이터
> 목차
> 1. [인메모리 데이터베이스](#인메모리-데이터베이스)
> 2. [DBCP](#dbcp)
> 3. [PostgreSQL 설정하기](#postgresql-설정하기)
> 4. [spring-data-jpa 소개 및 연동](#spring-data-jpa-소개-및-연동)
> 5. [데이터베이스 초기화](#데이터베이스-초기화)

## 인메모리 데이터베이스
* 데이터 스토리지의 메인 메모리에 설치되어 운영되는 방식의 데이터베이스 관리 시스템
* 종류 : __H2__, HSQL, Derby, ...
* spring-boot-starter-data-jdbc를 추가하면 DataSource, JdbcTemplate를 사용할 수 있다.
* 인메모레 데이터베이스 기본 연결 정보 확인하는 방법
  * URL 구버전 : "testdb"
  * URL 신버전 : __uniqueid__ 
    * 신버전에서 testdb로 고정시키고 싶다면?
      * spring.datasource.generate-unique-name=false 추가
  * username : "sa"
  * password : ""
  ```java
  @Component
  public class H2Runner implements ApplicationRunner {

    @Autowired
    DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) throws Exception {
      // 새로운 connection
      Connection connection = dataSource.getConnection();
      
      // metadata 출력
      System.out.println(connection.getMetaData().getURL());
      System.out.println(connection.getMetaData().getUserName());
      
      // user라는 테이블 만들기
      Statement statement = connection.createStatement();
      String sql = "CREATE TABLE USER(ID INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))";
      statement.executeUpdate(sql);
      
      connection.close();
    }
  }
  ```
* H2 Console 사용하기
  * case 1 : pom.xml에 spring-boot-devtools 추가 : 비교적 무겁다
  * case 2 : applicaion.properties에 spring.h2.console.enabled=true 추가 : 비교적 가볍다
  * and, http://localhost:8080/h2-console 에 접속.
  * test DB
    * [작성한 코드](https://github.com/96glory/whiteship-spring-boot/blob/be8e934807fc1b6e601427e2ab82ee7c7861964e/springdata/src/main/java/me/glory/springdata/H2Runner.java)
    ```sql
    CREATE TABLE USER (ID INTEGER NOT NULL, name VARCHAR(255), PRIMARY KEY (id))
    INSERT INTO USER VALUES (1, ‘glory’)
    ```

## DBCP (DataBase Connection Pool)
* connection을 만드는 과정이 복잡하므로 미리 여러 connection을 만들어 놓고 사용자가 connection을 요청했을 때 적절히 분배해줌.
* connection의 갯수와 주기 등의 설정은 애플리케이션의 환경과 사용량, 개발자에 달렸다.
* Spring에서 지원하는 DBCP
  * __[HikariCP](https://github.com/brettwooldridge/HikariCP#frequently-used)__
  * Tomcat CP
  * Commons DBCP2
* DBCP 설정
  * spring.datasource.hikari.*
  * spring.datasource.tomcat.*
  * spring.datasource.dbcp2.*
  
## PostgreSQL 설정하기
* 의존성 추가
  * 주의 : DB를 추가하는 것이 아니라 driver(connector 역할)를 추가하는 것임!
  ```xml
  <dependency>
   <groupId>org.postgresql</groupId>
   <artifactId>postgresql</artifactId>
  </dependency>
  ```
* Spring 프로젝트에 DB에 접속하기 위한 프로퍼티 추가
  ```properties
  spring.datasource.url=jdbc:postgresql://localhost:5432/springboot
  spring.datasource.username=glory
  spring.datasource.password=pass
  ```
* PostgreSQL 설치 및 서버 실행 in docker
  ```shell
  # docker로 PostgreSQL 띄우기
  docker run -p 5432:5432 -e POSTGRES_PASSWORD=pass -e POSTGRES_USER=glory \
    -e POSTGRES_DB=springboot --name postgres_boot -d postgres
  
  # 띄운 PostgreSQL 서버에 bash 로 접근
  docker exec -i -t postgres_boot bash
  
  # user를 postgres로 바꿈
  su - postgres
  
  # 사용할 DB 이름으로 접근
  psql springboot
  
  # 데이터베이스 조회
  \list
  
  # 테이블 조회
  \dt
  
  # 쿼리
  SELECT * FROM account;
  ```
  
## spring-data-jpa 소개 및 연동
> [작성한 코드](https://github.com/96glory/whiteship-spring-boot/tree/9df0f04325a1cd54822576326c8075841958d2b0/springdatajpa/src)
* ORM : Object-Relational Mapping & JPA : Java Persistence API
  * 객체와 릴레이션을 맵핑할 때 발생하는 개념적 불일치를 해결하는 프레임워크
* 스프링 데이터 JPA
  * 기능
    * Repository 빈 자동 생성
    * 쿼리 메소드 자동 구현
    * @EnableJpaRepositories (스프링 부트가 자동으로 설정해줌.)
  * 의존성 추가
    ```xml
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    ```
  * JPA 사용하기
    * @Entity 클래스 만들기
      ```java
      @Entity
      public class Account {
        @Id @GeneratedValue
        private Long id;
        private String username;
        private String password;

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Account account = (Account) o;
            return Objects.equals(id, account.id) &&
                    Objects.equals(username, account.username) &&
                    Objects.equals(password, account.password);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, username, password);
        }
      }
      ```
    * Repository 인터페이스 만들기
      ```java
      // JpaRepository<Entity의 타입, Id의 타입>
      public interface AccountRepository extends JpaRepository<Account, Long> {

      }
      ```
    * 데이터베이스 추가해주기 (ex. 인메모리 DB, postgreSQL, ...)
      * 인메모리 DB를 사용하지 않는 경우 spring.datasource.* 를 언급해주어야 한다.
  * 스프링 데이터 Respotiory의 테스트 만들기
    * [작성한 코드](https://github.com/96glory/whiteship-spring-boot/blob/9df0f04325a1cd54822576326c8075841958d2b0/springdatajpa/src/test/java/me/glory/springdatajpa/account/AccountRepositoryTest.java)
    * H2 DB를 테스트 의존성에 반드시 추가시켜주어야 함.
    * @DataJpaTest(슬라이스 테스트)로 작성하는 것을 추천.
      * @SpringBootTest를 추천하지 않는다.
        * 느리다.
        * 실제 DB를 건드릴 수 있다.

## 데이터베이스 초기화
> [작성한 코드](https://github.com/96glory/whiteship-spring-boot/tree/697b6162dac6195d322ba21302c00a53fc483fef/springdatajpa/src)
* JPA를 사용한 DB 초기화
  * spring.jpa.hibernate.ddl-auto=
    * update : 기존의 스키마를 가만히 두고 추가 혹은 변경된 것만 update함. 주로 개발할 때 사용함.
    * ~~create~~ : 처음 띄울 때 다 지우고 새로 시작 (위험 : 실제 데이터가 날아갈 수도)
    * ~~create-drop~~ : 애플리케이션 시작 시 create, 애플리케이션 종료 시 drop (위험 : 실제 데이터가 날아갈 수도)
    * ~~validate~~ : ddl에 변경을 가하지 않고 검증만 함. 주로 실제 애플리케이션을 운영할 때 사용함. 새 column을 추가하면 오류 발생함.
    * ~~none~~
  * spring.jpa.generate-dll=true로 설정 해줘야 동작함.
  * spring.jpa.show-sql=true : 실제 애플리케이션에 동작되는 sql를 다 볼 수 있다.
* SQL 스크립트를 사용한 DB 초기화
  * 추후 강의 참조
  
