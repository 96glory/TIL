# XML 기반의 DI 지시서 작성하기

## XML 기반의 spring bean configuration file

- XML 파일 기본 틀
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    </beans>
    ```

### 지시서 작성하기

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

> [강의 링크](https://bit.ly/2RB apnc)

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

### 값 형식의 속성에 값 설정하기 

#### setter 사용

> [강의 링크](https://bit.ly/3cGeXSL)

```xml
<!-- setting.xml, package : spring.di -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="exam" class="spring.di.entity.NewlecExam">
        <!--
            아래 property에 값을 넣어주기 위해 class NewlecExam에 setter를 포함해 주어야 함.
            아래 kor문과 같이 중첩된 구조로 표현할 수 있다.
        -->
        <property name="kor">
            <value>10</value>
        </property>
        <property name="eng"  value="10" />
        <property name="math" value="10" />
        <property name="com"  value="10" />
    </bean>

    <bean id="console" class="spring.di.ui.GridExamConsole" >
        <property name="exam" ref="exam"/>
    </bean>
</beans>
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

    public int getKor(){
        return kor;
    }

    public void setKor(int kor){
        this.kor = kor;
    }

    public int getEng(){
        return eng;
    }

    public void setEng(int eng){
        this.eng = eng;
    }

    public int getMath(){
        return math;
    }

    public void setMath(int math){
        this.math = math;
    }

    public int getCom(){
        return com;
    }

    public void setCom(int com){
        this.com = com;
    }

}
```

#### 생성자 사용

> [강의 링크](https://bit.ly/30g66Ce)

```xml
<!-- setting.xml, package : spring.di -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="exam" class="spring.di.entity.NewlecExam">
        <!--
            아래 constructor-arg에 값을 넣어주기 위해 class NewlecExam에 생성자를 포함해 주어야 함.
            버그 발생 때문에 인덱스 및 네임 설정을 해주면 좋다.
        -->
        <constructor-arg name="kor" value="10" />
        <constructor-arg name="eng" value="10" />
        <constructor-arg index="2"  value="10" />
        <constructor-arg index="3"  value="10" />
        
        <!--
            메서드의 오버로딩 때문에 type 지원도 한다.

            만약,
            public NewlecExam(int kor, int eng, int math, int com)과
            public NewlecExam(float kor, float eng, float math, float com)
            이 동시에 있을 때

            <constructor-arg name="kor" type="float" value="10" />
            <constructor-arg name="eng" type="float" value="10" />
            <constructor-arg index="2"  type="float" value="10" />
            <constructor-arg index="3"  type="float" value="10" />
        -->
    </bean>

    <bean id="console" class="spring.di.ui.GridExamConsole" >
        <property name="exam" ref="exam"/>
    </bean>
</beans>
```

```java
package spring.di.entity;

public class NewlecExam implements Exam {

    private int kor;
    private int eng;
    private int math;
    private int com;

    public NewlecExam() {

    }

    public NewlecExam(int kor, int eng, int math, int com){
        this.kor = kor;
        this.eng = eng;
        this.math = math;
        this.com = com;
    }

    @Override
    public int total() {
        return kor + eng + math + com;
    }

    @Override
    public float avg() {
        return total() / 4.0f;
    }

    public int getKor(){
        return kor;
    }

    public void setKor(int kor){
        this.kor = kor;
    }

    public int getEng(){
        return eng;
    }

    public void setEng(int eng){
        this.eng = eng;
    }

    public int getMath(){
        return math;
    }

    public void setMath(int math){
        this.math = math;
    }

    public int getCom(){
        return com;
    }

    public void setCom(int com){
        this.com = com;
    }

}
```

- 설정 파일 처리기를 추가하여 더 심플한 XML 작성하기
    ```xml
    <!-- setting.xml, package : spring.di -->
    <?xml version="1.0" encoding="UTF-8"?>

    <!-- 추가된 코드 : xmlns:p -->
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:p="http://www.springframework.org/schema/p"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

        <bean id="exam" class="spring.di.entity.NewlecExam" p:kor="10" p:eng="10" p:math="10" p:math="10" />

        <bean id="console" class="spring.di.ui.GridExamConsole" >
            <property name="exam" ref="exam"/>
        </bean>
    </beans>
    ```

### 콜렉션 생성과 DI

> [강의 링크](https://bit.ly/2S6VRf3)

```java
package spring.di;

public class Program {

    public static void main(String[] args){

        ApplicationContext context = new ClassPathXmalApplicationContext("spring/di/setting.xml");
        
        List<Exam> exams = (List<Exam>) context.getBean("exams");

        // 아래 코드를 지시서로 넘겨보자.
        // exams.add(new NewlecExam(1, 1, 1, 1));

        for(Exam e : exams)
            System.out.println(e);
    }

}
```

```xml
<!-- setting.xml, package : spring.di -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:p="http://www.springframework.org/schema/p"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
    <bean id="exam" class="spring.di.entity.NewlecExam" p:kor="10" p:eng="10" p:math="10" p:math="10" />

    <bean id="exams" class="java.util.ArrayList">
        <constructor-arg>
            <list>
                <ref bean="exam" />
                <bean class="spring.di.entity.NewlecExam" p:kor="20" p:eng="20" p:math="20" p:math="20" />
            </list>
        </constructor-arg>
    </bean>

</beans>
```