> [강의 링크](https://www.youtube.com/watch?v=QrIp5zc6Bo4&list=PLq8wAnVUcTFUHYMzoV2RoFoY2HDTKru3T&index=4&ab_channel=%EB%89%B4%EB%A0%89%EC%B2%98)

# IoC (Inversion Of Control) 컨테이너

- 컨테이너의 용도
    - XML이나 Annotation에 입력된 내용대로 객체를 생성해서, 객체들을 담을 그릇 역할을 한다.
- 왜 **IoC** 컨테이너 인가
    - IoC 방식으로 객체를 생성/조립하기 때문이다.
    - ![image](https://user-images.githubusercontent.com/52440668/93283983-9917ef00-f80c-11ea-8f4b-822dc72bdd8c.png)
        - 일체형으로 객체를 생성하는 것과는 다르게, 조립형은 작은 객체에서 큰 객체 순으로 객체를 생성하게 된다. 이러한 방식을 IoC, Inversion Of Control, 혹은 제어의 역전이라고 부른다.