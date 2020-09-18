# Value Type Mapping

> [출처](https://engkimbs.tistory.com/814?category=772527)

### 기본값 타입

- primitive type
- Integer, Double 같은 primitive의 Wrapper 타입

### 임베디드 타입

- 새로운 값 타입을 직접 정의해서 사용하는 경우 이를 임베디드 타입이라고 한다.
- 임베디드 타입 정의하기
    ```java
    @Embeddable
    public​ ​class​ ​Address​ {
        ​private​ String street;
        ​private​ String city;
        ​private​ String zipCode;
    }
    ```
- 임베디드 타입 사용하기
    - 임베디드 타입을 원하는 엔티티에 `@Embedded`를 넣어준다.
    ```java
    @Entity
    public​ ​class​ ​Account​ {
        
        // ...

        ​@Embedded
        ​private​ Address address;

        ​//...

    }
    ```
- 임베디드 타입을 여러개 사용하는 경우
    - 임베디드 타입을 여러개 사용하면 테이블에 매핑하는 컬럼명이 중복된다. 따라서 `@AttributeOverrides`, `@AttributeOverride`를 사용하여 매핑정보를 재정의 해야한다.
    ```java
    @Entity
    public​ ​class​ ​Account​ {
        ​//...

        ​@Embedded
        ​private​ Address homeAddress

        ​@Embedded
        ​@AttributeOverrides​({
            ​@AttributeOverride​(name = ​"street"​, column = ​@Column​(name = "company_street"​))
            ​@AttributeOverride​(name = ​"city"​, column = ​@Column​(name = "company_city"​))
            ​@AttributeOverride​(name = ​"zipcode"​, column = ​@Column​(name = ​"company_zipcode"​))
        })
        ​private​ Address companyddress;

        ​//...
    }
    ```

### 예시


```java
package com.tutorial.springbootjpa;

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

```java
package com.tutorial.springbootjpa;

// 이 클래스가 엔티티 역할을 할 것이다!
@Entity(name = "myAccount")
// 이 클래스는 DBMS에 존재하는 Account Table과 매핑될 것이다!
@Table(name = "Account")
@Getter @Setter
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable=false, unique=true)
    private String username;

    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created = new Date();

    private String yes;

    @Transient
    private String no;

    // Composite Value 타입을 지정할 때 사용하는 어노테이션. 자바의 클래스 형식으로 작성할 수 있음
    // @AttributeOverride 어노테이션을 통해서 Composite Value 타입의 멤버 변수를 테이블의 어떤 컬럼과 매칭할 지 커스터마이징할 수 있습니다.
    @Embedded
    @AttributeOverrides({
            ​@AttributeOverride​(name = ​"street"​, column = ​@Column​(name = "company_street"​))
            ​@AttributeOverride​(name = ​"city"​, column = ​@Column​(name = "company_city"​))
            ​@AttributeOverride​(name = ​"zipcode"​, column = ​@Column​(name = ​"company_zipcode"​))
    })
    private Address address;
}
```

```java
package com.tutorial.springbootjpa;

import javax.persistence.Embeddable;

@Embeddable
class Address {

    private String street;

    private String city;

    private String state;

    private String zipCode;

}
```