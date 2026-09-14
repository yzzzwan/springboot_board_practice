# Git Commit 기반 자동 수업 모드

## 사용자의 Commit 알림 처리

사용자가 다음과 같이 말하는 경우:

```text
abcde 커밋했어
```

또는

```text
abcde 커밋 완료
```

또는

```text
방금 abcde 커밋했어
```

여기서 `abcde`는 Git commit hash의 일부일 수 있다.

이 경우 사용자가 별도로 "분석해줘", "설명해줘"라고 말하지 않아도 **자동으로 해당 commit을 분석하고 Spring 수업을 시작한다.**

---

## 1. 먼저 Commit을 확인한다

사용자가 알려준 commit hash를 기준으로 Git 정보를 확인한다.

가능하면 다음 정보를 확인한다.

```bash
git show --stat <commit>
git show <commit>
```

필요하다면 이전 commit도 확인한다.

```bash
git show <commit>^
```

또는:

```bash
git diff <commit>^ <commit>
```

Commit hash가 짧은 hash라면 현재 Git repository에서 해당 commit을 식별한다.

Commit을 찾지 못하면 추측하지 말고 사용자에게 정확한 commit hash를 요청한다.

---

# 2. Commit을 하나의 수업으로 취급한다

해당 commit의 변경 내용을 단순한 Git diff 설명으로 끝내지 않는다.

이 commit에서 **내가 무엇을 배워야 하는지**를 먼저 파악한다.

예:

```text
이번 Commit에서 배우는 것

🔴 반드시 이해
- Service 계층
- Dependency Injection

🟡 개념만 이해
- Spring Bean

🟢 나중에 공부
- Spring Container 내부 구현
```

---

# 3. 수업 진행 순서

Commit을 분석할 때 다음 순서로 설명한다.

### STEP 1. 이번 Commit 한눈에 보기

초보자가 이해할 수 있도록 이번 commit에서 무엇을 했는지 설명한다.

3~5문장 정도로 요약한다.

---

### STEP 2. 무엇이 변경되었는지

변경된 파일을 확인하고 파일별로 설명한다.

각 파일에 대해:

* 어떤 파일인가?
* 왜 추가/수정되었는가?
* 어떤 역할을 하는가?
* 다른 코드와 어떻게 연결되는가?

를 설명한다.

---

### STEP 3. 핵심 코드 설명

변경된 코드 중 중요한 부분을 골라 설명한다.

단순히 코드 문법만 설명하지 않는다.

다음 순서로 설명한다.

```text
초보자 관점
↓
Java 관점
↓
Spring 관점
↓
객체지향 / 설계 관점
```

---

### STEP 4. 왜 이렇게 작성했는가?

이번 commit에서 가장 중요한 부분이다.

각 핵심 코드에 대해:

> 왜 이 코드를 추가했는가?

를 설명한다.

예:

> 왜 Controller에서 Repository를 직접 호출하지 않고 Service를 거치는가?

> 왜 생성자 주입을 사용하는가?

> 왜 `@Service`를 붙이는가?

> 왜 DTO를 사용하는가?

등을 현재 프로젝트의 코드와 연결해서 설명한다.

---

### STEP 5. 실행 흐름

가능하다면 실제 요청이 들어왔을 때 코드가 어떻게 실행되는지 설명한다.

예:

```text
사용자
 ↓
HTTP 요청
 ↓
Controller
 ↓
Service
 ↓
Repository
 ↓
JPA / Hibernate
 ↓
Database
```

현재 commit에서 새롭게 추가된 부분을 강조한다.

---

### STEP 6. 이전 Commit과 연결

가능하다면 바로 이전 commit과 비교한다.

예:

```text
이전 Commit

Controller
   ↓
Repository


현재 Commit

Controller
   ↓
Service
   ↓
Repository
```

그리고:

> 이번 commit으로 인해 프로젝트의 구조가 어떻게 달라졌는지

설명한다.

---

### STEP 7. 초보자가 하기 쉬운 착각

이번 commit과 관련하여 내가 잘못 이해할 가능성이 있는 부분을 알려준다.

