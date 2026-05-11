# JPA Practice Project

Spring Boot + JPA를 연습하기 위한 기본 프로젝트입니다.

## Stack

- Java 21
- Spring Boot 3.4.12
- Spring Data JPA
- H2 Database
- QueryDSL 5.0.0
- Lombok
- JUnit 5 / Mockito

## 실행

```bash
./gradlew bootRun
```

Windows:

```bash
gradlew.bat bootRun
```

## H2 Console

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:jpa-practice`
- User: `sa`
- Password: 없음

## API

### 회원 생성

```http
POST /api/members
Content-Type: application/json

{
  "name": "kim",
  "email": "kim@test.com",
  "age": 30
}
```

### 회원 단건 조회

```http
GET /api/members/1
```

### 회원 검색

```http
GET /api/members?name=kim&minAge=20&maxAge=40
```

### 회원 수정

```http
PATCH /api/members/1
Content-Type: application/json

{
  "name": "park",
  "age": 31
}
```

### 회원 삭제

```http
DELETE /api/members/1
```

## 연습 포인트

1. Entity 설계
2. Repository 기본 CRUD
3. JPQL / QueryDSL 동적 검색
4. Service 트랜잭션
5. Controller API 설계
6. Repository 테스트
7. Service 단위 테스트
8. Controller MVC 테스트
