# JPA Fetch

> [출처](https://engkimbs.tistory.com/818?category=772527)

### Fetch

- 연관 관계의 엔티티를 어떻게 가져올 것인지를 정하는 정책.
    - `@OneToMany`의 기본 값은 **Lazy**
    - `@ManyToOne`의 기본 값은 **Eager**

### `@OneToMany`의 Lazy를 Eager로 바꾸기

- `Post` : 게시판에 대한 정보를 나타내는 엔티티
    ```java
    @Entity
    @Getter
    @Setter
    public class Post {

        @Id
        @GeneratedValue
        private Long id;

        private String title;

        @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
        private Set<Comment> comments = new HashSet<>();

        public void addComment(Comment comment){
            this.comments.add(comment);
            comment.setPost(this);
        }

    }
    ```
- `Comment` : 게시판 댓글을 나타내는 엔티티
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

    }
    ```
- `JpaRunner`
    ```java
    @Component
    @Transactional
    public class JpaRunner implements ApplicationRunner {

        @PersistenceContext
        EntityManager entityManager;

        @Override
        public void run(ApplicationArguments args) throws Exception {

        Session session = entityManager.unwrap(Session.class);
        Post post = session.get(Post.class, 1l);
        System.out.println("==================");
        System.out.println(post.getTitle());

        }
    }
    ```
    - 위와 같이 데이터를 가져올 시, 연관관계를 가지는 `Comment`에 대한 정보를 로딩하지 않고 `Post`에 대한 정보만 가져오게 됩니다. 왜냐하면 `@OneToMany`는 `FetchType.LAZY`가 기본값으로 설정되어 있기 때문입니다.

### Eager : 즉시 로딩

- `Post` : 게시판에 대한 정보를 나타내는 엔티티
    ```java
    @Entity
    @Getter
    @Setter
    public class Post {

        @Id
        @GeneratedValue
        private Long id;

        private String title;

        // 추가된 EAGER 옵션
        @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        private Set<Comment> comments = new HashSet<>();

        public void addComment(Comment comment){
            this.comments.add(comment);
            comment.setPost(this);
        }

    }
    ```
    - `FetchType.EAGER` 옵션을 주게 될 경우 `Post`와 연관된 모든 `Comment`에 대한 정보를 가져오게 됩니다.

### `@ManyToOne`의 Eager

- `JpaRunner`
    ```java
    @Component
    @Transactional
    public class JpaRunner implements ApplicationRunner {

        @PersistenceContext
        EntityManager entityManager;

        @Override
        public void run(ApplicationArguments args) throws Exception {

            Session session = entityManager.unwrap(Session.class);
            Comment comment = session.get(Comment.class, 2l);
            System.out.println("==================");
            System.out.println(comment.getComment());
            System.out.println(comment.getPost().getTitle());
            
        }

    }
    ```
    - `Comment`를 가져올 경우 자동적으로 그와 매핑되어 있는 `Post`에 대한 정보를 가져오게 된다. 매핑 정보에 저장되어 있는 `@ManyToOne`은 `FetchType.EAGER`가 기본적으로 설정되어 있기 때문에 매핑되어 있는 연관된 엔티티에 대한 데이터를 로딩하기 때문이다.