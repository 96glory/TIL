# 스프링 데이터 Common Repository

> [출처](https://engkimbs.tistory.com/822?category=772527)

### `JpaRepository<Entity, ID-TYPE>`

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
        - `@DataJpaTest`에서는 기본적으로 `@Transational`이 추가되어 있어 기본적으로 각 테스트들이 수행되었을 때 rollback를 하게 됩니다. 따라서 **하이버네이트는 rollback될 쿼리는 수행할 필요가 없는 쿼리라 인식하여 Repository에 엔티티를 저장하는 insert문을 날리지 않게 됩니다.** 따라서 원하는 테스트를 하기 위해서는 `@Roallback(false)`를 추가하여 rollback을 하지 않겠다는 정보를 부가해야합니다.
    - 위 코드를 실행하게 되면 `Post`에 원래는 할당되지 않는 `id`에 값이 할당되어있지 않았습니다. (`assertThat(post.getId()).isNull();`가 통과됨)
    - `postRepository.save(post)`를 실행하게 되면서 `@GeneratedValue`에 의해 멤버 변수 `id`에 값이 할당되게 됩니다.
    - 따라서 위 테스트는 통과됩니다.
- 테스트 코드 : 페이징 기능 구현
    - [`Pagable` 정보](https://gunju-ko.github.io/spring/2018/05/01/Spring-Data-JPA-Paging.html)
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