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