# 3) xml 파일을 이용한 설정
> [참고](https://www.edwith.org/boostcourse-web-be/lecture/58971/)
> [작성한 코드](https://github.com/96glory/boostcourse-web-be/tree/43edbcf151592b6a8450e9751c41b6ab90fd507e)

## maven 프로젝트에서 JDK를 사용하기 위한 플러그인
```xml
  <build>
     <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.6.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>
    </plugins>
  </build>
```

## Bean Class란?
* 요즘에는 일반적인 Java 클래스를 Bean 클래스라고 보통 말한다.
* 특징
  * 기본 생성자를 가진다.
  * 필드는 private 하게 선언
  * getter setter 메소드를 가진다. (name property)
* 예시
```java
public class UserBean {
	
	//필드는 private한다.
	private String name;
	private int age;
	private boolean male;
	
	//기본생성자를 반드시 가지고 있어야 한다.
	public UserBean() {
	}
	
	public UserBean(String name, int age, boolean male) {
		this.name = name;
		this.age = age;
		this.male = male;
	}

	// setter, getter메소드
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

}
```

## spring-context 의존성 추가
```xml
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-context</artifactId>
		<version>${spring.version}</version>
	</dependency>
```
## resource 폴더 추가, applicationContext.xml 추가
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

	<bean id="userBean" class="kr.or.connect.UserBean"></bean>

</beans>
```

## DI 실습
