# 스프링에서 DI와 IoC 컨테이너 다뤄보기

## Dependecy를 직접 Injection 하기

> [강의 링크](https://www.youtube.com/watch?v=gtqctgfywn4&list=PLq8wAnVUcTFUHYMzoV2RoFoY2HDTKru3T&index=5&frags=wn&ab_channel=%EB%89%B4%EB%A0%89%EC%B2%98)

```java
package spring.di;

public class Program {

    public static void main(String[] args){
        Exam exam = new NewlecExam();

        // 직접 DI 하기!
        // ExamConsole console = new InlineExamConsole(exam);
        ExamConsole console = new GridExamConsole(exam);

        // 위 interface를 연결해주는 과정을 코드 수정 없이 가능할까? -> 스프링

        console.print();
    }

}
```

```java
package spring.di.entity;

public interface Exam{
    int total();
    float avg();
}
```

```java
package spring.di.entity;

public class NewlecExam implements Exam {

    private int kor;
    private int eng;
    private int math;
    private int com;

    @Override
    public int total() {
        return kor + eng + math + com;
    }

    @Override
    public float avg() {
        return total() / 4.0f;
    }

}
```

```java
package spring.di.ui;

public interface ExamConsole {
    void print();
}
```

```java
package spring.di.ui;

public class InlineExamConsole {

    private Exam exam;

    public InlineExamConsole(Exam exam){
        this.exam = exam;
    }

    @Override
    public void print(){
        System.out.printf("total is %d, avg is %f\n", exam.total(), exam.avg());
    }

}
```

```java
package spring.di.ui;

public class GridExamConsole {

    private Exam exam;

    public GridExamConsole(Exam exam){
        this.exam = exam;
    }

    @Override
    public void print(){
        System.out.printf("============================\n");
        System.out.printf("total is %3d, avg is %3.2f\n", exam.total(), exam.avg());
        System.out.printf("============================\n");
    }
    
}
```

## Spring DI 지시서 (Spring Bean Configuration) 작성하기

### XML 기반의 spring bean configuration file

- XML 파일 기본 틀
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    </beans>
    ```

#### 지시서 작성하기
> [강의 링크](https://bit.ly/32EUtXo)
```java
package spring.di;

public class Program {

    public static void main(String[] args){
        /*
            GridExamConsole에서 InlineExamConsole으로 바뀌어야 한다면
            정적 코드를 수정하는 것보다 따로 설정 파일로 빼보자.
            부품을 생성하고 조립해서 (아래 3줄) console로 넘겨주는 행위를 스프링이 해줄 것이다.

            스프링에게 지시하는 방법으로 코드를 변경
            (아래 setting.xml를 살펴보자)

        Exam exam = new NewlecExam();
        ExamConsole console = new GridExamConsole();
        console.setExam(exam);  // 새로 생긴 메서드
        */

        ExamConsole console = ???;
        console.print();
    }

}
```
```xml
<!-- setting.xml, package : spring.di -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- Exam exam = new NewlecExam(); -->
    <bean id="exam" class="spring.di.entity.NewlecExam" />

    <!-- ExamConsole console = new GridExamConsole(); -->
    <bean id="console" class="spring.di.ui.GridExamConsole" >
        <!--
            console.setExam(exam);
            메서드 이름이 setExam이라면 property.name에는 exam이라고 적어야 한다.
            property.ref는 참조되는 객체 이름이다.
            값일 경우 property.value에 넣으면 된다.
        -->
        <property name="exam" ref="exam"/>
    </bean>

</beans>
```
 ```java
 package spring.di.ui;

 public interface ExamConsole {
     void print();
     void setExam(Exam exam);
 }
 ```
 ```java
 package spring.di.ui;

 public class InlineExamConsole {

     private Exam exam;

     public InlineExamConsole(){

     }

     public InlineExamConsole(Exam exam){
         this.exam = exam;
     }

     @Override
     public void print(){
         System.out.printf("total is %d, avg is %f\n", exam.total(), exam.avg());
     }

     @Override
     public void setExam(Exam exam){
         this.exam = exam;
     }

 }
 ```
 ```java
 package spring.di.ui;

 public class GridExamConsole {

     private Exam exam;

     public GridExamConsole(){

     }

     public GridExamConsole(Exam exam){
         this.exam = exam;
     }

     @Override
     public void print(){
         System.out.printf("============================\n");
         System.out.printf("total is %3d, avg is %3.2f\n", exam.total(), exam.avg());
         System.out.printf("============================\n");
     }

     @Override
     public void setExam(Exam exam){
         this.exam = exam;
     }
     
 }
 ```
    
#### 스프링 IoC 컨테이너를 사용해서 지시서대로 객체를 만들어보기

> [강의 링크](https://bit.ly/2RBapnc)

- Application Context 생성하기
    - `ApplicationContext`는 인터페이스,
    - `ClassPathXmlApplicationContext`, `FileSystemXmlApplicationContext`, `XmlWebApplicationContext`, `AnnotationConfigApplicationContext`는 인터페이스 구현체

    ```java
    ApplicationContext context = new ClassPathXmlApplicationContext("config.xml");
    ```

- 예시에서 `ApplicationContext` 생성하기

    ```java
    package spring.di;

    // maven에서 spring boot dependency 가져오는 것은 생략

    public class Program {

        public static void main(String[] args){
            // context가 IoC 컨테이너의 역할을 함.
            ApplicationContext context = new ClassPathXmlApplicationContext("spring/di/setting.xml");
            
            /*
                getBean(String a)는 Object로 반환하기 때문에 type 변환을 해야 한다.
                ExamConsole console = (ExamConsole) context.getBean("console");
            */

            // getBean()의 파라미터에 클래스 타입을 넣게 되면, 스프링이 알아서 알맞는 객체를 주입해준다.
            ExamConsole console = context.getBean(ExamConsole.class);
            console.print();
        }

    }
    ```

    - GridExamConsole에서 InlineExamConsole으로 바뀌어야 한다면 XML 파일만 바꿔주면 된다!