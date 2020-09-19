# JPA CASCADE

> [출처](https://engkimbs.tistory.com/817?category=772527)

### CASCADE

- 엔티티의 상태 변화를 전파시키는 옵션
- 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티단방향 혹은 양방향)도 함께 영속 상태로 만들고 싶은 우 사용한다.

### Cascade 옵션을 주지 않았을 경우

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

        @OneToMany(mappedBy = "post")
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

- Cascade 옵션을 주지 않은 상태이므로 아래 `JpaRunner`를 실행시켜도 `Post`에 대한 정보만 DB에 반영될 뿐, `Comment` 정보는 반영되지 않습니다.
    ```java
    @Component
    @Transactional
    public class JpaRunner implements ApplicationRunner {

        @PersistenceContext
        EntityManager entityManager;

        @Override
        public void run(ApplicationArguments args) throws Exception {
            Post post = new Post();
            post.setTitle("Spring Data Title");

            Comment comment = new Comment();
            comment.setComment("Great Spring Data");
            post.addComment(comment);

            Comment comment1 = new Comment();
            comment1.setComment("Yes it is");
            post.addComment(comment1);

            Session session = entityManager.unwrap(Session.class);
            session.save(post);
        }

    }
    ```
    ```shell
    springboot=# select * from post;
    id  |       title
    ----+-------------------
    1   | Spring Data Title
    (1 row)

    springboot=# select * from comment;
    id  | name | owner_id
    ----+------+----------
    (0 rows)
    ```

### Cascade 옵션을 줄 경우

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

        // 추가된 cascade 옵션
        @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
        private Set<Comment> comments = new HashSet<>();

        public void addComment(Comment comment){
            this.comments.add(comment);
            comment.setPost(this);
        }

    }
    ```
- `JpaRunner`
    ```shell
    springboot=# select * from post;
    id  |       title
    ----+-------------------
    1 | Spring Data Title
    (1 row)

    springboot=# select * from comment;
    id  |      comment      | post_id
    ----+-------------------+---------
    2   | Yes it is         |       1
    3   | Great Spring Data |       1
    (2 rows)
    ```

### Cascade 옵션

- Persist : `Post`를 영속화 할 때 연관된 `Comment`도 함께 영속화 하는 경우
- Remove : `Post`를 제거할 때 연관된 `Member`도 제거
