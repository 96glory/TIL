> [강의 링크](https://www.youtube.com/watch?v=WjsDN_aFfyw&list=PLq8wAnVUcTFUHYMzoV2RoFoY2HDTKru3T&index=3&ab_channel=%EB%89%B4%EB%A0%89%EC%B2%98)

- 스프링 프레임워크 코어 기능 : **종속 객체를 생성 및 조립해주는 도구**
    - DI (Dependency Injection)
    - IoC Container

# DI (Dependency Injection)

- 종속성 주입? 의존성 주입? X  → **부품 조립**
- Dependency들을 Injection하기
    - 일체형 : Composition : HAS-A
        ```java
        class A{
            private B b;

            // 생성자에서 B를 생성해준다.
            public A(){
                b = new B();
            }
        }
        ```
        ```java
        A a = new A();
        ```
    - 조립형 : Association : HAS-A
        ```java
        class A {
            private B b;

            public A(){

            }

            // 생성자가 B를 생성하지 않고 외부에서 생성 후 메서드를 통해서 주입해준다.
            public void setB(B b){
                this.b = b;
            }
        }
        ```
        ```java
        // setter를 통한 주입 : Setter Injection
        B b = new B();  // dependency 생성
        A a = new A();

        a.setB(b);  // dependency injection
        ```
        ``` java
        // 생성자를 통한 주입 : Construction Injection
        B b = new B();  // dependency 생성
        A a = new A(b); // dependency injection
        ```
- DI의 장점
    - 의존성을 다른 것으로 쉽게 바꿀 수 있다.
- DI의 단점
    - 의존성을 주입해야 하는 코드가 발생한다.
    - **but, spring이 의존성 주입을 대신 해준다!!** 단점 극복.