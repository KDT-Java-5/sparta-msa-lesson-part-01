# 🚀 스파르타 MSA 과정 교안 예시 코드

> 스파르타 MSA 과정 **Part 01** 강의에서 사용하는 Spring Boot 기반 예시 프로젝트입니다.
> 주차·일차별 브랜치로 나뉘어 있어, 강의 진도에 맞춰 코드를 따라가며 학습할 수 있습니다.

![Branch](https://img.shields.io/badge/branch-week--02%2Foriginal-FF6F00)
![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.11-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.2-6DB33F?logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-8.14.4-02303A?logo=gradle&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?logo=flyway&logoColor=white)

---

## 📚 목차

- [Week 02 · 시작 코드 학습 내용](#-week-02--시작-코드-학습-내용)
- [브랜치 구성](#-브랜치-구성)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [시작하기](#-시작하기)

---

## 📌 Week 02 · 시작 코드 학습 내용

> **Week 02 시작 코드** — Week 01에서 완성한 도메인 모델(엔티티·Repository·DTO)을 바탕으로 API 계층을 쌓아 올립니다.

이 브랜치의 코드는 `week-01/day-04`와 같습니다. Week 02 강의를 시작하기 전에 아래 내용을 복습하세요.

| 일차 | 복습 내용 | 관련 파일 |
|---|---|---|
| **Day 01 · 환경 구성** | PostgreSQL 연결, Flyway 마이그레이션, Swagger UI 설정 | `application.yml`, `SwaggerConfig.java` |
| **Day 02 · 엔티티** | `User` 엔티티 매핑, 쿼리 메서드와 JPQL | `User.java`, `UserRepository.java` |
| **Day 03 · 연관관계** | 상품·카테고리·주문 도메인 모델링, `@ManyToOne`, 중간 엔티티 | `Product.java`, `Category.java`, `Order.java`, `OrderProduct.java` |
| **Day 04 · N+1** | 양방향 연관관계, Fetch Join, `@BatchSize`, 요청·응답 DTO | `User.java`, `UserRepository.java`, `UserRequest.java` |

### 🗺 현재 ERD

```mermaid
erDiagram
    users ||--o{ orders : "주문한다"
    orders ||--|{ order_products : "포함한다"
    products ||--o{ order_products : "주문된다"
    categories ||--o{ products : "분류한다"
    categories |o--o{ categories : "상위 카테고리"

    users {
        BIGINT id PK
        VARCHAR name
        VARCHAR email UK
        VARCHAR password
    }
    orders {
        BIGINT id PK
        BIGINT user_id FK
        DECIMAL total_price
        VARCHAR status
    }
    order_products {
        BIGINT id PK
        BIGINT order_id FK
        BIGINT product_id FK
        INT quantity
        DECIMAL price
    }
    products {
        BIGINT id PK
        BIGINT category_id FK
        VARCHAR name
        TEXT description
        DECIMAL price
        INT stock
    }
    categories {
        BIGINT id PK
        VARCHAR name
        BIGINT parent_id FK
    }
```

### ✅ 체크포인트

- [ ] 애플리케이션 실행 시 `V1` ~ `V3` 마이그레이션이 모두 적용된다
- [ ] 엔티티 간 연관관계와 연관관계의 주인을 ERD로 설명할 수 있다
- [ ] N+1 문제가 발생하는 상황과 해결 방법을 설명할 수 있다

---

## 🌿 브랜치 구성

각 주차는 `original`(시작 코드)과 `day-XX`(일차별 완성 코드) 브랜치로 구성됩니다.

| 주차 | 시작 코드 | Day 01 | Day 02 | Day 03 | Day 04 |
|:---:|:---:|:---:|:---:|:---:|:---:|
| **Week 01** | `week-01/original` | `week-01/day-01` | `week-01/day-02` | `week-01/day-03` | `week-01/day-04` |
| **Week 02** | **`week-02/original`** 👈 | `week-02/day-01` | `week-02/day-02` | `week-02/day-03` | `week-02/day-04` |

```bash
# 이전 단계와 비교
git diff week-01/day-04 week-02/original

# 다음 강의 단계로 이동
git checkout week-02/day-01
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
    │   │   │   │   ├── entity
    │   │   │   │   │   └── Product.java
    │   │   │   │   └── repository
    │   │   │   │       └── ProductRepository.java
    │   │   │   └── user
    │   │   │       ├── dto
    │   │   │       │   ├── request
    │   │   │       │   │   └── UserRequest.java
    │   │   │       │   └── response
    │   │   │       │       └── UserResponse.java
    │   │   │       ├── entity
    │   │   │       │   └── User.java
    │   │   │       └── repository
    │   │   │           └── UserRepository.java
    │   │   ├── global
    │   │   │   ├── config
    │   │   │   │   └── SwaggerConfig.java
    │   │   │   └── constants
    │   │   │       ├── enums
    │   │   │       │   └── OrderStatus.java
    │   │   │       └── Constants.java
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

> `week-01/day-04`와 코드가 같아서 변경 표시가 없습니다.

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
git checkout week-02/original

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

### 4. 테스트

```bash
./gradlew test
```

---

<div align="center">

**Happy Coding! 🎉**

</div>
