# 🚀 스파르타 MSA 과정 교안 예시 코드

> 스파르타 MSA 과정 **Part 01** 강의에서 사용하는 Spring Boot 기반 예시 프로젝트입니다.
> 주차·일차별 브랜치로 나뉘어 있어, 강의 진도에 맞춰 코드를 따라가며 학습할 수 있습니다.

![Branch](https://img.shields.io/badge/branch-week--02%2Fday--01-FF6F00)
![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.11-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.2-6DB33F?logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-8.14.4-02303A?logo=gradle&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?logo=flyway&logoColor=white)

---

## 📚 목차

- [Week 02 · Day 01 학습 내용](#-week-02--day-01-학습-내용)
- [브랜치 구성](#-브랜치-구성)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [시작하기](#-시작하기)

---

## 📌 Week 02 · Day 01 학습 내용

> **계층형 아키텍처와 공통 응답·예외 처리** — Controller → Service → Repository 계층을 만들고, 모든 API가 같은 형식으로 응답하도록 공통 처리를 구성합니다.

| 주제 | 내용 | 관련 파일 |
|---|---|---|
| **REST 컨트롤러** | `/api/products` CRUD 엔드포인트, `@ResponseStatus`로 201·204 상태 코드 지정 | `domain/product/controller/ProductController.java` |
| **서비스 계층** | 상품 단건 조회 로직 구현 — 없으면 `DomainException` 발생 (나머지 메서드는 뼈대만 작성) | `domain/product/service/ProductService.java` |
| **의존성 주입 방식 비교** | 생성자 주입 · 필드 주입 · Setter 주입을 비교하고, `@RequiredArgsConstructor` 생성자 주입 사용 | `domain/product/service/ProductServiceImpl.java` |
| **IoC와 Bean 등록** | `UserService` 인터페이스 + 구현체를 `@Configuration` / `@Bean`으로 직접 등록하고 컨트롤러에 주입 | `global/config/BeanConfig.java`, `domain/user/**` |
| **공통 응답 포맷** | `ApiResponse<T>` — 성공은 `data`, 실패는 `error { errorCode, errorMessage }`로 통일 (`null` 필드는 제외) | `global/response/ApiResponse.java` |
| **도메인 예외** | `DomainExceptionCode` enum에 HTTP 상태·메시지를 모아두고 `DomainException`으로 던짐 | `global/exception/DomainException*.java` |
| **전역 예외 처리** | `@RestControllerAdvice`로 도메인 예외, 검증 실패(`VALIDATION_ERROR`), 그 밖의 예외(`SERVER_ERROR`) 처리 | `global/exception/GlobalExceptionHandler.java` |
| **상품 DTO** | `ProductRequest`(`@NotNull`, `@Positive`, `@PositiveOrZero`), `ProductResponse`(`@JsonFormat` 날짜 형식) | `domain/product/dto/**` |

### 📦 공통 응답 형식

```jsonc
// 성공
{ "data": { "id": 1, "name": "노트북", "price": 1500000, "stock": 10 } }

// 실패 (GET /api/products/9999)
{ "error": { "errorCode": "NOT_FOUND_PRODUCT", "errorMessage": "상품 정보를 찾을 수 없습니다." } }
```

### ✅ 체크포인트

- [ ] Swagger UI에 `/api/products` API 5개가 보인다
- [ ] `GET /api/products/9999`처럼 없는 상품을 조회하면 `404`와 `NOT_FOUND_PRODUCT` 에러 응답이 온다
- [ ] `@Configuration` + `@Bean`으로 등록한 `UserService`가 `UserController`에 주입된다
- [ ] 생성자 주입을 권장하는 이유를 설명할 수 있다

---

## 🌿 브랜치 구성

각 주차는 `original`(시작 코드)과 `day-XX`(일차별 완성 코드) 브랜치로 구성됩니다.

| 주차 | 시작 코드 | Day 01 | Day 02 | Day 03 | Day 04 |
|:---:|:---:|:---:|:---:|:---:|:---:|
| **Week 01** | `week-01/original` | `week-01/day-01` | `week-01/day-02` | `week-01/day-03` | `week-01/day-04` |
| **Week 02** | `week-02/original` | **`week-02/day-01`** 👈 | `week-02/day-02` | `week-02/day-03` | `week-02/day-04` |

```bash
# 이전 단계와 비교
git diff week-02/original week-02/day-01

# 다음 강의 단계로 이동
git checkout week-02/day-02
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
    │   │   │   │   ├── entity
    │   │   │   │   │   ├── Order.java
    │   │   │   │   │   └── OrderProduct.java
    │   │   │   │   └── repository
    │   │   │   │       ├── OrderProductRepository.java
    │   │   │   │       └── OrderRepository.java
    │   │   │   ├── product
    │   │   │   │   ├── controller
    │   │   │   │   │   └── ProductController.java       ✨ NEW
    │   │   │   │   ├── dto
    │   │   │   │   │   ├── request
    │   │   │   │   │   │   └── ProductRequest.java      ✨ NEW
    │   │   │   │   │   └── response
    │   │   │   │   │       └── ProductResponse.java     ✨ NEW
    │   │   │   │   ├── entity
    │   │   │   │   │   └── Product.java
    │   │   │   │   ├── repository
    │   │   │   │   │   └── ProductRepository.java
    │   │   │   │   └── service
    │   │   │   │       ├── ProductService.java          ✨ NEW
    │   │   │   │       └── ProductServiceImpl.java      ✨ NEW
    │   │   │   └── user
    │   │   │       ├── controller
    │   │   │       │   └── UserController.java          ✨ NEW
    │   │   │       ├── dto
    │   │   │       │   ├── request
    │   │   │       │   │   └── UserRequest.java
    │   │   │       │   └── response
    │   │   │       │       └── UserResponse.java
    │   │   │       ├── entity
    │   │   │       │   └── User.java
    │   │   │       ├── repository
    │   │   │       │   └── UserRepository.java
    │   │   │       └── service
    │   │   │           ├── UserService.java             ✨ NEW
    │   │   │           └── UserServiceImpl.java         ✨ NEW
    │   │   ├── global
    │   │   │   ├── config
    │   │   │   │   ├── BeanConfig.java                  ✨ NEW
    │   │   │   │   └── SwaggerConfig.java
    │   │   │   ├── constants
    │   │   │   │   ├── enums
    │   │   │   │   │   └── OrderStatus.java
    │   │   │   │   └── Constants.java
    │   │   │   ├── exception
    │   │   │   │   ├── DomainException.java             ✨ NEW
    │   │   │   │   ├── DomainExceptionCode.java         ✨ NEW
    │   │   │   │   └── GlobalExceptionHandler.java      ✨ NEW
    │   │   │   └── response
    │   │   │       └── ApiResponse.java                 ✨ NEW
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
git checkout week-02/day-01

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

### 4. 테스트

```bash
./gradlew test
```

---

<div align="center">

**Happy Coding! 🎉**

</div>