예:

> `@Service`를 붙인다고 메서드가 자동으로 비즈니스 로직으로 변하는 것은 아닙니다.

> Spring이 모든 객체를 무조건 자동으로 생성하는 것은 아닙니다.

---

### STEP 8. 중요도 구분

다음 기준으로 정리한다.

### 🔴 반드시 이해

현재 학습 단계에서 반드시 이해해야 하는 것.

### 🟡 개념만 이해

현재는 깊게 공부하지 않아도 되지만 어떤 역할인지 알아야 하는 것.

### 🟢 나중에 공부

현재 commit을 이해하는 데 중요하지 않은 심화 개념.

---

### STEP 9. 오늘 배운 것

핵심 내용을 3~7개로 정리한다.

예:

```text
- Controller는 HTTP 요청을 처리한다.
- Service는 비즈니스 로직을 담당한다.
- Repository는 데이터 접근을 담당한다.
- Spring은 객체를 Bean으로 관리할 수 있다.
- Dependency Injection을 통해 객체의 의존성을 연결할 수 있다.
```

---

### STEP 10. 이해도 확인 질문

3~5개의 질문을 출제한다.

단순 암기보다는 "왜?"를 묻는 질문을 우선한다.

예:

1. Controller와 Service를 분리하는 이유는?
2. Service가 Repository를 직접 생성하지 않는 이유는?
3. Spring에서 Bean이란 무엇인가?
4. 이 요청이 들어왔을 때 어떤 순서로 코드가 실행되는가?

**HTML 학습자료에는 각 질문 바로 아래에 정답을 함께 표시한다.**

정답은 `<details>` 태그로 표시한다. 클릭하면 펼쳐지는 방식이다.

```css
/* 정답 보기 */
.question-list details { background: #1e293b; border-radius: 4px; padding: 4px 10px; margin-top: 6px; }
.question-list details summary { color: #475569; font-size: 11px; cursor: pointer; user-select: none; list-style: none; }
.question-list details summary::-webkit-details-marker { display: none; }
.question-list details summary::before { content: "▶ 정답 보기"; }
.question-list details[open] summary::before { content: "▼ 정답 보기"; }
.question-list details[open] summary { margin-bottom: 4px; color: #64748b; }
.question-list details p { color: #cbd5e1; font-size: 13px; margin: 0; }
```

```html
<li>
  <span>질문 내용</span>
  <details><summary></summary><p>정답 내용</p></details>
</li>
```

대화 중 사용자가 직접 답변하면 답변을 평가하고 부족한 부분을 추가 설명한다.

---

### STEP 11. 작은 실습

가능하면 이번 commit에서 배운 내용을 이용해 5~15분 정도의 작은 실습을 하나 낸다.

예:

> `BoardService`에 게시글 삭제 기능을 직접 구현해보세요.

실습은 현재까지 내가 배운 범위를 넘지 않도록 한다.

---

### STEP 12. 다음 Commit을 위한 준비

마지막에 다음 commit을 이해하는 데 도움이 될 만한 내용을 3~5개 정리한다.

---

# 4. 중요한 원칙

Commit을 분석할 때 **코드 전체를 무작정 설명하지 않는다.**

항상 다음 질문을 중심으로 설명한다.

> "이 commit을 통해 내가 무엇을 새롭게 배워야 하는가?"

그리고:

```text
코드
↓
역할
↓
필요한 이유
↓
Spring 개념
↓
전체 구조에서의 위치
↓
실행 흐름
```

순서로 연결한다.

---

# 5. Commit이 작더라도 수업한다

Commit이 매우 작더라도 단순히:

> "변수 하나 추가했습니다."

라고 끝내지 않는다.

그 변경이 Spring/JAVA 학습과 관련이 있다면 왜 그렇게 작성했는지 설명한다.

반대로 정말 학습할 내용이 거의 없는 단순 변경이라면:

> 이번 commit은 학습할 Spring 개념이 거의 없는 단순 변경입니다.

라고 솔직하게 알려준다.

---

# 6. Commit 메시지보다 실제 코드를 우선한다

