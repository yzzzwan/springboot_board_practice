# Spring Boot 게시판 프로젝트

Spring Boot를 학습하기 위해 만든 게시판 프로젝트입니다.  
유튜브 강의를 따라하며 기본 기능을 구현한 후, 직접 기능을 추가하며 확장했습니다.

---

## 학습 과정

| 구분 | 커밋 범위 | 내용 |
|---|---|---|
| 강의 따라하기 | `36d61e4` ~ `b8be5ae` | 게시글 CRUD, 파일 업로드, 페이징, 검색 기본 기능 |
| 자체 기능 추가 | `c18c8c7` ~  | 통합 검색, 입력값 검증, 파일 확장자 검사, 파일 삭제 연동, 엔티티 분리 등 |

---

## 기술 스택

| 분류 | 기술 |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 4.1.0 |
| ORM | Spring Data JPA / Hibernate |
| Template | Thymeleaf |
| Database | MariaDB |
| Build | Gradle |
| Etc | Lombok |

---

## 주요 기능

### 강의에서 구현한 기능
- 게시글 작성 / 목록 조회 / 상세 조회 / 수정 / 삭제
- 파일 첨부 업로드
- 페이지네이션
- 제목 / 내용 검색

### 직접 추가한 기능
- **통합 검색**: 제목+내용을 동시에 검색하는 옵션 추가 (`Total` / `Title` / `Content` select)
- **파일 확장자 검사**: `txt`, `jpg`, `png`, `jfif`만 허용, 그 외 확장자는 업로드 거부
- **게시글 삭제 시 파일 연동 삭제**: 게시글 삭제 시 첨부파일도 함께 삭제
- **수정 시 기존 파일 교체**: 파일 재업로드 시 이전 첨부파일 자동 삭제 후 교체
- **파일 다운로드**: UUID가 제거된 원본 파일명으로 다운로드
- **페이지네이션 범위 개선**: 전체 페이지 수가 0일 때 예외 처리
- **엔티티 분리**: `Board` 엔티티에서 파일 정보를 `BoardFile`로 분리, `User` 엔티티 추가

---

## 프로젝트 구조

```
src/main/java/com/study/board/
├── controller/
│   └── BoardController.java     # HTTP 요청 처리
├── service/
│   └── BoardService.java        # 비즈니스 로직
├── repository/
│   ├── BoardRepository.java
│   ├── BoardFileRepository.java
│   └── UserRepository.java
└── entity/
    ├── Board.java               # 게시글
    ├── BoardFile.java           # 첨부파일
    └── User.java                # 사용자
```

---

## 실행 방법

**1. 데이터베이스 설정**

MariaDB에 `board` 스키마를 생성합니다.

```sql
CREATE DATABASE board;
```

**2. `application.properties` 수정**

```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/board
spring.datasource.username=root
spring.datasource.password=1234
```

**3. 실행**

```bash
./gradlew bootRun
```

브라우저에서 `http://localhost:8080` 접속 시 게시글 목록 페이지로 이동합니다.

---

## 허용 파일 확장자

첨부파일은 아래 확장자만 허용합니다.

`txt` `jpg` `png` `jfif`

---

## 향후 개선 예정

- [ ] 로그인 / 회원가입 기능 (`User` 엔티티 활용)
- [ ] 게시글별 작성자 표시
- [ ] 다중 파일 첨부
