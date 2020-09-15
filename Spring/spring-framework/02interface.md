# 느슨한 결합력과 인터페이스

> [강의 링크](https://www.youtube.com/watch?v=KJ9Rus3QfUc&list=PLq8wAnVUcTFUHYMzoV2RoFoY2HDTKru3T&index=2&ab_channel=%EB%89%B4%EB%A0%89%EC%B2%98)

- ![image](https://user-images.githubusercontent.com/52440668/93223148-b5833f80-f7aa-11ea-9ed8-38e301fa6be4.png)
    - Service Layer : 사용자의 비즈니스에 맞는 서비스를 담당
    - Dao Layer : 데이터 소스를 접근하는 방식을 제공
- 위 이미지에서 Dao의 B1 코드를 수정하려면?
    - 새 B2 코드를 작성해서, B1에 덮어쓴다. 하지만, 이 방식은 Service의 S의 수정을 요할 수도 있다. (S와 B1의 결합력이 높다. 결합력을 낮추기 위해 interface를 사용한다.)
    - ![image2](https://user-images.githubusercontent.com/52440668/93223745-583bbe00-f7ab-11ea-8d45-a7d4cf0798c4.png)
- S에 B1을 생성할 지, B2를 생성할 지. 누가 이것을 결정하고 연결할 것인가.
    - ![image3](https://user-images.githubusercontent.com/52440668/93224003-9d5ff000-f7ab-11ea-97ab-5a09a26b9a17.png)