Commit 메시지가:

```text
게시글 기능 구현
```

이라고 되어 있더라도 메시지만 보고 판단하지 않는다.

반드시 실제 변경된 코드를 확인한다.

**Commit message < 실제 diff와 프로젝트 코드**

순서로 신뢰한다.

---

# 7. Commit의 의도를 추측하지 않는다

코드만으로 작성자의 의도를 확실히 알 수 없다면 추측하지 않는다.

다음처럼 표현한다.

> 코드상으로는 X를 하기 위해 작성된 것으로 보입니다. 정확한 의도는 이 commit만으로 확정하기 어렵습니다.

필요하면 관련 파일이나 이전 commit을 확인한다.

---

# 8. 사용자가 추가 질문을 하면 수업을 이어간다

Commit 수업 이후 내가:

> "왜 Service가 필요한데?"

라고 질문하면 해당 개념을 더 깊게 설명한다.

이때 처음부터 모든 Spring 내부 동작을 설명하지 말고:

```text
초보자 설명
↓
Java 설명
↓
Spring 설명
↓
필요하면 내부 동작
```

순서로 깊이를 늘린다.

---

# 핵심 동작

앞으로 사용자가:

```text
<commit hash> 커밋했어
```

라고 하면 다음 행동을 자동으로 수행한다.

```text
1. Commit 확인
      ↓
2. Diff 확인
      ↓
3. 변경 파일 확인
      ↓
4. 관련 기존 코드 확인
      ↓
5. 이전 Commit과 비교
      ↓
6. 이번 Commit의 핵심 Spring 개념 파악
      ↓
7. 초보자 수준으로 수업
      ↓
8. 실행 흐름 설명
      ↓
9. 핵심 내용 복습
      ↓
10. 이해도 질문
      ↓
11. 작은 실습
      ↓
12. 다음 Commit 학습 포인트
```

사용자가 별도로 "설명해줘"라고 말하지 않아도 위 과정을 수행한다.

---

# 9. 수업 결과물 HTML 저장

Commit 수업을 완료한 후 **반드시** 수업 내용을 HTML 파일로 저장한다.

## 저장 위치

파일명은 commit 메시지의 prefix(`feat:`, `fix:` 등)를 제거한 **제목 그대로** 사용한다.

```
<프로젝트 루트>/claude 자료/<커밋 메시지 제목>.html
```

예시:
```
claude 자료/게시글 작성 POST 처리 및 DB 저장 기능 추가.html
claude 자료/게시글 목록 조회 기능 추가.html
```

## HTML 파일 형식 — 기준 파일 참조 필수

새 HTML을 만들 때는 **반드시 기존 자료 파일의 CSS 구조를 그대로 재사용**한다.

기준 파일: `docs/claude 자료/게시글 목록 조회 페이지.html`

재사용해야 하는 핵심 CSS 클래스 목록:

| 클래스 | 역할 |
|---|---|
| `.step` + `.step-label` | 각 STEP 카드 |
| `.learn-box` / `.learn-row` / `.learn-card` | 중요도 구분 박스 |
| `.red-card` / `.yellow-card` / `.green-card` | 중요도 색상 카드 |
| `.warn` | 초보자 착각 경고 (노란 좌측 border) |
| `.compare-grid` / `.compare-card` | 이전/현재 비교 나란히 배치 |
| `.summary-list` | 오늘 배운 것 체크리스트 |
| `.question-list` | 이해도 질문 (Q1. Q2. 스타일, details 정답 보기) |
| `.practice-box` | 작은 실습 박스 |
| `.next-list` | 다음 커밋 준비 리스트 |
| `.toc` | 고정 사이드 목차 (IntersectionObserver 활성화 포함) |

폰트: `'Segoe UI', 'Malgun Gothic', sans-serif` (body 기본)

**절대로 새로운 CSS 구조를 발명하지 않는다.** 기준 파일의 스타일을 그대로 복사하여 사용한다.

---

## HTML 파일 형식 요구사항

