# AOP (Aspect Oriented Programming)

## AOP 이해하기

> [강의 링크](https://youtu.be/y2JkXjOocZ4)

- ![image](https://user-images.githubusercontent.com/52440668/94505681-9672bc00-0246-11eb-949c-28c1e40bc735.png)
    - 여러 관점에서 비즈니스 로직은 달라질 수 있다.
    - 사용자는 주 업무 로직만 사용하지만, 개발자나 운영자는 더 큰 로직을 요할 수 있기 때문.
    - 개발자나 운영자의 로직은 로그 처리, 보안 처리, 트랜잭션 처리 등을 예시로 들 수 있다. 위 업무들을 주 업무 로직 앞/뒤로 끼어들게 된다.
- ![image](https://user-images.githubusercontent.com/52440668/94506052-755e9b00-0247-11eb-8181-5619b1088499.png)
    - **Cross-cutting Concern은 Proxy 기반으로 이루어진다.**

## AOP 구현방식 이해하기

### Spring을 사용하지 않고 AOP 구현하기

> [강의 링크](https://youtu.be/DhcrnaKKrbA)

- `total()` 함수에서 시간이 얼마나 걸리는 지 파악하는 코드 추가하고 싶다!

    ```java
    public class NewlecExam implements Exam {

        // 생략

        public int total(){

            long start = System.currentTimeMillis();
            SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String str = dayTime.format(new Date(start));
            System.out.println(str);

            // 기존 코드
            int result = kor + eng + math + com;

            long end = System.currentTimeMillis();

            String message = (end - start) + "ms is passed";
            System.out.println(message);

            // 기존 코드
            return result;
        }

        // 생략
    }
    ```
- 주 업무 로직을 분리한다.
    - Core Concern
        ```java
        public class NewlecExam implements Exam {

            // 생략

            public int total(){
                int result = kor + eng + math + com;
                return result;
            }

            // 생략
        }
        ```
    - Cross-cutting Concern
        ```java
        public static void main(String [] args){
            Exam exam = new NewlecCalculator();
            Exam examProxy = (Exam) Proxy.newProxyInstance(
                NewlecExam.class.getClassLoader(),
                new Class[]{Exam.class},
                new InvocationHandler(){
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        long start = System.currentTimeMillis();
                        SimpleDateFormat dayTime = new (SimpleDateFormat"yyyy-mm-dd hh:mm:ss");
                        String str = dayTime.format(new Date(start));
                        System.out.println(str);
                        
                        Object result = method.invoke(exam, args);

                        long end = System.currentTimeMillis();
                        String message = (end - start) + "ms is passed";
                        System.out.println(message);

                        return result;
                    }
            })

            int result = examProxy.total();
            System.out.println("total is " + result);
        }

### 순수 자바로 AOP 구현해보기 

> [강의 링크](https://bit.ly/34oAgok)

```java
package spring.aop;

public class Program {

    public static void main(String[] args){
        
        Exam exam = new NewlecExam(1, 1, 1, 1);

        Exam proxy = (Exam) Proxy.newProxyInstance(
            NewlecExam.class.getClassLoader(), 
            new Class[] {Exam.class},
            new InvocationHandler() {

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    long start = System.currentTimeMillis();

                    Object result = method.invoke(exam, args);
                    
                    long end = System.currentTimeMillis();

                    String message = (end - start) + "ms 시간이 걸렸습니다.";
                    System.out.println(messsage);

                    return result;
                }

            }
        );

        System.out.printf("total is %d", exam.total());
        System.out.printf("avg is %f", exam.avg());

    }

}

```

```java
package spring.aop.entity;

public interface Exam{
    int total();
    float avg();
}
```

```java
package spring.aop.entity;

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
        int result =  kor + eng + math + com;

        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public float avg() {

        float result = total() / 4.0f

        return result;
    }

    // getter, setter 생략
}
```

## 스프링 AOP

- Advice
    - Before : 앞에만 필요한 곁다리 업무
    - After returnning : 뒤에만 필요한 곁다리 업무
    - After throwing : 본 업무를 처리하는 곁다리 업무
    - Around : 앞뒤로 필요한 곁다리 업무
- 스프링 AOP를 사용하기 위해서 프로젝트에 스프링 설정을 해준다.

### AroundAdvice 구현하기

> [강의 링크](https://bit.ly/33yVqkk)

```xml
<!-- setting.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xmlns:util="http://www.springframe.org/schema/util"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="target" class="spring.aop.entity.NewlecExam" p:kor="1" p:eng="1" p:math="1" p:com="1"/>
    <bean id="logAroundAdvice" class="spring.aop.advice.LogAroundAdvice" />
    <bean id="proxy" class="org.springframework.aop.framework.ProxyFactoryBean">
        <property name="target" ref="target" />
        <property name="interceptorNames">
            <list>
                <value>logAroundAdvice</value>
            </list>
        </property>
    </bean>
</beans>
```

```java
package spring.aop.advice;

import org.aopalliance.intercept.MethodInterceptor;

public class LogAroundAdvice implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        long start = System.currentTimeMillis();

        Object result = invocation.proceed();

        long end = System.currentTimeMillis();
        String message = (end - start) + "ms 시간이 걸렸습니다.";
        System.out.println(messsage);

        return result;
    }

}
```

```java
package spring.aop;

