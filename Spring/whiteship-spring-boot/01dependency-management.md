# Spring Boot의 의존성 관리
* maven의 xml을 통해 root의 dependency 문서가 사용자의 xml 파일의 spring version을 보고 알맞은 version과 properties를 찾아서 주입해준다.
* spring의 version upgrade가 되어도 호환성 문제가 발생하지 않는다.
* spring dependency에서 관리하지 않는 외부 의존성은 버전을 명시해야 한다.
* 보통 pom.xml의 parent 태그를 사용한다.
