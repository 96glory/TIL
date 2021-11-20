# cache와 실제 사용 사례

### 기본 이론

- 메모리 계층 구조
    - ![image](https://user-images.githubusercontent.com/52440668/93404948-97aafd00-f8c6-11ea-8dfa-9000b85e5453.png)
    - 데이터를 저장하는 공간의 속도와 용량은 반비례 관계
        - 속도가 빠른 메모리일수록 용량이 작음
        - 용량이 큰 저장장치는 속도가 느림
        - 둘 다 잡기에는 비용이 너무 많이 든다
        - 그래서 데이터 저장 공간은 속도와 용량에 따라 역할을 나누어서 사용한다.
- 파레토의 법칙
    - ![pareto](https://lh3.googleusercontent.com/proxy/kG-zamHO1Gca5SJWzPW5gmI-Sq0JcgVCZ6RRHeBIUcUPAI1dhUE3NrX0mGfZZl-JcBwki8RtF6HwExITqChjxBI6nXcZ8K8xwKfj4rhi1C_RB_Pfq44W4qh49d1VyEIyFjPxuaNK86MHrRMf2HDBD5DwZXjBPFA0rZoR2g)
    - 원인 중 상위 20%가 전체 결과의 80%를 만든다는 법칙
- 데이터 지역성의 원리
    - 자주 쓰이는 데이터는 시간적 혹은 공간적으로 한 곳에 몰려 있을 가능성이 높다
    - 시간 지역성 : for문에서 사용하는 조건 변수
    - 공간 지역성 : for문에서 탐색하기로 한 배열

### cache

- 캐시의 정의
    - 나중에 필요할 수도 있는 무언가를 저장하였다가 신속하게 회수할 수 있는 보관 장소로, 어떤 식으로든 보호되거나 숨겨진다.
- 캐시의 작동 방식
    - 원본 데이터 `System-of-Record` 와는 별개로 자주 쓰이는 데이터들 `Hot Data` 을 복사해둘 캐시 공간을 마련한다. 캐시 공간은 낮은 시간 복잡도로 접근 가능한 곳을 주로 사용한다.
    - 데이터를 달라는 요청이 들어오면, 원본 데이터가 담긴 곳에 접근하기 전에 먼저 캐시 내부부터 찾는다.
    - 캐시에 원하는 데이터가 없거나 `Cache miss`, 너무 오래되어 최신성을 잃었다면 `Expiration`, 원본 데이터가 있는 곳에 접근하여 데이터를 가져온다. 이때 데이터를 가져오면서 캐시에도 해당 데이터를 복사하거나 갱신한다.
    - 캐시에 원하는 데이터가 있으면 원본 데이터가 있는 공간에 접근하지 않고 캐시에서 바로 해당 데이터를 제공한다. `Cache Hit`
    - 캐시 공간은 작으므로, 공간이 모자라게 되면 안 쓰는 데이터부터 삭제하여 공간을 확보한다. `Eviction`

### cache의 사례

- CDN (Content Delivery Network)
    - YouTube의 메인 서버는 미국에 있다. 한국과 미국을 잇는 국제 인터넷 회선은 비싸고 용량을 늘리기도 어렵다.
    - 구글은 각 통신사마다 Google Global Cache를 두어 인기 있는 YouTube 동영상은 미국 서버까지 접속할 필요 없이 국내 서버에서 처리하도록 하였다.
    - 비싼 국제 회선 비용이 절감되고, 버퍼링이 줄어 고화질 서비스 제공이 개선되었다.
    - **이처럼 세계 각지에 캐시 서버를 두어 전송 속도를 높이고, 부하를 분산하는 시스템이 CDN이다.**
- 웹 캐시
    - 네트워크를 통해 데이터를 가져오는 것은 하드디스크보다 느릴 수 있다.
    - 그래서 웹 브라우저는 웹 페이지를 접속할 때, HTML, CSS, JS, image 등을 하드디스크나 메모리에 캐싱해 두었다가 다음 번에 그 웹 페이지를 접속할 때 재활용한다. (**브라우저 캐시**)
    - 웹 서버 또한 상당수의 경우 동적 웹 페이지라 할지라도 매번 내용이 바뀌지 않는 경우가 많다. 서버에서 생성한 HTML를 캐싱해 두었다가 다음 번 요청에 재활용한다. (**응답 캐시**)
    - 클라이언트에서 자주 요청받는 내용은 웹 서버로 전달하지 않고 웹 서버 앞단의 **프록시 서버에서 캐싱해둔 데이터** 를 바로 제공하기도 한다. (**프록시 캐시**)
    - [참고 : 슈퍼마켓에서 우유를 사면서 웹 캐싱을 알아봅시다](https://rinae.dev/posts/web-caching-explained-by-buying-milk-kr)
    - 브라우저 캐시
        - 웹 서버에서 클라이언트에 보내는 HTTP 헤더에 캐시 지시자를 삽입하면, 클라이언트 웹 브라우저에서는 해당 지시자에 명시된 캐시 정책에 따라 캐싱을 실시한다.
        - 캐시의 유효 시간 `max-age` 이 지나도 캐시된 데이터가 바뀌지 않은 경우를 확인하기 위해 ETag라는 유효성 검사 토큰을 사용한다.
        - 때로는 캐시 유효 시간을 최대한 길게 잡으면서도 정적 파일의 업데이트를 신속히 적용하기 위해 **정적 파일의 이름 뒤에 별도의 토큰이나 버전 번호를 붙여야 하는 경우**도 있다.
        - 캐시 정책은 해당 웹 페이지의 전반적인 상황에 따라 각 파일마다 다르게 적용되어야 한다. 적어도 **정적 파일과 동적인 부분의 브라우저 캐싱 정책은 달라야 한다. 비공개 정보가 담긴 페이지는 보안상 아예 캐싱을 막아야 할 수도 있다.**
- DB에서의 캐싱 : Redis (Remote Dictionary Server)
    - 메모리 기반 오픈소스 NoSQL DBMS의 일종으로, 웹 서비스에서 캐싱을 위해 많이 쓴다.
    - 기본적으로 모든 데이터를 메모리에 저장하여 처리하므로 속도가 빠르다.
    - Dictionary = `HashMap<Key, Value>`
    - 서버 재부팅 때 메모리의 데이터가 휘발되지 않게끔 데이터를 하드디스크에 기록할 수 있다.
    - DBMS의 일종이므로, 명시적으로 삭제하지 않는 한 메모리에서 데이터를 삭제하지 않는다.
    - 자체적으로 여러가지 자료형을 지원한다.
- 자바에서의 캐싱 : EHcache
    - Java의 표준 캐싱 API 명세인 JSR-107을 따르는 오픈소스 캐시 구현체
    - Spring Framework나 Hibernate ORM 등에서 바로 사용 가능
    - 캐시 저장공간을 속도에 따라 여러 Tier로 나누어 메모리 계층 구조를 적용 가능
    - 메모리에 캐시된 내용을 하드디스크에 기록 가능
    - 대규모 서비스에서 캐시 서버 여럿을 클러스터로 묶을 수 있는 기능을 제공