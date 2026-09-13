# 🚀 스파르타 MSA 과정 교안 예시 코드

> 스파르타 MSA 과정 **Part 01** 강의에서 사용하는 Spring Boot 기반 예시 프로젝트입니다.
> 주차·일차별 브랜치로 나뉘어 있어, 강의 진도에 맞춰 코드를 따라가며 학습할 수 있습니다.

![Branch](https://img.shields.io/badge/branch-week--01%2Fday--01-FF6F00)
![Java](https://img.shields.io/badge/Java-21-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.11-6DB33F?logo=springboot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2023.0.2-6DB33F?logo=spring&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-8.14.4-02303A?logo=gradle&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?logo=flyway&logoColor=white)

---

## 📚 목차

- [Week 01 · Day 01 학습 내용](#-week-01--day-01-학습-내용)
- [브랜치 구성](#-브랜치-구성)
- [기술 스택](#-기술-스택)
- [프로젝트 구조](#-프로젝트-구조)
- [시작하기](#-시작하기)

---

## 📌 Week 01 · Day 01 학습 내용

> **프로젝트 기본 환경 구성** — DB 연결, 마이그레이션, API 문서화까지 개발을 시작할 준비를 합니다.

| 주제 | 내용 | 관련 파일 |
|---|---|---|
| **데이터베이스 연결** | PostgreSQL datasource 설정, JPA/Hibernate 설정 (`ddl-auto: none`) | `application.yml` |
| **DB 마이그레이션** | Flyway로 스키마 버전 관리, 첫 마이그레이션으로 `example` 테이블 생성 | `db/migration/V1__init_table.sql` |
| **API 문서화** | springdoc-openapi Swagger UI 설정, JWT Bearer 인증 스키마 등록 | `global/config/SwaggerConfig.java` |
| **공통 패키지 구성** | 전역 설정·상수를 담는 `global` 패키지 구조 마련 | `global/constants/Constants.java` |
| **로깅** | Hibernate가 실행하는 SQL을 DEBUG 레벨로 출력 | `application.yml` |
| **의존성 정리** | Spring Boot 3.3.11로 조정, 아직 사용하지 않는 Security·Redis 의존성 제거 | `build.gradle` |

### ✅ 체크포인트

- [ ] 애플리케이션 실행 시 Flyway가 `V1__init_table.sql`을 적용하고 `example` 테이블이 생성된다
- [ ] 콘솔에 Hibernate SQL 로그가 출력된다
- [ ] http://localhost:8080/swagger-ui/index.html 에서 Swagger UI가 열린다

---

## 🌿 브랜치 구성

각 주차는 `original`(시작 코드)과 `day-XX`(일차별 완성 코드) 브랜치로 구성됩니다.

| 주차 | 시작 코드 | Day 01 | Day 02 | Day 03 | Day 04 |
|:---:|:---:|:---:|:---:|:---:|:---:|
| **Week 01** | `week-01/original` | **`week-01/day-01`** 👈 | `week-01/day-02` | `week-01/day-03` | `week-01/day-04` |
| **Week 02** | `week-02/original` | `week-02/day-01` | `week-02/day-02` | `week-02/day-03` | `week-02/day-04` |

```bash
# 이전 단계(시작 코드)와 비교
git diff week-01/original week-01/day-01

# 다음 강의 단계로 이동
git checkout week-01/day-02
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
    │   │   ├── LessonApplication.java
    │   │   └── global
    │   │       ├── config
    │   │       │   └── SwaggerConfig.java      ✨ NEW
    │   │       └── constants
    │   │           └── Constants.java          ✨ NEW
    │   └── resources
    │       ├── application.yml                 📝 UPDATED
    │       └── db/migration
    │           └── V1__init_table.sql          ✨ NEW
    └── test
        └── java/com/sparta/msa/lesson
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
git checkout week-01/day-01

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
