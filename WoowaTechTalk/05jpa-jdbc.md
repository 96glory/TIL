# JPA와 JDBC

> [강의 링크](https://www.youtube.com/watch?v=Ppqc3qN75EE)

### JDBC

- jdbc로 체스 과제 구현하기

  - 테이블 생성
    ```sql
    create table chess_board(
        id int auto_increment primary key,
        roomId int not null,
        positionX int not null,
        positionY int not null,
        unit char(1) not null
    );
    ```
  - connection 설정

    ```java
    public class DBConnection {

        public static Connection getConnection() throws SQLException {
            Connection con = null;
            String server = "127.0.0.1";
            String database = "chess";
            String userName = "glory";
            String password = "123";

            con = DriverManager.getConnection("jdbc:mysql://" + server + "/" + database, userName, password);

            return con;
        }

    }
    ```

  - 데이터 삽입

    ```java
    public class ChessBoardDAO {

        private Connection connection;

        public ChessBoardDAO(Connection connection) {
            this.connection = connection;
        }

        public void add(ChessBoard chessBoard, Team team, int roomId) throws SQLException {
            for(Position position : chessBoard.getUnits().keySet()) {
                String query = "INSERT INTO chess_board (roomId, positionX, positionY, unit) " + "Values ( ?, ?, ?, ?)";
                PreparedStatement pstmt = connection.prepareStatement(query);
                pstmt.setInt(1, roomId);
                pstmt.setInt(2, position.getX());
                pstmt.setInt(3, position.getY());
                pstmt.setString(4, chessBoard.getUnit(position).get().toString());
                pstmt.executeUpdate();
            }
        }

    }
    ```

- ![image1](https://user-images.githubusercontent.com/52440668/91015853-cf57b980-e626-11ea-932e-c11582639975.png)
- JDBC의 특징
  - SQL문
  - connection 관리 : DB와 APP의 연결을 관리
  - 객체 preparedstatement : DB에 SQL을 전달
  - 객체 resultset : APP에 결과값을 전달

### JPA

- ![image2](https://user-images.githubusercontent.com/52440668/91016001-0a59ed00-e627-11ea-9b9f-e628dc50ff51.png)
- JPA의 장점

  - SQL문을 직접 작성할 일이 적어진다.

    ```java
    public class ChessBoardDAO{

        @Autowired
        ChessBoardRepository chessBoardRepository;

        public void add(ChessBoard chessBoard){
            chessBoardRepository.save(chessBoard);
        }

    }
    ```

  - SQL 구조를 자바 애플리케이션 내에서 적용하지 않아도 된다.

    - Order table이 foreign key로 Member table의 member id를 참조하는 경우

      ```java
      // Nor improved
      @Entity
      @Table(name = "ORDERS")
      public class Order{

          @Id @GeneratedValue @Column(name = "ORDER_ID")
          private Long id;

          @Column(name = "MEMBER_ID")
          private Long memberId;

          // ...
      }
      ```

      ```java
      Member member = members.findById(order.memberId);
      ```

      ```java
      // @JoinColumn를 통해 개선된 case
      @Entity
      @Table(name = "ORDERS")
      public class Order{

          @Id @GeneratedValue @Column(name = "ORDER_ID")
          private Long id;

          @ManyToOne
          @JoinColumn(name = "MEMBER_ID")
          private Long memberId;

          // ...
      }
      ```

      ```java
      Member member = orders.member;
      ```
