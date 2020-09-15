# AOP - Advice, Target, Pointcut

> [강의 링크](https://www.youtube.com/watch?v=WQR_VQnz7Yg&ab_channel=%EC%9A%B0%EC%95%84%ED%95%9CTech)

### AOP

- AOP : 여러 오브젝트에 나타나는 공통적인 부가 기능을 모듈화하여 재사용하는 기법
    - Spring의 AOP는 프록시를 기반으로 한 AOP다.
- 예시 : 한 개, 혹은 여러 컨트롤러내 여러 API에 시간 측정 기능이 필요하다.
    - ![그림1](https://user-images.githubusercontent.com/52440668/93180215-cc597000-f771-11ea-99c0-26142fcdfec9.png)
    - 시간 측정을 한 번만 작성해서 가져다가 쓰면 된다.
- Advice : 어떤 부가기능을 언제 사용할까?
- Joinpoint, PointCut : 어디에 사용할까?

#### Advice

- 어떤 부가기능을 언제 사용할지에 대한 정의
- ![그림2](https://user-images.githubusercontent.com/52440668/93180887-b9936b00-f772-11ea-9bbe-b765868a178b.png)
- 위의 예시에서 비즈니스 로직을 감싸는 것이 시간 측정 기능이므로, 프록시 오브젝트를 감싸서 어떤 행위를 수행할 **Advice** 코드에 `@Around`를 선언해준다.

#### JoinPoint

- 어드바이스가 적용될 수 있는 위치
    - **메서드 호출할 때**
    - 변수에 접근할 때, 객체를 초기화할 때, 객체에 접근할 때
- 하지만, JoinPoint로 시간 측정 어드바이스를 적용하게 되면, 모든 컨트롤러 앞뒤로 시간 측정을 하게 된다. 내가 원하는 컨트롤러에만 시간측정 어드바이스를 붙을 수 있게 만들어진 것이 PointCut이다.

#### PointCut

- Advice를 적용할 JoinPoint를 선별하는 작업

#### Target

- 어드바이스가 적용될 대상
    - 위의 예시에서 `getArticle`과 `deleteArticle`이 Target이 된다.