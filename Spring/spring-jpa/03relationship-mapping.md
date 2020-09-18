# JPA 관계 매핑

> [출처](https://engkimbs.tistory.com/815?category=772527)

## 관계 매핑 목차

- 방향
    - 단방향 : 관계가 있을 때 둘 중 한 쪽만 참조하는 것
    - 양방향 : 양 쪽 모두 서로 참조하는 것
        - 객체를 양방향 연관관계로 만들면 연관관계의 주인을 정해주어야 한다.
- 다중성
    - ManyToOne
    - OneToMany
    - ManyToMany
    - OneToOne
- 연관관계의 주인

### 단방향 연관관계

- 예시
    - 회왼과 팀이 있다.
    - 회원은 하나의 팀에만 소속될 수 있다.
    - 회원과 팀은 ManyToOne 관계다.
- 예시의 코드
    ```java
    @Entity
    public​ ​class​ ​Member​ {

        ​@Id
        ​private​ Long id;

        ​private​ String username;

        ​@ManyToOne
        ​private​ Team team;

        ​// ...
    }
    ```
    ```java
    @Entity
    public​ ​class​ ​Team​ {

        ​@Id
        ​private​ Long id;

        @Column(nullable=false)
        ​private​ String name;
        
        ​//...
    }
    ```

### 양방향 연관관계

- 양방향 연관관계를 만들기 위해서 기존의 연관관계에 팀에서 회원으로 접근하는 관계를 추가하면 된다.
    ```java
    @Entity
    public​ ​class​ ​Team​ {

        ​@Id
        ​private​ Long id;

        @Column(nullable=false)
        ​private​ String name;
        
        ​@OneToMany
        ​private​ Set<Member> members = new HashSet<>();

        ​//...

        public void addMember(Member member){
            this.members.add(member);
            member.setTeam(this);
        }

        public void removeMember(Member member){
            this.members.remove(member);
            member.setTeam(null);
        }
    }
    ```
- JpaRunner
    ```java
    @Component
    @Transactional
    public class JpaRunner implements ApplicationRunner {

        @PersistenceContext
        EntityManager entityManager;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            Member member = new Member();
            member.setName("glory");

            Team team = new Team();
            team.setName("KIA TIGERS");

            member.addTeam(team);

            entityManager.persist(member);
            entityManager.persist(team);
        }
    }
    ```

### 연관관계의 주인

- 테이블과 객체의 차이
    - 테이블은 외래키 하나로 두 테이블의 연관관계를 관리한다.
    - 엔티티를 양방향 연관관계로 설정하면 객체의 참조는 둘인데 외래키는 하나다.
    - **이러한 차이로 JPA에서는 두 객체 연관관계 중 하나를 정해서 테이블의 외래키를 관리하는데, 이를 연관관계의 주인이라 한다.**
- 연관관계의 주인 위치
    - 연관관계의 주인은 테이블의 외래키가 있는 곳으로 정해야 한다.주인이 아닌 곳에는 `mappedBy` 속성을 사용해 주인이 아님을 명시한다.
    ```java
    @Entity
    public​ ​class​ ​Team​ {

        ​@Id
        ​private​ Long id;

        @Column(nullable=false)
        ​private​ String name;
        
        ​@OneToMany(mappedBy = "team")
        ​private​ Set<Member> members;

        ​//...
    }