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