# 🚀 스파르타 MSA 과정 교안 예시 코드

> 스파르타 MSA 과정 **Part 01** 강의에서 사용하는 Spring Boot 기반 예시 프로젝트입니다.
> 주차·일차별 브랜치로 나뉘어 있어, 강의 진도에 맞춰 코드를 따라가며 학습할 수 있습니다.

![Branch](https://img.shields.io/badge/branch-week--02%2Fextra-FF6F00)
![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.11-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.2-6DB33F?logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-8.14.4-02303A?logo=gradle&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?logo=flyway&logoColor=white)

---

## 📚 목차

- [Week 02 · 추가 학습 내용](#-week-02--추가-학습-내용)
- [브랜치 구성](#-브랜치-구성)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [시작하기](#-시작하기)

---

## 📌 Week 02 · 추가 학습 내용

> **[추가 학습] QueryDSL** — 정규 커리큘럼 이후 심화 내용으로, 타입 안전한 코드로 동적 조건, 조인, 페이징, DTO 조회, 그룹 집계 쿼리를 작성합니다.

| 주제 | 내용 | 관련 파일 |
|---|---|---|
| **QueryDSL 설정** | Q클래스 생성 위치를 `build/generated/querydsl`로 지정하고 `clean` 시 삭제, `JPAQueryFactory` Bean 등록 | `build.gradle`, `global/config/QueryDslConfig.java` |
| **동적 조건** | `BooleanExpression`을 반환하는 메서드로 조건 분리 — `null`이면 조건에서 자동으로 빠짐 (`findProducts`) | `domain/product/repository/ProductQueryRepository.java` |
| **조인** | `join(product.category, category)`로 상위 카테고리 기준 상품 조회 (`findProductsByParentCategory`) | `ProductQueryRepository.java` |
| **페이징** | `offset`/`limit`로 목록을 조회하고 별도 count 쿼리와 함께 `PageImpl`로 반환 (`findPagedProducts`) | `ProductQueryRepository.java` |
| **DTO 조회** | `@QueryProjection` 생성자로 `QProductDTO` 생성, 필요한 컬럼만 조회 (`findProductDTOs`, `findCategoryProducts`) | `domain/product/dto/ProductDTO.java`, `CategoryProductDTO.java` |
| **그룹 집계** | `groupBy` + `count()`로 카테고리별 상품 수 조회 (`findCategoryProductCounts`) | `domain/product/dto/CategoryProductCountDTO.java` |

### ✅ 체크포인트

- [ ] `./gradlew compileJava` 후 `build/generated/querydsl`에 `QProduct`, `QCategory`, `QProductDTO` 등이 생성된다
- [ ] `findProducts(null, 1000.0, null)`처럼 일부 조건만 넘기면 해당 조건만 WHERE 절에 포함된다
- [ ] 페이징 조회 시 콘텐츠 쿼리와 count 쿼리가 각각 실행되는 것을 SQL 로그로 확인할 수 있다
- [ ] 엔티티 조회와 DTO 조회의 차이와 각각의 사용 시점을 설명할 수 있다

---

## 🌿 브랜치 구성

각 주차는 `original`(시작 코드)과 `day-XX`(일차별 완성 코드) 브랜치로 구성되며, `extra`는 추가 학습 브랜치입니다.

| 주차 | 시작 코드 | Day 01 | Day 02 | Day 03 | Day 04 / 추가 학습 |
|:---:|:---:|:---:|:---:|:---:|:---:|
| **Week 01** | `week-01/original` | `week-01/day-01` | `week-01/day-02` | `week-01/day-03` | `week-01/day-04` |
| **Week 02** | `week-02/original` | `week-02/day-01` | `week-02/day-02` | `week-02/day-03` | **`week-02/extra`** 👈 (추가 학습) |

```bash
# 이전 단계와 비교
git diff week-02/day-03 week-02/extra

# Part 01의 마지막 단계입니다 🎉
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
├── build.gradle                                          📝 UPDATED
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
    │   │   │   │   ├── dto
    │   │   │   │   │   ├── request
    │   │   │   │   │   │   └── OrderRequest.java
    │   │   │   │   │   └── response
    │   │   │   │   │       └── OrderResponse.java
    │   │   │   │   ├── entity
    │   │   │   │   │   ├── Order.java
    │   │   │   │   │   └── OrderProduct.java
    │   │   │   │   ├── mapper
    │   │   │   │   │   └── OrderMapper.java
    │   │   │   │   ├── repository
    │   │   │   │   │   ├── OrderProductRepository.java
    │   │   │   │   │   └── OrderRepository.java
    │   │   │   │   └── service
    │   │   │   │       └── OrderService.java
    │   │   │   ├── product
    │   │   │   │   ├── controller
    │   │   │   │   │   └── ProductController.java
    │   │   │   │   ├── dto
    │   │   │   │   │   ├── request
    │   │   │   │   │   │   └── ProductRequest.java
    │   │   │   │   │   ├── response
    │   │   │   │   │   │   └── ProductResponse.java
    │   │   │   │   │   ├── CategoryProductCountDTO.java  ✨ NEW
    │   │   │   │   │   ├── CategoryProductDTO.java       ✨ NEW
    │   │   │   │   │   └── ProductDTO.java               ✨ NEW
    │   │   │   │   ├── entity
    │   │   │   │   │   └── Product.java
    │   │   │   │   ├── repository
    │   │   │   │   │   ├── ProductQueryRepository.java   ✨ NEW
    │   │   │   │   │   └── ProductRepository.java
    │   │   │   │   └── service
    │   │   │   │       ├── ProductService.java
    │   │   │   │       └── ProductServiceImpl.java
    │   │   │   └── user
    │   │   │       ├── controller
    │   │   │       │   └── UserController.java
    │   │   │       ├── dto
    │   │   │       │   ├── request
    │   │   │       │   │   └── UserRequest.java
    │   │   │       │   └── response
    │   │   │       │       └── UserResponse.java
    │   │   │       ├── entity
    │   │   │       │   └── User.java
    │   │   │       ├── mapper
    │   │   │       │   └── UserMapper.java
    │   │   │       ├── repository
    │   │   │       │   └── UserRepository.java
    │   │   │       └── service
    │   │   │           └── UserService.java
    │   │   ├── global
    │   │   │   ├── config
    │   │   │   │   ├── QueryDslConfig.java               ✨ NEW
    │   │   │   │   ├── SecurityConfig.java
    │   │   │   │   └── SwaggerConfig.java
    │   │   │   ├── constants
    │   │   │   │   ├── enums
    │   │   │   │   │   └── OrderStatus.java
    │   │   │   │   └── Constants.java
    │   │   │   ├── exception
    │   │   │   │   ├── DomainException.java
    │   │   │   │   ├── DomainExceptionCode.java
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
git checkout week-02/extra

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
