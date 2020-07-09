# 프로파일
> [작성한 코드](https://github.com/96glory/whiteship-spring-boot/tree/a0d9313f70135296feaf1f616b1538ef8976f065/externalconfig/src/main)
* 목적
  * 특정한 프로파일에서만 특정한 빈을 등록하고 싶다.
  * application 동작을 특정한 프로파일에서 조금 다르게 하고 싶다.
* 사용처
  * @Configuration
  * @Component
* 어떤 프로파일을 활성화할 것인가?
  * spring.profiles.active=___
* 어떤 프로파일을 추가할 것인가?
  * spring.profiles.include=___
* 프로파일용 프로퍼티
  * application-{profile}.properties
