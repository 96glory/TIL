# JPA Entity 상태

> [출처](https://engkimbs.tistory.com/816?category=772527)

## 영속성 관리

- `EntityManagerFactory`
    - 엔티티 매니저를 만드는 공장. 싱글톤으로 구성되어 애플리케이션 전체에서 공유됨.
- `EntityManager`
    - DB 커넥션을 획득하여 트랜잭션을 수행하는 주체가 됨.
- 영속성 컨텍스트 `PersistenceContext`
    - 엔티티를 영구 저장하는 환경.
    - `EntityManager`로 엔티티를 저장하거나 조회하면 `EntityManager`는 `PersistenceContext`에 엔티티를 보관하고 관리한다.
- 엔티티의 생명 주기
    - Session은 `EntityManager`를 상속한 Hibernate 구현체다.

## JPA Entity 상태

- ![image](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=http%3A%2F%2Fcfile21.uf.tistory.com%2Fimage%2F9975194B5C4DACBC134CF3)
- Transient : JPA가 엔티티를 나타내는 객체에 대한 정보를 모르는 상태
- Persistent : JPA가 엔티티를 관리 중인 상태
    - 1차 캐시 : `PersistentContext`에 인스턴스를 넣은 것. 아직 저장되지 않은 상태에서 다시 인스턴스를 요청하면 DB에서 값을 가져오는 것이 아니라 **메모리에 캐시된 엔티티 정보를 받아옴.**
    - Dirty Checking : 엔티티의 변경사항을 지속적으로 추적함. 상태를 추적하여 데이터에 변화가 없을 경우에는 **어떠한 상태도 DB에 반영하지 않는다.**
    - Write Behind : DB에 엔티티의 변경사항을 최대한 필요한 시점에 반영하는 것을 의미한다.
- Detached : JPA가 더이상 엔티티를 관리하지 않는 상태. 혹은, 트랜잭션이 끝났을 때, 이미 DB에 저장되고 Session이 끝난 상태를 의미한다.
- Removed : 엔티티를 삭제하기로 한 상태