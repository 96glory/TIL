# 어노테이션으로 Dependency Injection

## 어노테이션의 장점

> [강의 링크](https://bit.ly/3jd6AAO)

- 다양한 어노테이션
    - ![image](https://user-images.githubusercontent.com/52440668/94403799-e9963180-01a8-11eb-8f90-ab4de5e42fc5.png)
- 어노테이션의 장점
    - XML에서의 설정 변경이 귀찮아짐. 이젠 코드 안에서 객체 연결이 가능하게끔 하자.

## `@Autowired`를 이용한 DI

> [강의 링크](https://bit.ly/3jd6AAO)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--
        @Autowired가 있으니 클래스를 탐색해서 DI 해달라고 IoC 컨테이너에게 알려줌!
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

## `@Autowired`의 동작 방식 이해

> [강의 링크](https://bit.ly/2GgQwiy)

- **무슨 객체를 주입하느냐 판단하는 기준은 클래스가 일치 여부다.**

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