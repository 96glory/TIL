# JPA Query

> [출처](https://engkimbs.tistory.com/819?category=772527)

### JPQL (Java Persistence Query Language) / HQL (Hibernate Query Language)

- DB 테이블이 아닌, 엔티티 객체 모델 기반으로 쿼리를 작성하는 언어
- SQL과 유사
- JPA 또는 Hibernate가 해당 쿼리를 SQL문으로 변환해서 실행하준다.

```java
TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post AS p", Post.class);
List<Post> posts = query.getResultList();
posts.forEach(System.out::println);
```

### Criteria

- JPA에서 제공하는 type-safe한 쿼리
- JPQL에서 오타가 있는 지 검증합니다.

```java
CriteriaBuilder builder = entityManager.getCriteriaBuilder();
CriteriaQuery<Post> query = builder.createQuery(Post.class);
Root<Post> root = query.from(Post.class);
query.select(root);
```

### Native Query

- DBMS에서 제공하는 SQL 쿼리를 실행하는 방식

```java
TypedQuery<Post> query = entityManager.createQuery("SELECT p FROM Post AS p", Post.class);
List<Post> posts = query.getResultList();
posts.forEach(System.out::println);
```