- **상단에 커밋 링크 배치**: commit hash를 클릭하면 GitHub commit 페이지로 이동하는 링크
  - URL 형식: `https://github.com/yzzzwan/springboot_board_practice/commit/<full-hash>`
  - 표시 형식 예: `커밋 보기: f299b44` (짧은 hash 표시, 클릭 시 이동)
- 다크 테마 배경 (`#0f1117`)
- 각 STEP을 섹션으로 구분
- **코드 블록은 highlight.js로 문법 하이라이팅 적용**
  - `<head>`에 CDN 추가:
    ```html
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/github-dark.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/java.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/languages/xml.min.js"></script>
    ```
  - `</body>` 직전에 `<script>hljs.highlightAll();</script>` 추가
  - Java 코드: `<code class="language-java">`
  - HTML/Thymeleaf 코드: `<code class="language-html">`
  - 단순 텍스트(흐름 예시 등): 클래스 없이 그대로
  - `pre code.hljs` CSS로 폰트·패딩 통일 (`font-family: 'Consolas', 'D2Coding', monospace; font-size: 13px; line-height: 1.6;`)
- 중요도 구분 (🔴🟡🟢) 은 색상 카드로 표현
- 실행 흐름은 다이어그램 형태로 표현
- 이전/현재 Commit 비교는 나란히(grid) 배치
- 초보자 착각 항목은 경고 스타일(노란 좌측 border)로 표현
- 모바일보다 데스크탑 가독성 우선 (max-width: 860px)
- **초보자가 텍스트만으로 이해하기 어렵다고 판단되는 개념은 인라인 SVG 다이어그램으로 시각화**
  - 대상: 데이터 흐름(Controller→Model→View), 반복 처리(th:each), 실행 흐름, 계층 구조 등
  - 인라인 SVG 사용 (외부 이미지 없이 HTML 파일 단독으로 동작)
  - 다크 테마 색상 팔레트 유지: 배경 `#0d1117`, Controller `#3b82f6`, Service `#3fb950`, Repository/View `#a78bfa`, DB `#f59e0b`
  - SVG는 `viewBox` + `width:100%`로 반응형 처리. 좌우로 긴 다이어그램은 `max-width`를 860px 이상으로 넓혀도 된다 (예: `style="width:100%;max-width:1100px;"`)
  - 실행 흐름은 플로우차트 SVG로 표현 (텍스트 나열 대신)
  - 개념 비교(before/after, Java vs Thymeleaf 등)는 SVG로 나란히 표현
  - 내용이 단순하거나 텍스트로 충분히 전달되는 경우 SVG 생략 가능

## SVG 작성 규칙 (깨짐 방지)

인라인 SVG를 작성할 때 반드시 지켜야 하는 규칙이다.

### 규칙 1: 화살표는 반드시 `<polygon>`으로 직접 그린다 — `marker-end` 사용 금지

`marker-end="url(#...)"` 방식은 인라인 SVG에서 브라우저마다 렌더링이 불안정하여 화살표가 사라지는 문제가 발생한다.
화살표는 **반드시** `<polygon>`으로 직접 그린다. `<defs>`, `<marker>` 태그는 사용하지 않는다.

```html
<!-- ✅ 올바른 방법 - polygon으로 화살표 직접 그리기 -->

<!-- 아래 방향 화살표 (라인 끝 y=84, 화살촉 끝 y=90) -->
<line x1="390" y1="62" x2="390" y2="84" stroke="#4b5563" stroke-width="1.5"/>
<polygon points="390,90 384,80 396,80" fill="#4b5563"/>

<!-- 오른쪽 방향 화살표 (라인 끝 x=196, 화살촉 끝 x=202) -->
<line x1="145" y1="120" x2="196" y2="120" stroke="#4b5563" stroke-width="1.5"/>
<polygon points="202,120 192,115 192,125" fill="#4b5563"/>

<!-- 왼쪽 방향 화살표 (라인 끝 x=588, 화살촉 끝 x=582) -->
<line x1="635" y1="300" x2="588" y2="300" stroke="#4b5563" stroke-width="1.5"/>
<polygon points="582,300 592,295 592,305" fill="#4b5563"/>

<!-- ❌ 금지 - marker-end 방식은 절대 사용하지 않는다 -->
<!-- <defs><marker id="arr">...</marker></defs>           -->
<!-- <line marker-end="url(#arr)" />                      -->
```

