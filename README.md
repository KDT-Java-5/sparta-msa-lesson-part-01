# 🚀 스파르타 MSA 과정 교안 예시 코드

> 스파르타 MSA 과정 **Part 01** 강의에서 사용하는 Spring Boot 기반 예시 프로젝트입니다.
> 주차·일차별 브랜치로 나뉘어 있어, 강의 진도에 맞춰 코드를 따라가며 학습할 수 있습니다.

![Branch](https://img.shields.io/badge/branch-week--01%2Fday--02-FF6F00)
![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.11-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.2-6DB33F?logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-8.14.4-02303A?logo=gradle&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?logo=flyway&logoColor=white)

---

## 📚 목차

- [Week 01 · Day 02 학습 내용](#-week-01--day-02-학습-내용)
- [브랜치 구성](#-브랜치-구성)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [시작하기](#-시작하기)

---

## 📌 Week 01 · Day 02 학습 내용

> **JPA 엔티티와 Repository** — 첫 번째 도메인인 `User`를 엔티티로 매핑하고, Spring Data JPA로 조회 메서드를 만들어 봅니다.

| 주제 | 내용 | 관련 파일 |
|---|---|---|
| **엔티티 매핑** | `@Entity`, `@Table(name = "users")`, `@Id` + `@GeneratedValue(IDENTITY)`로 테이블과 객체 매핑 | `domain/user/entity/User.java` |
| **엔티티 설계 관례** | JPA용 `protected` 기본 생성자, `@Builder` 생성자, `@FieldDefaults`로 필드 접근 제한 | `domain/user/entity/User.java` |
| **시간 자동 기록** | `@CreationTimestamp`, `@UpdateTimestamp`로 생성·수정 시각 자동 저장 | `domain/user/entity/User.java` |
| **동적 쿼리 생성** | `@DynamicInsert`, `@DynamicUpdate`로 null이 아니거나 변경된 컬럼만 SQL에 포함 | `domain/user/entity/User.java` |
| **쿼리 메서드** | `findByEmail`, `findByCreatedAtAfterOrderByNameAsc`, `countByName` — 메서드 이름으로 쿼리 생성 | `domain/user/repository/UserRepository.java` |
| **JPQL** | `@Query` + `@Param`으로 직접 작성한 JPQL 실행 (`findUserByEmail`) | `domain/user/repository/UserRepository.java` |
| **DB 마이그레이션** | Flyway `V2` 마이그레이션으로 `users` 테이블 생성 (이메일 UNIQUE) | `db/migration/V2__create_users_table.sql` |

### ✅ 체크포인트

- [ ] 애플리케이션 실행 시 Flyway가 `V2__create_users_table.sql`을 적용하고 `users` 테이블이 생성된다
- [ ] `UserRepository`의 쿼리 메서드 이름이 어떤 SQL로 바뀌는지 콘솔 SQL 로그로 확인할 수 있다
- [ ] `Optional`을 반환하는 이유(결과가 없을 수 있음)를 설명할 수 있다

---

## 🌿 브랜치 구성

각 주차는 `original`(시작 코드)과 `day-XX`(일차별 완성 코드) 브랜치로 구성됩니다.

| 주차 | 시작 코드 | Day 01 | Day 02 | Day 03 | Day 04 |
|:---:|:---:|:---:|:---:|:---:|:---:|
| **Week 01** | `week-01/original` | `week-01/day-01` | **`week-01/day-02`** 👈 | `week-01/day-03` | `week-01/day-04` |
| **Week 02** | `week-02/original` | `week-02/day-01` | `week-02/day-02` | `week-02/day-03` | `week-02/day-04` |

```bash
# 이전 단계와 비교
git diff week-01/day-01 week-01/day-02

# 다음 강의 단계로 이동
git checkout week-01/day-03
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
    │   │   ├── domain/user
    │   │   │   ├── entity
    │   │   │   │   └── User.java               ✨ NEW
    │   │   │   └── repository
    │   │   │       └── UserRepository.java     ✨ NEW
    │   │   ├── global
    │   │   │   ├── config
    │   │   │   │   └── SwaggerConfig.java
    │   │   │   └── constants
    │   │   │       └── Constants.java
    │   │   └── LessonApplication.java
    │   └── resources
    │       ├── db/migration
    │       │   ├── V1__init_table.sql
    │       │   └── V2__create_users_table.sql  ✨ NEW
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
git checkout week-01/day-02

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
