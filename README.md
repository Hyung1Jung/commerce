# commerce
MSA, DDD로 설계하는 commerce 서비스입니다.

본 프로젝트는 완성 기한이 정해져있지 않습니다. 꾸준히 달성해가는 성과들을 바탕으로 더 큰 성장과 서비스의 프로젝트를 개선하기 위한 시도를 할 것입니다.

[각 기능들의 자세한 내용은 이곳을 참고해주시면 감사하겠습니다.](https://github.com/Hyung1Jung/commerce/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%A4%91%EC%A0%90-%EC%82%AC%ED%95%AD)

# Event Storming
![event storming](https://user-images.githubusercontent.com/43127088/152793363-c5322930-7f6c-45a1-bd27-8b5699050f46.png)

# Archiecture

### MSA Archiecture
![Untitled Diagram drawio (1)](https://user-images.githubusercontent.com/43127088/154705526-a2415783-d94e-40ca-b724-7612c476deb6.png)

1. user에서 회원가입을 진행합니다.
2. 한번 로그인 하면 여러 도메인에서 더 이상의 로그인 없이 이용할 수 있도록 sso 방식으로, auth를 따로 두어 그 곳에서 인증을 하고, 인증에 성공하면 로그인을 진행합니다.
3. 로그인에 성공하면, api gateway에서 토큰을 받아 사용자 정보를 추출하여 header에 삽입합니다.
4. api gateway에서 만들어진 토큰을 이용해서 product, order의 인가 작업을 진행하고 신뢰할 수 있는 토큰이면 인가에 성공합니다.
5. 인가에 성공하면, api gateway header에 있는 사용자 정보를 가져와 상품 생성 및 주문 등을 수행합니다.
6. 카프카의 토픽과 메세지 큐를 이용해 non-blocking 방식으로 product와 order간에 주문 작업을 진행합니다.  

- 모든 작업은 api gateway를 통해 이루어 집니다.
- 각각의 서버 모두 독립된 서비스로 나누었기 떄문에, 각 서비스는 독립된 배포 및 분산되고, 자율적으로 개발되고, 크기가 작고, 기능 중심적이고, 자동화된 프로세스로 구축되고 배포됩니다. 
- 또한 각각의 MicroService에서 발생하는 장애가 전체 시스템 장애로 확장되지 않습니다.

데이터 플레인
- 트래픽을 컨트롤 하는 목적으로 제공하는 영역이고, 데이터 플래인은 Envoy Proxy 를 사용합니다.

Envoy proxy
- L4, L7 지원, 서버간의 부하 분산량 조절가능합니다.
- 로드 벨런싱 등 액세스 제어 및 속도제한, 할당량을 지원합니다.
- Zipkin을 통한 분산트랜젝션 추적 기능 제공합니다.
- 풍부한 라우팅 규칙, 재시도, 장애 조치등으로 트래픽 동작 제어, 서킷 브래이커등을 제공합니다.
- http 등 tcp 트래픽에 대한 자동 로드 벨런싱 제공합니다.

컨트롤 플레인
- 데이터 영역으로 어떤 방식으로 어떠한 트래픽으로 흐르도록 제어하는 영역입니다.
- 데이터 영역에 대한 컨피그 값을 저장합니다.

Gateway
- standalone envoy proxy 형태로 istio-ingress-gateway에서 직접적으로 트래픽을 받으며 서비스 메시에 대한 inbound, outbound 트래픽을 관리합니다.

Virtual Service
- 쿠버네티스 서비스로 라우팅 되게 구성하는 이스티오 오브젝트입니다. 버추얼 서비스가 없는 경우, envoy proxy 는 모든 서비스 인스턴스 간에 라운드로빈 로드 밸런싱을 사용해 트래픽을 분산시킵니다.

### DDD -> Hexagonal Architecture(육각형 아키텍쳐)의 구성 
![Hexagonal-Simplified](https://user-images.githubusercontent.com/43127088/148683425-420094f0-b965-4571-b3e3-44513111bcef.png)

핵사고날 아키텍쳐 장점
1. 아키텍처 확장이 용이합니다.
2. SOLID 원칙을 쉽게 적용할 수 있습니다.
3. 모듈 일부를 배포하는 게 용이합니다.
4. 테스트를 위해 모듈을 가짜로 바꿀 수 있으므로 테스트가 더 안정적이고 쉽습니다.
5. 더 큰 비즈니스적 가치를 갖고 더 오래 지속되는 도메인 모델에 큰 관심을 둡니다.

헥사고날 아키텍처는 내부(도메인)와 외부(인프라)로 구분됩니다.

- 내부 영역 - 순수한 비즈니스 로직을 표현하며 캡슐화된 영역이고 기능적 요구사항에 따라 먼저 설계
- 외부 영역 - 내부 영역에서 기술을 분리하여 구성한 영역이고 내부 영역 설계 이후 설계

포트와 어댑터 - 포트는 내부 비즈니스 영역을 외부 영역에 노출한 API이고 인바운드(Inbound)/아웃바운드(Outbound) 포트로 구분됩니다.

- 인바운드 포트 - 내부 영역 사용을 위해 노출된 API
- 아웃바운드 포트 - 내부 영역이 외부 영역을 사용하기 위한 API

어댑터는 외부 세계와 포트 간 교환을 조정하고 역시 인바운드(Inbound)/아웃바운드(Outbound) 어댑터로 구분됩니다.

- 인바운드 어댑터 - 외부 애플리케이션/서비스와 내부 비즈니스 영역(인바운드 포트) 간 데이터 교환을 조정
- 아웃바운드 어댑터 - 내부 비즈니스 영역(아웃바운드 포트)과 외부 애플리케이션/서비스 간 데이터 교환을 조정

결국 이 구조의 핵심은 비즈니스 로직이 표현 로직이나 데이터 접근 로직에 의존하지 않는 것입니다.

### use tech 
- springboot, kotlin, kotest, docker, mysql, flyway DB, redis, spring security, kafka, zookeeper,  github action CI, Kotest(BDD)

