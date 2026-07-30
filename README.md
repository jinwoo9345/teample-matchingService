# 🤝 Teample (팀플) - 매칭 서비스 백엔드 API (teample-matchingService)

> **팀 프로젝트 매칭 및 팀원 모집을 위한 도메인 중심 Spring Boot 백엔드 서버**  
> 대학생 및 취준생을 위한 프로젝트 팀 빌딩, 프로필 및 온도(기여도) 기반 팀원 매칭, 프로젝트 모집 관리 API를 제공합니다.

---

## 🛠️ Tech Stack

### Framework & Language
- **Java 17**
- **Spring Boot 3.x**
- **Gradle**

### Security & Database
- **Spring Security**
- **JSON Web Token (JWT)** (`jjwt 0.11.5`) - Stateless 사용자 인증 및 Authorization
- **Spring Data JPA / Hibernate**
- **MySQL** (`mysql-connector-j`)

### API & Documentation
- **Springdoc OpenAPI (Swagger UI 2.8.5)** - REST API 자동 문서화 및 테스트
- **Spring Boot Starter Validation** - DTO 요청 데이터 검증
- **Lombok**

---

## 🏛️ Architecture & Domain Structure

도메인 주도 설계(Domain-Driven Design) 패턴을 적용하여 비즈니스 모듈별로 패키지를 깔끔하게 분리했습니다.

```
com.teample.matching
├── global/            # Security Config, JWT, Global Exception, Swagger Config
└── domain/
    ├── user/          # 회원가입, 로그인, 프로필, 온도(Temperature), 티어(Tier)
    ├── project/       # 프로젝트 모집글 생성, 상태 관리, 모집 인원 및 역할 설정
    ├── matching/      # 프로젝트 지원 신청, 승인/거절 매칭 워크플로우
    ├── chat/          # 팀원 간 채팅방, 메시지, 참여자 정보
    ├── review/        # 프로젝트 종료 후 팀원 상호 평가 시스템
    └── tag/           # 기술 스택 및 사용자/프로젝트 매칭 태그
```
각 도메인 모듈 내부에는 `Controller` ➔ `Service` ➔ `Repository` ➔ `DTO` ➔ `Entity(Domain)` 계층이 명확히 구분되어 있습니다.

---

## ✨ Key Features & Domain Entities

### 1. 👤 회원 및 프로필 관리 (`User`)
* **인증/인가:** JWT 기반의 회원가입, 로그인 및 Stateless 세션 처리
* **프로필 & 매너 지표:** 유저 신뢰도를 나타내는 **온도(Temperature)** 지표 및 **티어(Tier)** 시스템
* **매칭 태그:** 희망 직군, 전공, 선호 기술 스택 태그 설정

### 2. 📂 프로젝트 모집 관리 (`Project`, `ProjectMember`)
* **모집글 작성:** 프로젝트 타입(공모전/사이드프로젝트 등), 모집 인원, 마감일, 필수 역할 설정
* **멤버 역할 부여:** 팀장/팀원 권한 및 참여자별 역할(`projectRole`) 및 기여도 모니터링

### 3. 🔄 매칭 프로세스 (`Matching`)
* **지원 워크플로우:** 프로젝트 지원 신청(`APPLY`) ➔ 팀장 승인(`ACCEPTED`) / 거절(`REJECTED`) 매칭 상태 관리

### 4. 💬 소통 및 피드백 (`Chat`, `Review`)
* **팀 소통:** 프로젝트 참여자 간 채팅 세션 및 메시지 데이터 모델링
* **팀원 평가:** 프로젝트 완료 후 팀원 상호 리뷰 및 매너 점수 반영

---

## 🧪 API Documentation (Swagger)

서버 실행 후 아래 주소로 접속하면 전체 REST API 명세 및 상호작용 테스트가 가능합니다.

- **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`

---

## 📌 Project Status

본 저장소는 팀 프로젝트로 진행된 백엔드 서비스 코드로, **1차 백엔드 코어 도메인 설계 및 REST API 구현 완료** 후 현재는 프로젝트가 완성/아카이빙(Archived)된 상태입니다.

- [x] Java 17 & Spring Boot 3 기반 도메인 계층 아키텍처 설계
- [x] Spring Security + JWT 기반 Stateless 인증/인가 및 글로벌 예외 처리
- [x] User, Project, Matching 등 핵심 도메인 Entity 모델링 및 REST API 구현
- [x] Swagger UI를 통한 REST API 명세 및 문서화 연동
