# 어노테이션으로 Dependency Injection

## 어노테이션의 장점

> [강의 링크](https://bit.ly/3jd6AAO)

- 다양한 어노테이션
    - ![image](https://user-images.githubusercontent.com/52440668/94403799-e9963180-01a8-11eb-8f90-ab4de5e42fc5.png)
- 어노테이션의 장점
    - XML에서의 설정 변경이 귀찮아짐. 이젠 코드 안에서 객체 연결이 가능하게끔 하자.

## `@Autowired`

### `@Autowired`를 이용한 DI

> [강의 링크](https://bit.ly/3jd6AAO)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--
        @Autowired가 있으니 IoC 컨테이너에게 클래스를 탐색해서 DI 해달라고 명시함!
        그러기 위해서 xmlns:context를 추가해 주어야 함.
    -->
    <context:annotation-config />

    <bean id="exam" class="spring.di.entity.NewlecExam" />

    <bean id="console" class="spring.di.ui.InlineExamConsole" >
        <!-- <property name="exam" ref="exam"/> -->
    </bean>

</beans>

```

- 위 `property`를 주석화 하면 아래 코드에서 `NullPointerException`이 발생한다.

```java
package spring.di;

public class Program {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/di/setting.xml");
        
        ExamConsole console = (ExamConsole) context.getBean("console");
        console.print();
    }

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

    // @Autowired 추가
    // 주석 처리된 XMl 코드가 @Autowired와 결과는 같다.
    @Autowired
    @Override
    public void setExam(Exam exam){
        this.exam = exam;
    }

}
```

### `@Autowired`의 동작 방식 이해

> [강의 링크](https://bit.ly/2GgQwiy)

- **무슨 객체를 주입하느냐 판단하는 기준은 클래스의 일치 여부다.**

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

        <context:annotation-config />

        <bean id="exam" class="spring.di.entity.NewlecExam" />

        <!-- id를 바꾸거나, 심지어 지워도 잘 주입된다. -->
        <!-- <bean id="exam1" class="spring.di.entity.NewlecExam" /> -->
        <!-- <bean class="spring.di.entity.NewlecExam" /> -->

        <bean id="console" class="spring.di.ui.InlineExamConsole" >
            <!-- <property name="exam" ref="exam"/> -->
        </bean>

    </beans>

    ```

    ```java
    package spring.di;

    public class Program {

        public static void main(String[] args){
            ApplicationContext context = new ClassPathXmlApplicationContext("spring/di/setting.xml");
            
            ExamConsole console = (ExamConsole) context.getBean("console");
            console.print();
        }

    }
    ```

- 같은 빈이 두 개 이상이라면?

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

        <context:annotation-config />

        <bean class="spring.di.entity.NewlecExam" p:kor="10" p:eng="10" p:math="10" p:math="10" />
        <bean class="spring.di.entity.NewlecExam" p:kor="20" p:eng="20" p:math="20" p:math="20" />

        <bean id="console" class="spring.di.ui.InlineExamConsole" >
            <!-- <property name="exam" ref="exam"/> -->
        </bean>

    </beans>
    ```
    
    - **에러 발생 : `mathcing bean but found 2`**
    
    - 해결 1 : id 붙여주기

        ```xml
        <bean id="exam" class="spring.di.entity.NewlecExam" p:kor="10" p:eng="10" p:math="10" p:math="10" />
        <bean class="spring.di.entity.NewlecExam" p:kor="20" p:eng="20" p:math="20" p:math="20" />

        <bean id="console" class="spring.di.ui.InlineExamConsole" >
            <!-- <property name="exam" ref="exam"/> -->
        </bean>
        ```

        - but, id 값이 달라지면 다시 에러 발생

            ```xml
            <bean id="exam1" class="spring.di.entity.NewlecExam" p:kor="10" p:eng="10" p:math="10" p:math="10" />
            <bean class="spring.di.entity.NewlecExam" p:kor="20" p:eng="20" p:math="20" p:math="20" />

            <bean id="console" class="spring.di.ui.InlineExamConsole" >
                <!-- <property name="exam" ref="exam"/> -->
            </bean>
            ```
    - 해결 2 : `@Qualifier`

        ```xml
        <bean id="exam1" class="spring.di.entity.NewlecExam" p:kor="10" p:eng="10" p:math="10" p:math="10" />
        <bean id="exam2" class="spring.di.entity.NewlecExam" p:kor="20" p:eng="20" p:math="20" p:math="20" />

        <bean id="console" class="spring.di.ui.InlineExamConsole" >
                <!-- <property name="exam" ref="exam"/> -->
        </bean>
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

            // @Qualifier 추가
            @Autowired
            @Qualifier("exam1")
            @Override
            public void setExam(Exam exam){
                this.exam = exam;
            }

        }
        ```

### `@Autowired`의 위치

> [강의 링크](https://bit.ly/3cDQBcl)

- `@Autowired`의 위치
    - setter
        - setter 함수가 호출하면서 injection이 이루어진다.
    - constructor
        - `@Qualifier`를 이용해서 빈의 id를 지정해주어야 할 때, 애매함이 발생함.
        - 따라서, 생성자의 파라미터에 직접 `@Qualifier`를 선언해주면 오류가 발생하지 않는다.
    - field
        - 기본 생성자를 호출하면서 injection이 이루어진다.
        - 기본 생성자를 선언하지 않으면 injection이 이루어지지 않는다.
        - 파라미터가 있는 생성자가 없다면 기본 생성자를 알아서 만들어 주기 때문에, 파라미터가 있는 생성자가 없으면 injection이 가능하다.

```java
public class InlineExamConsole implements ExamConsole {
    
    // field : @Autowired
    private Exam exam;

    public InlineExamConsole() {

    }

    // constructor : @Autowired
    // public InlineExamConsole(Exam exam) {
    //     this.exam = exam;
    // }
    @Autowired
    public InlineExamConsole(@Qualifier("exam1") Exam exam) {
        // ...
    }

    // setter : @Autowired
    public void setExam(Exam exam) {
        this.exam = exam;
    }

}
```

### `@Autowired`의 `Required` 옵션

> [강의 링크](https://bit.ly/3cDQBcl)

- `Required`
    - XML에 빈이 없으면, 없는 대로 로직을 수행하면 좋을 것 같다.
        ```java
        package spring.di.ui;

        public class InlineExamConsole {
            
            // 객체가 없어도 로직 수행에는 문제가 없다!
            @Autowired(required = false)
            @Qualifier("exam2")
            private Exam exam;

            public InlineExamConsole(){
                
            }

            public InlineExamConsole(Exam exam){
                this.exam = exam;
            }

            @Override
            public void print(){
                // 빈이 없는 경우
                if(exam == null)
                    System.out.printf("total is %d, avg is %f\n", 0, 0);

                // 빈이 있는 경우
                else
                    System.out.printf("total is %d, avg is %f\n", exam.total(), exam.avg());
            }

            @Override
            public void setExam(Exam exam){
                this.exam = exam;
            }

        }
        ```

## 어노테이션을 이용한 객체 생성

> [강의 링크](https://bit.ly/2S2lspE)

### 객체 생성과 `@Component`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 
        @Component가 붙은 클래스를 base-package로부터 찾고 주입.
        @Component가 붙은 클래스 내에 어노테이션 빈도 알아서 주입한다.
    -->

    <context:component-scan base-package="spring.di.ui, spring.di.entity" />
    <!-- <context:annotation-config /> -->
    <!-- <bean id="exam" class="spring.di.entity.NewlecExam" /> -->
    <!-- <bean id="console" class="spring.di.ui.InlineExamConsole">
        <property name="exam" ref="exam" />
    </bean> -->

</beans>
```

```java
package spring.di.ui;
@Component("console")
public class InlineExamConsole {
    
    @Autowired
    @Qualifier("exam2")
    private Exam exam;

    public InlineExamConsole(){
        
    }

    public InlineExamConsole(Exam exam){
        this.exam = exam;
    }

    @Override
    public void print(){
        if(exam == null)
            System.out.printf("total is %d, avg is %f\n", 0, 0);
        else
            System.out.printf("total is %d, avg is %f\n", exam.total(), exam.avg());
    }

    @Override
    public void setExam(Exam exam){
        this.exam = exam;
    }

}
```

```java
package spring.di;

public class Program {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/di/setting.xml");
        
        ExamConsole console = (ExamConsole) context.getBean("console");
        console.print();
    }

}
```

```java
package spring.di.entity;

@Component
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

    // getter, setter 생략
}
```

> [강의 링크](https://bit.ly/2Hv4LB0)

#### `@Component`의 종류와 도움 되는 어노테이션

- 기본 값 설정을 위한 `@Value`

    ```java
    @Component
    public class NewlecExam implements Exam {
        
        @Value("10")
        private int kor;

        @Value("20")
        private int eng;

        @Value("40")
        private int math;

        @Value("30")
        private int com;

        // ...
    }

- `@Component`의 종류
    - 의미론적 부여. 추가 기능 有
        - `@Controller`
            - `@RequestMapping`
            - `@GetMapping`
            - ...
        - `@Service`
        - `@Repository`
    - 왜 여러 종류인가.
        - 웹 애플리케이션을 개발할 때 의미를 분명히 하기 위해서
            - ![image](https://user-images.githubusercontent.com/52440668/94437892-575a5180-01d9-11eb-986d-f9a01ef64d2d.png)