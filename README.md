# gPRC

## gPRC 의 등장 배경
### RESTful 서비스의 단점
분산 어플리케이션 환경에서 많이 사용되는 `RESTful` 서비스는 보통 `JSON` 과 같이 human readable 한 형식으로 서비스간 통신한다. 하지만 서비스 통신에서 굳이
사람이 읽을 필요가 없다. (텍스트 기반이어야 할 필요가 없기 때문에 비효율적이다.)

그 밖에도 `RESTful` 스타일 (일관성과 규칙을)을 유지하는데 드는 개발팀의 비용등에 의하여 새로운 메시지 프로토콜에 대한 요구사항이 등장하였다.

### gPRC 의 장점
- `gRPC` 는 텍스트 기반 형식 대신 바이너리 프로토콜 (프로토콜 버퍼 aka protobuf) 을 사용해 서비스간 통신을 하고 HTTP/2 기반이라 통신 속도가 빠르다.
- `RESTful` 과는 다르게 서비스 인터페이스를 먼저 정의하여 사용한다. (`RESTful` 은 swagger 나 OpenAPI 처럼 텍스트 포맷이 더 중요)
- 데이터 타입을 명확하게 정의할 수 있다.
- 서비스 개발 시 특정 언어에 구애 받지 않는다.
- 스트리밍을 기본적으로 지원한다.
- 유용한 기능들(인증, 암호화, resilience 등)을 지원한다.
- 큰 회사들이 잘 쓰고 잘 밀어주고 있다.

그러나 마찬가지로 단점도 있다.

### gPRC 의 단점
- 외부(서비스를 사용할 외부 제 3자)에 노출할만한 프로토콜은 아니다. (proto 를 작성하는 것 처럼 타입 속성을 강제하기 때문에 서비스의 유연성이 떨어짐)
- 스키마가 자주 수정 되는 곳에서는 별로다. (스키마 수정 시 서버/클라이언트 모두 다시 생성되어야 함)
- `RESTful` 보다는 비주류임

### 그 밖에 다른 프로토콜
`Thrift` 나 `GraphQL` 같은 것들도 있다. (커뮤니티 보면 `GraphQL` 이 뭔가 더 슬슬 올라오고 있는 느낌임)

## gPRC 는 어떻게 사용할까?

다음과 같은 프로토버퍼 파일을 작성해야 한다. (서비스와 메시지 타입을 정의한다.)

```protobuf
syntax = "proto3";

// 서비스 구현
service Greeter {
  rpc SayHello (HelloRequest) returns (HelloReply) {};
  rpc SayHelloAgain (HelloRequest) returns (HelloReply) {}
}

// 메시지 구현
message HelloRequest {
  // unqiue 한 값을 사용해야 한다.
  string name = 1;
}

message HelloReply {
  string message = 1;
}
```

서비스를 정의하였으면 이 서비스 정의를 컴파일하고 소스 코드를 생성해야 한다.
해당 서비스로 클라이언트 stub / 서버 스켈레톤 을 생성한다.

<img src="https://grpc.io/img/landing-2.svg">

### 실행
```shell
# proto 파일로 코드 생성
$ ./gradlew build
```