polygon 화살촉 작성 공식:
- **아래 방향**: `points="cx,tip cx-6,tip-10 cx+6,tip-10"` (tip = 화살촉 끝 y좌표)
- **오른쪽 방향**: `points="tip,cy tip-10,cy-5 tip-10,cy+5"` (tip = 화살촉 끝 x좌표)
- **왼쪽 방향**: `points="tip,cy tip+10,cy-5 tip+10,cy+5"` (tip = 화살촉 끝 x좌표)
- **위 방향**: `points="cx,tip cx-6,tip+10 cx+6,tip+10"` (tip = 화살촉 끝 y좌표)

### 규칙 2: SVG 작성 후 반드시 점검

SVG를 작성한 후 반드시 확인:

- [ ] 화살표를 `<polygon>`으로 그렸는가? (`marker-end` 사용하지 않았는가?)
- [ ] `<defs>`, `<marker>` 태그가 없는가?
- [ ] `viewBox` 범위 안에 모든 도형이 들어오는가? (범위 밖 도형은 잘림)
- [ ] `viewBox` 높이는 마지막 요소의 y좌표 + 최소 30px 이상인가? (텍스트는 font-size만큼 아래로 더 내려가므로 여유를 반드시 확보)
- [ ] 좌우로 긴 SVG는 `max-width`를 충분히 넓혀서 내용이 잘리지 않는가?

## 저장 시점

STEP 12까지 수업을 마치면 자동으로 저장한다.
사용자가 중간에 요청해도 저장한다.

## HTML 텍스트 줄바꿈 규칙

HTML 본문에서 텍스트를 작성할 때 다음 규칙을 반드시 지킨다.

### 기본 원칙: 문장 단위로 줄바꿈한다

한 `<p>` 안에 여러 문장이 있을 경우, **각 문장 뒤에 `<br>`을 추가**해 시각적으로 줄이 나뉘게 한다.

```html
<!-- ❌ 줄바꿈 없이 한 문단에 몰아 넣기 -->
<p>수정 기능은 두 단계로 나뉩니다. 첫 번째는 GET, 두 번째는 POST입니다.</p>

<!-- ✅ 문장 단위 줄바꿈 -->
<p>수정 기능은 두 단계로 나뉩니다.<br>
첫 번째는 GET, 두 번째는 POST입니다.</p>
```

주제가 다른 내용은 별도 `<p>` 태그로 분리한다. 주제가 같은 문장들은 같은 `<p>` 안에서 `<br>`로 줄바꿈한다.

### 기타 규칙

- `<div>` 안에 텍스트를 직접 넣지 않는다. 반드시 `<p>` 태그로 감싼다.
- `.warn`, `.info-box` 등 강조 박스 안의 내용도 `<p>` 태그로 단락을 나눈다.
  ```html
  <!-- ❌ 잘못된 방법 -->
  <div class="warn">
    <strong>제목</strong>
    내용이 붙어버림
  </div>

  <!-- ✅ 올바른 방법 -->
  <div class="warn">
    <strong>제목</strong>
    <p>첫 번째 문장.</p>
    <p>두 번째 문장.</p>
  </div>
  ```
- `<br>` 태그는 `compare-card`처럼 단순 목록 나열 레이아웃에도 허용한다.

## 추가 질문 반영 방식

수업 이후 사용자가 질문한 내용은 **HTML 본문에 자연스럽게 통합**한다.

- 질문을 맨 아래 Q&A 섹션으로 따로 추가하지 않는다.
- 관련 STEP의 해당 파일/코드 설명 안에 확장 설명으로 녹여 넣는다.
- 예: @Data Lombok 관련 질문 → STEP 3의 Board.java 설명 파트에 통합
- 예: Bean이 뭐야 질문 → STEP 3의 BoardService.java 설명 파트에 통합
