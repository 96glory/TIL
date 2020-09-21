# 스프링 데이터

## 스프링 데이터 Repository

### `JpaRepository<Entity, ID-TYPE>`

> [출처](https://engkimbs.tistory.com/822?category=772527)

- 소스 코드
    ```java
    public interface PostRepository extends JpaRepository<Post, Long> {

    }
    ```
    - 인터페이스 `JpaRepository`는 `save`, `findAll`, `findAllById` 등과 같은 메서드를 제공해준다.
    - 프로그래머가 `Repository`와 인터페이스 할 수 있는 메서드를 직접 구현하는 것도 가능.

- 테스트 코드
    ```java
    @RunWith(SpringRunner.class)
    @DataJpaTest
    public class PostRepositoryTest {
        /*
            @Autowired를 통해서 위에서 작성한 PostRepository 인터페이스의 구현체인 리포지터리 빈을 주입받아 사용할 수 있습니다.
            이때는 pom.xml 에서 추가한 H2 DB를 테스트에서 Repository로 사용하게 됩니다.
        */
        @Autowired
        PostRepository postRepository;

        @Test
        @Rollback(false)
        public void crudRepository() {
            Post post = new Post();
            post.setTitle("hello spring boot common");
            assertThat(post.getId()).isNull();

            Post newPost = postRepository.save(post);

            assertThat(newPost.getId()).isNotNull();
        }

    }
    ```
    - `@Rollback(false)`
        - `@DataJpaTest`에서는 기본적으로 `@Transational`이 추가되어 있어 기본적으로 각 테스트들이 수행되었을 때 rollback를 하게 됩니다. **하이버네이트는 rollback될 쿼리는 수행할 필요가 없는 쿼리라 인식하여 Repository에 엔티티를 저장하는 insert문을 날리지 않게 됩니다.** 따라서 원하는 테스트를 하기 위해서는 `@Roallback(false)`를 추가하여 rollback을 하지 않겠다는 정보를 부가해야합니다.
    - 위 코드 첫 세 줄 : `Post`에 원래는 할당되지 않는 `id`에 값이 할당되지 않았습니다. (`assertThat(post.getId()).isNull();`가 통과됨)
    - `postRepository.save(post)`를 실행하게 되면서 `@GeneratedValue`에 의해 멤버 변수 `id`에 값이 할당되게 됩니다.
    - 따라서 위 테스트는 통과됩니다.
- 테스트 코드 : 페이징 기능 구현
    - [`Pagable` 정보 링크](https://gunju-ko.github.io/spring/2018/05/01/Spring-Data-JPA-Paging.html)
    ```java
    @RunWith(SpringRunner.class)
    @DataJpaTest
    public class PostRepositoryTest {

        @Autowired
        PostRepository postRepository;

        @Test
        @Rollback(false)
        public void crudRepository() {
            Post post = new Post();
            post.setTitle("hello spring boot common");
            assertThat(post.getId()).isNull();

            Post newPost = postRepository.save(post);
            assertThat(newPost.getId()).isNotNull();

            List<Post> posts = postRepository.findAll();

            assertThat(posts.size()).isEqualTo(1);
            assertThat(posts).contains(newPost);

            Page<Post> page = postRepository.findAll(PageRequest.of(0, 10));
            assertThat(page.getTotalElements()).isEqualTo(1);
            assertThat(page.getNumber()).isEqualTo(0);
            assertThat(page.getSize()).isEqualTo(10);
            assertThat(page.getNumberOfElements()).isEqualTo(1);
        }
    }
    ```
    
### `JpaRepository` 인터페이스 메서드 추가

- `PostRepository`에 다음과 같이 어떤 기능을 구현하기 위한 메서드를 추가하면 쉽게 Repository에 원하는 데이터를 찾아 올 수 있다.
- 단 **Spring Data에서 요구하는 규칙을 만족해야지 제대로 된 기능이 추가된다.**

- 소스 코드
    ```java
    public interface PostRepository extends JpaRepository<Post, Long> {

        Page<Post> findByTitleContains(String title, Pageable pageable);

        long countByTitleContains(String title);

    }
    ```
    - `findBy`로 시작하는 메서드는 쿼리를 요청하는 메서드를 나타낸다.
        - `findByTitleContains`는 `Post`의 멤버 변수 `title`이 메서드의 인수 `title`와 같은 엔티티를 찾아 `Page`형태로 리턴하는 메서드
    - `countBy`로 시작하는 메서드는 쿼리 결과 레코드 수를 요청하는 메서드를 나타낸다.
        - `countByTitleContains`는 `Post`에서 해당 `title`에 해당하는 `Post`들의 개수를 찾아 반환하는 메서드
- 테스트 코드
    ```java
    @RunWith(SpringRunner.class)
    @DataJpaTest
    public class PostRepositoryTest {

        @Autowired
        PostRepository postRepository;

        @Test
        @Rollback(false)
        public void crudRepository() {
            Post post = new Post();
            post.setTitle("hello spring boot common");
            assertThat(post.getId()).isNull();

            Post newPost = postRepository.save(post);
            assertThat(newPost.getId()).isNotNull();

            List<Post> posts = postRepository.findAll();

            assertThat(posts.size()).isEqualTo(1);
            assertThat(posts).contains(newPost);

            Page<Post> page = postRepository.findAll(PageRequest.of(0, 10));
            assertThat(page.getTotalElements()).isEqualTo(1);
            assertThat(page.getNumber()).isEqualTo(0);
            assertThat(page.getSize()).isEqualTo(10);
            assertThat(page.getNumberOfElements()).isEqualTo(1);

            page = postRepository.findByTitleContains("spring", PageRequest.of(0, 10));
            assertThat(page.getTotalElements()).isEqualTo(1);
            assertThat(page.getNumber()).isEqualTo(0);
            assertThat(page.getSize()).isEqualTo(10);
            assertThat(page.getNumberOfElements()).isEqualTo(1);

            long spring = postRepository.countByTitleContains("spring");
            assertThat(spring).isEqualTo(1);
        }
    }
    ```

### 새로운 Repository Interface 정의하기

> [참조](https://engkimbs.tistory.com/823?category=772527)

- 기본적으로 Spring JPA에서 제공하는 `JpaRepository`, `CrudRepository`를 용하지만, 만일 추가적인 기능을 개발하는 개발자에게 특정 메서드만 다룰 수 있게 한다거나, 삭제 관련 메서드(`deleteAll`)를 감추고 싶을 경우 Repository Interface를 직접 정의할 수 있습니다.

#### @RepositoryDefinition

- 소스 코드
    ```java
    @RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
    public interface CommentRepository {

        Comment save(Comment comment);

        List<Comment> findAll();

    }
    ```
    - `@RepositoryDefinition`은 `domainClass`와 `idClass`, 두 개의 파라미터를 정의해야 합니다.
        - `domainClass` : 이 Repository가 인터페이스하는 엔티티/테이블
        - `idClass` : 이 `domainCalss`를 식별할 때 사용하는 클래스의 타입
    - `CrudRepository`나 `JpaRepository`에 정의되어 있는 인터페이스 메서드를 참조해 메서드를 정의할 수 있습니다. 이 메서드들을 통해 엔티티와 테이블 간 상호작용을 할 수 있게 됩니다.
- 테스트 코드
    ```java
    @RunWith(SpringRunner.class)
    @DataJpaTest
    public class CommentRepositoryTest {

        @Autowired
        CommentRepository commentRepository;

        @Test
        public void crud() {
            Comment comment = new Comment();
            comment.setComment("Hello Comment");
            commentRepository.save(comment);

            List<Comment> all = commentRepository.findAll();
            assertThat(all.size()).isEqualTo(1);
        }

    }
    ```

#### 기존 Repository 인터페이스를 상속받아 새로운 Repository 만들기

- `Repository` 인터페이스를 상속받아 `MyRepository`를 정의한 예제
    - 소스 코드
        ```java
        @NoRepositoryBean
        public interface MyRepository<T, ID extends Serializable> extends Repository<T, ID> {

            <E extends T> E save(E entity);

            List<T> findAll();

            long count();
        }
        ```
        - `Repository`는 특별한 기능을 제공하는 인터페이스가 아닌 **마크 인터페이스** 입니다. `이 인터페이스가 Repository 용도로 사용될 것이다`를 알리는 용도로 쓰이죠. 
        - `@NoRepositoryBean` 은 이 인터페이스가 Repository 용도로서 사용되는 것이 아닌 단지 **Repository의 메서드를 정의하는 인터페이스라는 정보를 부여합니다.**
        - 위 예제와 마찬가지로 `save`, `findAll` 같은 메서드를 정의하면 이 메서드를 통하여 엔티티와 테이블 간에 상호작용이 일어나게 됩니다.
        <br>
        ```java
        public interface CommentRepository extends MyRepository<Comment, Long>{

            Comment save(Comment comment);

            List<Comment> findAll();
        }
        ```
        - `MyRepository` 인터페이스를 상속받아 제너릭으로 정의된 인수 부분을 구체적인 엔티티와 타입명으로 정의합니다.
    - 테스트 코드
        ```java
        @RunWith(SpringRunner.class)
        @DataJpaTest
        public class CommentRepositoryTest {

            @Autowired
            CommentRepository commentRepository;

            @Test
            public void crud() {
                Comment comment = new Comment();
                comment.setComment("Hello Comment");
                commentRepository.save(comment);

                List<Comment> all = commentRepository.findAll();
                assertThat(all.size()).isEqualTo(1);

                long count = commentRepository.count();
                assertThat(count).isEqualTo(1);
            }
        }
        ```

## 스프링 데이터 Null 체크

> [출처](https://engkimbs.tistory.com/824?category=772527)

- 소스 코드
    ```java
    @NoRepositoryBean
    public interface MyRepository<T, ID extends Serializable> extends Repository<T, ID> {

        <E extends T> E save(@NonNull E entity);

        List<T> findAll();

        long count();

        @Nullable
        <E extends T> Optional<E> findById(ID id);
    }
    ```
    - `@NonNull` : 파라미터에 Null 값이 들어오는 것을 방지하는 어노테이션
    - `@Nullable` : 이 메서드에서 Null 값을 허용될 수 있음을 보여주는 어노테이션. Repository 인터페이스에 리턴값을 `Optional`로 감싸서 후에 Null 값을 처리하게 할 수 있습니다.

## 스프링 데이터 쿼리 만들기

> [출처](https://engkimbs.tistory.com/825?category=772527)

- Repository 인터페이스 내에서 **메서드 명으로 쿼리를 만드는 방법**
    - `CREATE` : 메서드 이름을 분석해서 쿼리 만들기
    - `USE_DECLARED_QUERY` : 미리 정의해 둔 쿼리를 찾아 사용하기
    - `CREATE_IF_NOT_FOUND` : 미리 정의한 쿼리를 보고 없으면 만들기
    - 메서드 명 규칙
        ```
        리턴타입 {접두어}{도입부}By{프로퍼티 표현식}(조건식)[(And|Or){프로퍼티 표현식}(조건식)]{정렬조건}(매개변수)
        ```
        - 접두어 : `find`, `get`, `query`, `count`, ...
        - 도입부 : `Distinct`, `First(N)`, `Top(N)`
        - 프로퍼티 표현식 : `Person`, `Address`, `ZipCode` → `find(Person)`,  `ByAddress_ZipCode(...)`
        - 조건식 : `IgnoreCase`, `Between`, `LessThan`, `GreaterThan`, `Like`, `Contains`, ...
        - 정렬 조건 : `OrderBy{프로퍼티}Asc|Desc`
        - 리턴 타입 : `E`, `Optional<E>`, `List<E>`, `Page<E>`, `Slice<E>`, `Stream<E>`
        - 매개 변수 : `Pageable`, `Sort`
- `@Query`, `@NamedQuery`를 이용하여 쿼리를 미리 정의해두고 사용할 수 있습니다.
    - 유념해야할 것은 **쿼리를 찾는 방식은 저장소마다 다릅니다.** 따라서 찾는 방식이 어떤 매커니즘으로 이루어지는지 알아야 제대로 된 쿼리가 적용될 것을 예측할 수 있습니다.

- 소스 코드
    ```java
    @NoRepositoryBean
    public interface MyRepository<T, ID extends Serializable> extends Repository<T, ID> {

        <E extends T> E save(@NonNull E entity);

        List<T> findAll();

        long count();

        @Nullable
        <E extends T> Optional<E> findById(ID id);
    }
    ```
    ```java
    @Entity
    @Getter
    @Setter
    public class Comment {

        @Id
        @GeneratedValue
        private Long id;

        private String comment;

        @ManyToOne
        private Post post;

        private Integer likeCount = 0;

    }
    ```
    ```java
    public interface CommentRepository extends MyRepository<Comment, Long>{
        
        // Keyword 기준으로 대소문자 관계 없이 likeCount가 like 인자보다 많은 Comment 엔티티를 찾아라.
        List<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keyword, int like);

        // Keyword 기준으로 Pageable한 요청 객체를 받아 대소문자 관계 없이 Comment 엔티티를 찾아서 Page 형태로 엔티티를 반환하라.
        Page<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);

    }
    ```
- 테스트 코드
    ```java
    @RunWith(SpringRunner.class)
    @DataJpaTest
    public class CommentRepositoryTest {

        @Autowired
        CommentRepository commentRepository;

        @Test
        public void crud() {
            this.createComment(100, "spring data jpa");
            this.createComment(50, "hibernate spring");

            List<Comment> comments = commentRepository.findByCommentContainsIgnoreCaseOrderByLikeCountDesc("Spring");

            assertThat(comments.size()).isEqualTo(2);
            assertThat(comments).first().hasFieldOrPropertyWithValue("likeCount", 100);
        }

        private void createComment(int likeCount, String content) {
            Comment comment = new Comment();
            comment.setLikeCount(likeCount);
            comment.setComment(content);
            commentRepository.save(comment);
        }

    }
    ```
    ```java
    @RunWith(SpringRunner.class)
    @DataJpaTest
    public class CommentRepositoryTest {

        @Autowired
        CommentRepository commentRepository;

        @Test
        public void crud() {
            this.createComment(100, "spring data jpa");
            this.createComment(50, "hibernate spring");

            PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "LikeCount"));
            Page<Comment> comments = commentRepository.findByCommentContainsIgnoreCase("spring", pageRequest);
            assertThat(comments.getNumberOfElements()).isEqualTo(2);
            assertThat(comments).first().hasFieldOrPropertyWithValue("likeCount", 100);
        }

        private void createComment(int likeCount, String content) {
            Comment comment = new Comment();
            comment.setLikeCount(likeCount);
            comment.setComment(content);
            commentRepository.save(comment);
        }
    }
    ```

## 스프링 데이터 커스텀 Repository

> [출처](https://engkimbs.tistory.com/826?category=772527)

### 새로운 Repository 만들기

- 초기 setting
    ```properties
    spring.jpa.properties.hibernate.format_sql=true
    logging.level.org.hibernate.type.descriptor.sql=trace
    ```
    ```java
    // @Data : Lombok의 기능입니다. `Setter`, `Getter`, `ToString`, `Constructor`를 자동으로 생성해줍니다.
    @Data
    @Entity
    public class Post {

        @Id
        @GeneratedValue
        private Long id;

        private String title;

        // @Lob : varchar를 넘어서는 큰 내용을 넣고 싶은 경우
        @Lob
        private String content;

        // @Temporal : 날짜 Type 매핑 (java.util.Date, java.util.Calendar)
        // DATE - 날짜, TIME - 시간, TIMESTAMP - 날짜 & 시간
        @Temporal(TemporalType.TIMESTAMP)
        private Date created;

    }
    ```
    ```java
    public interface PostCustomRepository<T> {

        List<Post> findMyPost();

        void delete(T entity);
    }
    ```
    - 사용자가 정의한 커스텀 Repository. 이 인터페이스를 `implements`한 접미사가 `Impl`인 클래스는 커스텀 인터페이스 클래스의 구현체가 자동적으로 할당되어 사용됩니다.
    - 스프링 데이터에서 기본 기능으로서 제공하는 `delete` 메서드도 Custom Repository에서 구현하면 덮어쓰기가 가능해집니다.

- `PostCustomRepository`를 구현한 `PostCustomRepositoyImpl`
    ```java
    @Repository
    @Transactional
    public class PostCustomRepositoryImpl implements PostCustomRepository {

        @Autowired
        EntityManager entityManager;

        @Override
        public List<Post> findMyPost() {
            System.out.println("custom findMyPost");
            return entityManager.createQuery("SELECT p FROM Post as p", Post.class)
                    .getResultList();
        }

        @Override
        public void delete(Object entity) {
            System.out.println("custom delete");
            entityManager.remove(entity);
        }

    }
    ```

    - `PostCustomRepository`를 구현한 클래스입니다. 위 코드에서 `EntityManager`는 엔티티를 저장하고 관리하는 역할을 합니다.
    - `PostCustomRepository`를 구현한 메서드들은 `PostRepository`에 의존성이 주입될 때 사용할 수 있게 됩니다.

- 기존 `PostRepository`에 커스텀 Repository 추가
    ```java
    public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository<Post> {

    }
    ```
    - `PostCustomRepository` 인터페이스를 확장한 `PostRepository`를 통하여 `PostCustomRepository`에서 정의한 메서드들을 사용할 수 있습니다.

- 테스트 코드
    ```java
    @RunWith(SpringRunner.class)
    @DataJpaTest
    public class DemoApplicationTests {

        @Autowired
        PostRepository postRepository;

        @Test
        public void crud() {
            postRepository.findMyPost();

            Post post = new Post();
            post.setTitle("hibernate");
            postRepository.save(post);

            postRepository.findMyPost();

            postRepository.delete(post);
            postRepository.flush();
        }
    }
    ```

### `JpaRepository` 커스터마이징

- `JpaRepository`를 확장한 `MyRepository`
    ```java
    // @NoRepositoryBean : 이 인터페이스가 Repository로서 직접적으로 기능하지 않을 것이라는 것을 명시함.
    @NoRepositoryBean
    public interface MyRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

        boolean contains(T entity);

    }
    ```
- `MyRepository`의 구현체 `MyRepositoryImpl`
    ```java
    public class MyRepositoryImpl<T, ID extends Serializable>
            extends SimpleJpaRepository<T, ID> implements MyRepository<T, ID>{

        private EntityManager entityManager;

        public MyRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
            super(entityInformation, entityManager);
            this.entityManager = entityManager;
        }

        @Override
        public boolean contains(T entity) {
            return entityManager.contains(entity);
        }
    }
    ```
    - 위 Repository의 구현체는 `JpaRepository` 구현체 중 **Default 구현체인 `SimpleJpaRepository`를 상속받아 작성할 수 있습니다.** 이것을 통해 개발자에게 노출되는 Repository 인터페이스에서 `JpaRepository`를 따로 인터페이스를 확장하지 않고 아래와 같이 `MyRepository` 인터페이스만 확장하여 쓸 수 있습니다.
    ```java
    public interface PostRepository extends MyRepository<Post, Long> {

    }
    ```
- 테스트 코드
    ```java
    @RunWith(SpringRunner.class)
    @DataJpaTest
    public class DemoApplicationTests {

        @Autowired
        PostRepository postRepository;

        @Test
        public void crud() {
            Post post = new Post();
            post.setTitle("hibernate");

            assertThat(postRepository.contains(post)).isFalse();

            postRepository.save(post);

            assertThat(postRepository.contains(post)).isTrue();
        }
    }
    ```

## 스프링 데이터 도메인 이벤트

> [출처](https://engkimbs.tistory.com/827?category=772527)

- 스프링 프레임워크에서는 IoC 컨테이너에 접근하기 위한 `ApplicationContext` 인터페이스를 제공합니다. 
- `ApplicationContext`는 `ApplicationEventPublisher`를 상속 받습니다. 따라서 사용자가 정의한 이벤트나 스프링 프레임워크에서 미리 정의한 이벤트를 publish하는 기능도 제공하고 있습니다.
- 사용자가 정의한 이벤트를 수신하기 위해서는 `Listener`를 정의하여 해당 클래스에 맞는 이벤트를 받아 처리하면 됩니다.

- 객체 `Post`
    ```java
    @Entity
    @Data
    public class Post {

        @Id
        @GeneratedValue
        private Long id;

        private String title;

        @Lob
        private String content;

        @Temporal(TemporalType.TIMESTAMP)
        private Date created;
    }
    ```
- `ApplicationEvent`를 상속하여 Custom Event를 작성
    ```java
    public class PostPublishedEvent extends ApplicationEvent {

        private final Post post;

        public PostPublishedEvent(Object source) {
            super(source);
            this.post = (Post) source;
        }

        public Post getPost() {
            return post;
        }

    }
    ```
    - `Post` 객체를 받는 생성자와 저장된 Post 객체의 Getter만을 작성.
- `PostListener`
    ```java
    @Component
    public class PostListener implements ApplicationListener<PostPublishedEvent> {

        @Override
        public void onApplicationEvent(PostPublishedEvent event) {
            System.out.println("------------------------");
            System.out.println(event.getPost().getTitle() + " is published");
            System.out.println("------------------------");
        }
    }
    ```
    - `PostListener`는 `PostPublishedEvent`가 publish될 때 그 이벤트를 수신하여 출력하는 Event Listener
- testing with `AppRunner`
    ```java
    @Component
    public class AppRunner implements ApplicationRunner {

        @Autowired
        ApplicationContext applicationContext;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            Post post = new Post();
            post.setTitle("POST EVENT");
            PostPublishedEvent event = new PostPublishedEvent(post);

            applicationContext.publishEvent(event);
        }
    }
    ```
    - `ApplicationContext`의 구현체는 이벤트를 publish할 수 있는 기능을 제공합니다. 따라서 `PostPublishedEvent`를 `ApplicationContext`를 통하여 publish하면 그 이벤트가 위에서 작성한 `PostListener`에게 수신되는 것을 알 수 있습니다.