public class Program {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("spring/aop/setting.xml");
        // new AnnotationConfigApplicationContext(NewlecDIConfig.class);
        
        Exam proxy = (Exam) context.getBean("proxy");

        System.out.printf("total is %d", proxy.total());
        System.out.printf("avg is %f", proxy.avg());
    }

}
```

### BeforeAdvice 구현하기

> [강의 링크](https://bit.ly/34CN2Qi)

```xml
<!-- setting.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xmlns:util="http://www.springframe.org/schema/util"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="target" class="spring.aop.entity.NewlecExam" p:kor="1" p:eng="1" p:math="1" p:com="1"/>
    <bean id="logAroundAdvice" class="spring.aop.advice.LogAroundAdvice" />
    <bean id="logBeforeAdvice" class="spring.aop.advice.LogBeforeAdvice" />

    <bean id="proxy" class="org.springframework.aop.framework.ProxyFactoryBean">
        <property name="target" ref="target" />
        <property name="interceptorNames">
            <list>
                <value>logAroundAdvice</value>
                <value>logBeforeAdvice</value>
            </list>
        </property>
    </bean>
</beans>
```

```java
package spring.aop.advice;

public class LogBeforeAdvice implements MethodBeforeAdvice {

    @Override
    public Object before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("앞에서 실행될 로직");
    }

}
```

### After Returning & Throwing Advice

> [강의 링크](https://bit.ly/3iEgvOq)

```xml
<!-- setting.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xmlns:util="http://www.springframe.org/schema/util"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 일부러 오류 발생 -->
    <bean id="target" class="spring.aop.entity.NewlecExam" p:kor="101" p:eng="1" p:math="1" p:com="1"/>

    <bean id="logAroundAdvice" class="spring.aop.advice.LogAroundAdvice" />
    <bean id="logBeforeAdvice" class="spring.aop.advice.LogBeforeAdvice" />
    <bean id="logAfterReturningAdvice" class="spring.aop.advice.LogAfterReturningAdvice" />
    <bean id="logAfterThrowingAdvice" class="spring.aop.advice.LogAfterThrowingAdvice" />

    <bean id="proxy" class="org.springframework.aop.framework.ProxyFactoryBean">
        <property name="target" ref="target" />
        <property name="interceptorNames">
            <list>
                <value>logAroundAdvice</value>
                <value>logBeforeAdvice</value>
                <value>logAfterReturningAdvice</value>
                <value>logAfterThrowingAdvice</value>
            </list>
        </property>
    </bean>
</beans>
```

```java
package spring.aop.advice;

public class LogAfterReturningAdvice implements AfterReturningAdvice {

    @Override
    public Object afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("returnValue : " + returnValue + ", method : " + method.getName());
    }

}
```

```java
package spring.aop.advice;

public class LogAfterThrowingAdvice implements ThrowsAdvice {

