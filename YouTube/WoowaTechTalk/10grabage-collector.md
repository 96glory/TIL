# JVM의 Garbage Collector

> [강의 링크](https://www.youtube.com/watch?v=vZRmCbl871I&ab_channel=%EC%9A%B0%EC%95%84%ED%95%9CTech)

- JVM(Java Virtual Machine)
    - OS의 메모리 영역에 접근하여 메모리를 관리하는 프로그램
    - 메모리 관리, Garbage Collector 수행
- JAVA의 메모리 영역
    - Stack
        - 정적으로 할당한 메모리 영역
        - 원시 타입의 데이터가 값과 함께 할당. Heap 영역에 생성된 Object 타입의 데이터의 참조 값 할당.
    - Heap
        - 동적으로 할당한 메모리 영역
        - 모든 Object 타입의 데이터가 할당. Heap 영역의 Object를 가리키는 참조 변수가 Stack에 할당.
    ```java
    public class Main {
        public static void main(String[] args){
            int num1 = 10;  // stack
            int num2 = 5;   // stack
            int sum = num1 + num2; // stack
            String name = "abc"; // Heap 참조 주소는 Stack에, 실제 값은 Heap에 할당

            System.out.println(sum);
            System.out.println(name);
        }
    }
    ```

- GC (Garbage Collector)
    - 동적으로 할당한 메모리 영역 중 사용하지 않는 영역을 탐지하여 해제하는 기능
- GC의 과정 (Mark & Sweep)
    1. GC가 Stack의 모든 변수를 스캔하면서 각각 어떤 객체를 참조하고 있는지 찾아서 마킹한다. (Mark)
    2. Reachable Object가 참조하고 있는 객체도 찾아서 마킹한다. (Mark)
    3. 마킹되지 않은 객체를 Heap에서 제거한다. (Sweep)
- HEAP의 구조
    - ![그림1](https://user-images.githubusercontent.com/52440668/93542894-30a84980-f995-11ea-962c-31d6b4d37c16.png)
- GC가 발생하는 시기
    - 새로운 객체는 `Eden`에 할당된다.
    - `Eden`이 가득 차면, GC 발생 (Minor GC)
    - `Eden`에 Minor GC 발생하여 Mark & Sweep이 끝난 뒤, Reachable 객체는 `Survival 0`으로 옮겨진다. Unrechable 객체는 메모리에서 해제됨
    - Minor GC가 계속 반복되서 `Survival 0`이 가득 차면, `Survival 0`에서 Minor GC가 발생하고, 거기서 살아남은 객체는 `Survival 1`으로 옮겨진다. **이동한 객체는 Age 값이 증가된다.**
    - `Survival 1`이 가득 차면, `Survival 1`에서 Minor GC가 발생하고, 거기서 살아남은 객체는 `Survival 0`으로 옮겨진다. **이동한 객체는 Age 값이 증가된다.**
    - 계속 반복하다가 Age가 특정 값 이상이 되면 `Old Generation`으로 옮겨진다. **이 과정을 Promotion이라고 한다.**
    - `Old Generation`이 가득 차면 Major GC가 발생한다.