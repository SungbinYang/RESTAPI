## REST API

- API
  - Application Programming Interface
- REST
  - REpresentational State Transfer
  - 인터넷 상의 시스템 간의 상호 운용성(interoperability)을 제공하는 방법중 하나
  - 시스템 제각각의 독립적인 진화를 보장하기 위한 방법
  - REST API: REST 아키텍처 스타일을 따르는 API
- REST 아키텍처 스타일 ([발표 영상](https://www.youtube.com/watch?v=RP_f5dMoHFc) 11분)
  - Client-Server
  - Stateless
  - Cache
  - Uniform Interface
  - Layered System
  - Code-On-Demand (optional)
- Uniform Interface (발표 영상 11분 40초)
  - Identification of resources
  - manipulation of resources through represenations
  - **self-descrive messages**
  - **hypermedia as the engine of appliaction state (HATEOAS)**
- 두 문제를 좀 더 자세히 살펴보자. (발표 영상 37분 50초)
  - Self-descriptive message
    - 메시지 스스로 메시지에 대한 설명이 가능해야 한다.
    - 서버가 변해서 메시지가 변해도 클라이언트는 그 메시지를 보고 해석이 가능하다.
    - 확장 가능한 커뮤니케이션
  - HATEOAS
    - 하이퍼미디어(링크)를 통해 애플리케이션 상태 변화가 가능해야 한다.
    - 링크 정보를 동적으로 바꿀 수 있다. (Versioning 할 필요 없이!)
- Self-descriptive message 해결 방법
  - 방법 1: 미디어 타입을 정의하고 IANA에 등록하고 그 미디어 타입을 리소스 리턴할 때 Content-Type으로 사용한다.
  - 방법 2: profile 링크 헤더를 추가한다. (발표 영상 41분 50초)
    - [브라우저들이 아직 스팩 지원을 잘 안해](http://test.greenbytes.de/tech/tc/httplink/)
    - 대안으로 [HAL](https://stateless.group/hal_specification.html)의 링크 데이터에 [profile](https://datatracker.ietf.org/doc/html/draft-wilde-profile-link-04) 링크 추가
- HATEOAS 해결 방법
  - 방법1: 데이터에 링크 제공
    - 링크를 어떻게 정의할 것인가? HAL
  - 방법2: 링크 헤더나 Location을 제공

## “Event” REST API

- 이벤트 등록, 조회 및 수정 API
- GET /api/events
- 이벤트 목록 조회 REST API (로그인 안 한 상태)
  - 응답에 보여줘야 할 데이터
    - 이벤트 목록
    - 링크
      - self
      - profile: 이벤트 목록 조회 API 문서로 링크
      - get-an-event: 이벤트 하나 조회하는 API 링크
      - next: 다음 페이지 (optional)
      - prev: 이전 페이지 (optional)
  - 문서?
    - 스프링 REST Docs로 만들 예정
- 이벤트 목록 조회 REST API (로그인 한 상태)
  - 응답에 보여줘야 할 데이터
    - 이벤트 목록
    - 링크
      - self
      - profile: 이벤트 목록 조회 API 문서로 링크
      - get-an-event: 이벤트 하나 조회하는 API 링크
      - create-new-event: 이벤트를 생성할 수 있는 API 링크
      - next: 다음 페이지 (optional)
      - prev: 이전 페이지 (optional)
  - 로그인 한 상태???? (stateless라며..)
    - 아니, 사실은 Bearer 헤더에 유효한 AccessToken이 들어있는 경우!
- POST /api/events
  - 이벤트 생성
- GET /api/events/{id}
  - 이벤트 하나 조회
- PUT /api/events/{id}
  - 이벤트 수정

## Events API 사용 예제

- (토큰 없이) 이벤트 목록 조회
  - create 안 보임
- access token 발급 받기 (A 사용자 로그인)
- (유효한 A 토큰 가지고) 이벤트 목록 조회
  - create event 보임
- (유효한 A 토큰 가지고) 이벤트 만들기
- (토큰 없이) 이벤트 조회
  - update 링크 안 보임
- (유효한 A 토큰 가지고) 이벤트 조회
  - update 링크 보임
- access token 발급 받기 (B 사용자 로그인)
- (유효한 B 토큰 가지고) 이벤트 조회
  - update 안 보임
- REST API 테스트 클라이언트 애플리케이션

  - 크롬 플러그인

    - Talend API Tester

    ![](./img01.png)

  - 애플리케이션

    - Postman

    ![](./img02.png)

## 스프링 부트 프로젝트 만들기
- 추가할 의존성
  * Web
  * JPA
  * HATEOAS
  * REST Docs
  * H2
  * PostgreSQL
  * Lombok
- 자바 버전 11로 시작
  * [자바는 여전히 무료다.](https://medium.com/@javachampions/java-is-still-free-c02aef8c9e04)
- 스프링 부트 핵심 원리
  * 의존성 설정 (pom.xml)
  * 자동 설정 (@EnableAutoConfiguration)
  * 내장 웹 서버 (의존성과 자동 설정의 일부)
  * 독립적으로 실행 가능한 JAR (pom.xml의 플러그인)