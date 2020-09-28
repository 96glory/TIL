# Java Configuration

> [강의 링크](https://bit.ly/3cBrO8I)

- XML과 어노테이션 방식을 섞어서 지시서를 작성하기 보다 한 쪽만 사용하는 게 더 효율이 좋을 것이다.
- **기존 XML 지시서를 자바 지시서로 바꾸겠다.**
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:context="http://www.springframework.org/schema/context"
        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

        <context:component-scan base-package="spring.di.ui, spring.di.entity" />
        <bean id="exam" class="spring.di.entity.NewlecExam" />

    </beans>
    ```
    ```java
    package spring.di;

    @ComponentScan({"spring.di.ui", "spring.di.entity"})
    @Configuration
    public class NewlecDIConfig {
        
        // 함수 이름은 XML의 ID 값이다.
        @Bean
        public Exam exam(){
            return new NewlecExam();
        }

    }
    ```
    ```java
    package spring.di;

    public class Program {

        public static void main(String[] args){   

            ApplicationContext context = new AnnotationConfigApplicationContext(NewlecDIConfig.class);
            // new ClassPathXmlApplicationContext("spring/di/setting.xml");         
            
            ExamConsole console = (ExamConsole) context.getBean("console");
            console.print();
        }

    }
    ```