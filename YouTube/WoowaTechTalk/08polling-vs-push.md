# Web Polling vs Web Push

> [강의 링크](https://www.youtube.com/watch?v=v11dxmc5a0I&ab_channel=%EC%9A%B0%EC%95%84%ED%95%9CTech)

### Page

- 정적 웹 페이지 : 미리 저장된 파일이 그대로 전달
- 동적 웹 페이지 : 사용자의 요청을 해석해서 동적으로 생성된 파일을 전달
- **실시간 웹 (Real Time Web) : 페이지의 현재 상태를 방해하지 않고 서버와 통신**

### Web Polling vs Web Push

- 실시간 웹을 만들기 위한 기술

#### Web Polling

- 기존 요청/응답에서 푸시처럼 보이게 만든 기술
- Polling
    - 클라이언트가 주기적으로 서버로 요청해서 정보를 전달받는 방식
    - 실시간 메시지 전달이 크게 중요하지 않은 서비스
    - 불필요한 요청/응답이 많이 발생할 수 있음
    - ![그림1](https://user-images.githubusercontent.com/52440668/93280094-c4e2a700-f803-11ea-8da3-168c95ba6f6b.png)
- Long Polling
    - 클라이언트가 보낸 요청을 서버가 가지고 있다가 이벤트가 발생하면 정보를 전달받는 방식
    - 실시간 메시지 전달이 중요한 서비스
    - polling에 비해 불필요한 요청/응답이 덜 발생함
    - ![그림2](https://user-images.githubusercontent.com/52440668/93280304-45a1a300-f804-11ea-9abd-0e0fae84281c.png)
        - 대기하는 시간은 서버에서 어떻게 구현하느냐에 따라 다르다

#### Web Push

- 요청 없이 서버에서 클라이언트로 정보를 전달
- Web Socket
    - TCP 연결을 통해 양방향 데이터 통신을 제공
    - ![그림3](https://t1.daumcdn.net/cfile/tistory/990F3D395B745BD624)
        - 요청 시 Upgrade 속성을 사용, HTTP에서 WSS(Web Socket Server)로의 전환
            ```
            GET /chat HTTP/1.1
            Host: example.com:8000
            Upgrade: websocket
            Connection: Upgrade
            Sec-WebSocket-Key: dGhlIHNhbXBsZSBub25jZQ==
            Sec-WebSocket-Version: 13
            ```


- SSE(Server Sent Events)
    - HTTP 연결을 통해 서버에서 클라이언트로 데이터를 수신 가능하게 함
    - 연결된 상태를 유지하고 서버가 일방적으로 데이터를 전송 (단방향)
    - 사용자는 메시지를 청취하는 과정이 필요
    - 자동 재 연결 (연결이 닫힐 때 브라우저가 0 ~ 3초 후 자동으로 소스에 다시 연결)
    - ![그림4](https://miro.medium.com/max/920/1*vX9rraBWqFoUP9yTDV0bWQ.png)