    // 어떤 예외를 처리할 것인가에 따라 파라미터가 달라진다.
    public void afterThrowing(IllegalArgumentException e) throws Throwable {
        System.out.println("예외 발생 : " + e.getMessage());
    }

}
```

```java
public class NewlecExam implements Exam {

    // 생략

    @Override
    public int total() {
        int result =  kor + eng + math + com;

        if(kor > 100)
            throw new IllegalArgumentException("유효하지 않은 국어점수");

        try{
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public float avg() {

        float result = total() / 4.0f

        return result;
    }
}
```

### PointCut 설정하는 방법

#### 전통적인 방법

> [강의 링크](https://bit.ly/3noiz0B)

```xml
<!-- setting.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xmlns:util="http://www.springframe.org/schema/util"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="target" class="spring.aop.entity.NewlecExam" p:kor="101" p:eng="1" p:math="1" p:com="1"/>

    <bean id="logAroundAdvice" class="spring.aop.advice.LogAroundAdvice" />
    <bean id="logBeforeAdvice" class="spring.aop.advice.LogBeforeAdvice" />
    <bean id="logAfterReturningAdvice" class="spring.aop.advice.LogAfterReturningAdvice" />
    <bean id="logAfterThrowingAdvice" class="spring.aop.advice.LogAfterThrowingAdvice" />

    <!-- total 함수만 before에 위빙이 됨 -->

    <bean id="classicPointCut" class="org.springframework.aop.support.NameMatchMethodPointCut">
        <property name="mappedName" value="total" />
    </bean>

    <bean id="classicBeforeAdvisor" class="org.springframework.aop.support.DefaultPointCutAdvisor">
        <property name="advice" ref="LogBeforeAdvice" />
        <property name="pointcut" ref="classicPointCut" />
    </bean>

    <bean id="proxy" class="org.springframework.aop.framework.ProxyFactoryBean">
        <property name="target" ref="target" />
        <property name="interceptorNames">
            <list>
                <value>logAroundAdvice</value>
                <value>classicBeforeAdvisor</value>
                <value>logAfterReturningAdvice</value>
                <value>logAfterThrowingAdvice</value>
            </list>
        </property>
    </bean>
</beans>
```

#### 간소화된 Advisor

> [강의 링크](https://bit.ly/30GsAfV)

```xml
<!-- setting.xml -->
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:p="http://www.springframework.org/schema/p"
    xmlns:util="http://www.springframe.org/schema/util"
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="target" class="spring.aop.entity.NewlecExam" p:kor="101" p:eng="1" p:math="1" p:com="1"/>

    <bean id="logAroundAdvice" class="spring.aop.advice.LogAroundAdvice" />
    <bean id="logBeforeAdvice" class="spring.aop.advice.LogBeforeAdvice" />
    <bean id="logAfterReturningAdvice" class="spring.aop.advice.LogAfterReturningAdvice" />
    <bean id="logAfterThrowingAdvice" class="spring.aop.advice.LogAfterThrowingAdvice" />


    <!-- 기존 코드

    <bean id="classicPointCut" class="org.springframework.aop.support.NameMatchMethodPointCut">
        <property name="mappedName" value="total" />
    </bean>

    <bean id="classicBeforeAdvisor" class="org.springframework.aop.support.DefaultPointCutAdvisor">
        <property name="advice" ref="LogBeforeAdvice" />
        <property name="pointcut" ref="classicPointCut" />
    </bean>
    
    -->

    <bean id="newBeforeAdvisor" class="org.springframework.aop.support.NameMatchMethodPointCutAdvisor">
        <property name="advice" ref="LogBeforeAdvice" />
        <property name="mappedNames">
            <list>
                <value>total<value>
                <value>avg<value>
            </list>
        </property>
    </bean>

    <bean id="proxy" class="org.springframework.aop.framework.ProxyFactoryBean">
        <property name="target" ref="target" />
        <property name="interceptorNames">
            <list>
                <value>logAroundAdvice</value>
                <value>newBeforeAdvisor</value>
                <value>logAfterReturningAdvice</value>
                <value>logAfterThrowingAdvice</value>
            </list>
        </property>
    </bean>
</beans>
```