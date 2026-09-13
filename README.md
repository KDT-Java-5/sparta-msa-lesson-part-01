# 🚀 스파르타 MSA 과정 교안 예시 코드

> 스파르타 MSA 과정 **Part 01** 강의에서 사용하는 Spring Boot 기반 예시 프로젝트입니다.
> 주차·일차별 브랜치로 나뉘어 있어, 강의 진도에 맞춰 코드를 따라가며 학습할 수 있습니다.

![Branch](https://img.shields.io/badge/branch-week--02%2Fday--02-FF6F00)
![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.11-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.2-6DB33F?logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-8.14.4-02303A?logo=gradle&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?logo=flyway&logoColor=white)

---

## 📚 목차

- [Week 02 · Day 02 학습 내용](#-week-02--day-02-학습-내용)
- [브랜치 구성](#-브랜치-구성)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [시작하기](#-시작하기)

---

## 📌 Week 02 · Day 02 학습 내용

> **회원가입 API와 MapStruct** — 입력값 검증, 비밀번호 암호화, DTO ↔ 엔티티 변환을 갖춘 회원가입 API를 완성합니다.

| 주제 | 내용 | 관련 파일 |
|---|---|---|
| **회원가입 API** | `POST /api/users` — `@Valid`로 요청 검증 후 `201 Created` 응답 | `domain/user/controller/UserController.java` |
| **비즈니스 규칙** | 이메일 중복 확인 → `DUPLICATE_EMAIL` 예외 | `domain/user/service/UserService.java` |
| **비밀번호 암호화** | `BCryptPasswordEncoder`를 Bean으로 등록하고 저장 전에 암호화 | `global/config/SecurityConfig.java` |
| **MapStruct 기본** | `@Mapper(componentModel = "spring")`로 `UserRequest → User`, `User → UserResponse` 변환 코드 자동 생성 | `domain/user/mapper/UserMapper.java` |
| **MapStruct 필드 매핑** | `@Mapping`으로 이름이 다른 필드(`id → orderId`)와 중첩 객체 필드(`user.name → username`) 매핑 | `domain/order/mapper/OrderMapper.java` |
| **다른 도메인 조회** | 주문 서비스에서 유저를 조회하고 없으면 `NOT_FOUND_USER` 예외 (주문 로직은 뼈대만 작성) | `domain/order/service/OrderService.java` |
| **구조 정리** | `UserService` 인터페이스·`UserServiceImpl`·`BeanConfig` 삭제 → `@Service` 클래스 하나로 정리, DTO 필드 `username → name` | `domain/user/**` |

### 📨 회원가입 요청 예시

```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "홍길동", "email": "hong@example.com", "password": "password1234"}'
```

```json
{ "data": { "id": 1, "name": "홍길동", "email": "hong@example.com" } }
```

### ✅ 체크포인트

- [ ] `POST /api/users` 호출 시 `201`과 함께 `id`, `name`, `email`이 응답된다 (비밀번호는 응답에 없음)
- [ ] DB `users.password`에 BCrypt로 암호화된 값(`$2a$...`)이 저장된다
- [ ] 같은 이메일로 다시 가입하면 `400 DUPLICATE_EMAIL`, 이메일 형식이 틀리면 `400 VALIDATION_ERROR`가 온다
- [ ] `./gradlew compileJava` 후 `build/generated`에서 MapStruct가 만든 `UserMapperImpl`을 확인할 수 있다

---

## 🌿 브랜치 구성

각 주차는 `original`(시작 코드)과 `day-XX`(일차별 완성 코드) 브랜치로 구성되며, `extra`는 추가 학습 브랜치입니다.

| 주차 | 시작 코드 | Day 01 | Day 02 | Day 03 | Day 04 / 추가 학습 |
|:---:|:---:|:---:|:---:|:---:|:---:|
| **Week 01** | `week-01/original` | `week-01/day-01` | `week-01/day-02` | `week-01/day-03` | `week-01/day-04` |
| **Week 02** | `week-02/original` | `week-02/day-01` | **`week-02/day-02`** 👈 | `week-02/day-03` | `week-02/extra` (추가 학습) |

```bash
# 이전 단계와 비교
git diff week-02/day-01 week-02/day-02

# 다음 강의 단계로 이동
git checkout week-02/day-03
```

---

## 🛠 기술 스택

| 분류 | 사용 기술 |
|---|---|
| **Language / Build** | Java 21, Gradle 8.14 |
| **Framework** | Spring Boot 3.3, Spring Cloud 2023.0 |
| **Web Server** | Undertow (Tomcat 대체) |
| **Persistence** | Spring Data JPA, QueryDSL 5.0, Flyway |
| **Database** | PostgreSQL |
| **Communication** | Spring Cloud OpenFeign, Spring Retry |
| **Validation** | Spring Validation (Hibernate Validator) |
| **Mapping** | MapStruct 1.5, Lombok |
| **API Docs** | springdoc-openapi (Swagger UI) |
| **Monitoring** | Spring Boot Actuator |
| **Test** | JUnit 5, Spring Boot Test |

---

## 📁 프로젝트 구조

```
sparta-msa-lesson-part-01
├── build.gradle
├── settings.gradle
├── gradle/wrapper
└── src
    ├── main
    │   ├── java/com/sparta/msa/lesson
    │   │   ├── domain
    │   │   │   ├── category
    │   │   │   │   ├── entity
    │   │   │   │   │   └── Category.java
    │   │   │   │   └── repository
    │   │   │   │       └── CategoryRepository.java
    │   │   │   ├── order
    │   │   │   │   ├── dto/response
    │   │   │   │   │   └── OrderResponse.java           ✨ NEW
    │   │   │   │   ├── entity
    │   │   │   │   │   ├── Order.java
    │   │   │   │   │   └── OrderProduct.java
    │   │   │   │   ├── mapper
    │   │   │   │   │   └── OrderMapper.java             ✨ NEW
    │   │   │   │   ├── repository
    │   │   │   │   │   ├── OrderProductRepository.java
    │   │   │   │   │   └── OrderRepository.java
    │   │   │   │   └── service
    │   │   │   │       └── OrderService.java            ✨ NEW
    │   │   │   ├── product
    │   │   │   │   ├── controller
    │   │   │   │   │   └── ProductController.java
    │   │   │   │   ├── dto
    │   │   │   │   │   ├── request
    │   │   │   │   │   │   └── ProductRequest.java
    │   │   │   │   │   └── response
    │   │   │   │   │       └── ProductResponse.java
    │   │   │   │   ├── entity
    │   │   │   │   │   └── Product.java
    │   │   │   │   ├── repository
    │   │   │   │   │   └── ProductRepository.java
    │   │   │   │   └── service
    │   │   │   │       ├── ProductService.java
    │   │   │   │       └── ProductServiceImpl.java
    │   │   │   └── user
    │   │   │       ├── controller
    │   │   │       │   └── UserController.java          📝 UPDATED
    │   │   │       ├── dto
    │   │   │       │   ├── request
    │   │   │       │   │   └── UserRequest.java         📝 UPDATED
    │   │   │       │   └── response
    │   │   │       │       └── UserResponse.java        📝 UPDATED
    │   │   │       ├── entity
    │   │   │       │   └── User.java
    │   │   │       ├── mapper
    │   │   │       │   └── UserMapper.java              ✨ NEW
    │   │   │       ├── repository
    │   │   │       │   └── UserRepository.java
    │   │   │       └── service
    │   │   │           └── UserService.java             📝 UPDATED
    │   │   ├── global
    │   │   │   ├── config
    │   │   │   │   ├── SecurityConfig.java              ✨ NEW
    │   │   │   │   └── SwaggerConfig.java
    │   │   │   ├── constants
    │   │   │   │   ├── enums
    │   │   │   │   │   └── OrderStatus.java
    │   │   │   │   └── Constants.java
    │   │   │   ├── exception
    │   │   │   │   ├── DomainException.java
    │   │   │   │   ├── DomainExceptionCode.java         📝 UPDATED
    │   │   │   │   └── GlobalExceptionHandler.java
    │   │   │   └── response
    │   │   │       └── ApiResponse.java
    │   │   └── LessonApplication.java
    │   └── resources
    │       ├── db/migration
    │       │   ├── V1__init_table.sql
    │       │   ├── V2__create_users_table.sql
    │       │   └── V3__create_product_table.sql
    │       └── application.yml
    └── test/java/com/sparta/msa/lesson
        └── LessonApplicationTests.java
```

> 🗑 이전 단계에서 삭제된 파일: `BeanConfig.java`, `UserServiceImpl.java`

---

## ▶️ 시작하기

### 1. 사전 준비

- **JDK 21**
- **PostgreSQL** — `localhost:5432`에 `sparta` 데이터베이스 생성

| 항목 | 값 |
|---|---|
| URL | `jdbc:postgresql://localhost:5432/sparta` |
| Username | `postgres` |
| Password | `postgres` |

```bash
# Docker로 PostgreSQL 실행 (선택)
docker run -d --name sparta-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=sparta \
  -p 5432:5432 postgres
```

### 2. 클론 및 빌드

```bash
git clone <repository-url>
cd sparta-msa-lesson-part-01
git checkout week-02/day-02

./gradlew build
```

### 3. 실행

```bash
./gradlew bootRun
```

| 주소 | 설명 |
|---|---|
| http://localhost:8080 | 애플리케이션 |
| http://localhost:8080/swagger-ui/index.html | Swagger UI |

### 🔗 API 목록

| Method | URL | 설명 |
|:---:|---|---|
| `GET` | `/api/products` | 상품 목록 조회 |
| `GET` | `/api/products/{id}` | 상품 단건 조회 |
| `POST` | `/api/products` | 상품 등록 (201 Created) |
| `PUT` | `/api/products/{id}` | 상품 수정 |
| `DELETE` | `/api/products/{id}` | 상품 삭제 (204 No Content) |
| `POST` | `/api/users` | 회원가입 (201 Created) |

### 4. 테스트

```bash
./gradlew test
```

---

<div align="center">

**Happy Coding! 🎉**

</div>
