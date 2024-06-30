# MySQL master/slave 환경 구축

## 바이너리 로그 기반 복제(Binary Log)

### Binary log란?

MySQL 서버에서 **Create, Alter, Drop**과 같은 작업을 수행하면 MySQL은 그 변화된 **이벤트를 기록**하는데 변경사항들에 대한 정보를 담고 있는 **이진 파일을 바이너리 로그 파일** 이라고 한다.

이때 show나 select와 같은 조회 문법은 제외 된다.

### Replication 동작원리

MySQL의 Replication은 기본적으로 **비동기 복제 방식**을 사용하고 있다. Master 노드에서 변경되는 데이터에 대한 이력을 로그에 기록하면 Replication Master Thread가 비동기적으로 이를 읽어서 Slave쪽으로 전송한다.

<img width="450" alt="db" src="https://github.com/EZ-card/EZ-Card/assets/139452702/48be7476-4ea6-4de9-b58f-012dc5d106b8">

1. Commit 발생
2. Connection Thread에서 스토리지 엔진에게 해당 트랜잭션에 대한 Prepare(Commit 준비)를 수행
3. Commit을 수행하기 전에 먼저 `Binary Log`에 변경사항 기록
4. 스토리지 엔진에게 트랜잭션 Commit을 수행하도록 함
5. `Master Thread`는 **비동기적으로 `Binary Log`를 읽어서 Slave로 전송**
6. Slave의 **`I/O Thread`**는 Master로부터 수신한 변경 데이터를 읽어서 `Relay Log`에 기록
7. Slave의 **`SQL Thread`는 Relay Log**에 기록된 변경 데이터를 읽어서 **Slave의 스토리지 엔진에 적용**

## Replication의 장점

### Select 성능 향상

- read 작업은 자원은 많이 소비핸다. Replication을 구성하면 N개의 Slave를 가질 수 있기 때문에 Read에 대한 부하가 그만큼 분산된다.

### 데이터 백업

- Master의 내용을 복제하기 때문에 Master에 문제가 생겨 데이터가 날아가도 Slave중 하나를 Master로 승격시켜 사용할 수 있다.

## Replication의 단점

### 데이터의 정합성을 보장할 수 없음

- Slave는 Master의 복사본을 사용하기 때문에 그것이 정말 완벽하다고 할 수 없다. Slave가 Master의 쿼리 처리량을 따라가지 못하면 정합성에 차이가 생길 수 있다.

### Binary Log File 관리

- Master는 Binary Log가 무분별하게 쌓이는 것을 막기위해 데이터 보관 주기를 설정하지만 Maste는 Slave까지 관리하지 않기 때문에 Master에서 Binary Log File을 삭제 했다고 Slave의 Binary Log를 삭제하지 못한다.

### Fail Over 불가

- Master에서 Error가 발생 했을 경우 Slave로 Failover 하는 기능을 지원하지 않는다. Slave역시 Master와 Log 위치가 다르다면 관리자가 작업을 해아한다.
