## 백엔드 프로젝트 #1 게시판

### 목차

#### 1. Github 프로젝트 환경 설정

- 스프링 부트 프로젝트 생성
- 프로젝트 구조 이해
- Maven 또는 Gradle을 이용한 의존성 관리
- IDE 설정
- Github Repository 구성

#### 2. 데이터베이스 설정

- H2 Database 또는 MySQL 등을 사용하여 데이터베이스 설정
- JPA(Java Persistence API)를 사용하여 데이터베이스 연동
- 엔티티 클래스 생성 및 관계 설정

(참고): https://congsong.tistory.com/46

#### 3. 게시글(CRUD) 기능 구현

- 게시글 작성, 읽기, 수정, 삭제(CRUD) 기능 구현
- 컨트롤러(Controller), 서비스(Service), 리포지토리(Repository) 구조로 나누어 구현
- Thymeleaf를 사용하여 간단한 화면 구성

#### 4.댓글(Comment) 기능 추가

- 댓글 작성, 읽기, 수정, 삭제(CRUD) 기능 구현
- 게시글과 댓글 간의 관계 설정

#### 5. 사용자 인증 및 권한 관리

- 스프링 시큐리티(Spring Security)를 사용하여 로그인 기능 추가
- 사용자 권한에 따라 게시글 작성 및 수정 권한 관리

#### 6. 예외 처리와 검증

- 입력 데이터의 유효성 검증 및 에러 처리
- 전역 예외 처리 구현

#### 7. 프로젝트 테스트

- JUnit 또는 TestNG를 사용하여 단위 테스트 구현
- 통합 테스트를 통해 전체 시스템 테스트

#### 8. 프로젝트 디버깅 및 로깅

- 디버깅 기술 활용
- 로깅 설정 및 활용

#### 9. 보안 강화

- XSS(Cross-Site Scripting) 및 CSRF(Cross-Site Request Forgery) 방어
- HTTPS 적용

#### 10. 프로젝트 배포

- 스프링 부트 애플리케이션을 WAR 또는 JAR로 빌드
- 클라우드 서비스(예: AWS, Heroku)를 사용하여 배포

#### 11. 추가 기능 (선택 사항)

- 파일 업로드 및 다운로드
- 검색 기능 추가
- 페이징 및 정렬 기능 구현
- RESTful API 추가


