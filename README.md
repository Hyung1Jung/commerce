# commerce

### Archiecture

- MSA Archiecture
![아키텍쳐 drawio](https://user-images.githubusercontent.com/43127088/148683531-e2f8c96e-2b63-47d1-b5e3-06d03bb4feaf.png)

1. user에서 회원가입을 진행합니다.
2. 한번 로그인 하면 여러 도메인에서 더 이상의 로그인 없이 이용할 수 있도록 SAML의 sso 방식처럼, auth 를 따로 두어 그 곳에서 인증을 하고, 인증에 성공하면 로그인을 진핼합니다.
3. 로그인에 성공하면, api gateway에서 토큰을 받아 사용자 정보를 추출하여 header에 삽입합니다.
4. api gateway에서 만들어진 토큰을 이용해서 product, order의 인가 작업을 진행하고 신뢰할 수 있는 토큰이면 인가에 성공합니다.
5. 인가에 성공하면, api gateway header에 있는 사용자 정보를 가져와 상품 생성 및 주문 등을 수행합니다.
6. 카프카의 토픽과 메세지 큐를 이용해 non-blocking 방식으로 product와 order간에 주문 작업을 진행합니다.  

- 모든 작업은 api gateway를 통해 이루어 집니다.
- 각각의 서버 모두 독립된 서비스로 나누었기 떄문에, 각 서비스는 독립된 배포 및 분산되고, 자율적으로 개발되고, 크기가 작고, 기능 중심적이고, 자동화된 프로세스로 구축되고 배포됩니다. 
- 또한 각각의 MicroService에서 발생하는 장애가 전체 시스템 장애로 확장되지 않습니다.

- DDD -> Hexagonal Architecture(육각형 아키텍쳐) 적용
![Hexagonal-Simplified](https://user-images.githubusercontent.com/43127088/148683425-420094f0-b965-4571-b3e3-44513111bcef.png)

### use tech 
- springboot, kotlin, docker, mysql, flyway DB, spring security, kafka, github action CI, Kotest(BDD)

