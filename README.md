# **여행 여정을 기록과 관리하는 SNS 서비스**
<br/>


## 🖥️ 프로젝트 소개

여행과 그 세부 여정을 기록, 수정하고 조회하는 서비스입니다.

- 토이프로젝트 2단계 - Spring Boot
  
<br/>


## 📌 주요 기능



### 여행 기록 및 여정 기록 기능

- 여행 일정 기록
- 하나의 여행에 여러 개의 일정 기록
<br/>
  

### 여행 수정 및 여정 수정 기능

- 여행의 정보 수정
- 여정의 정보 수정
<br/>
  

### 여행 정보 조회 기능

- 저장된 여행 전체 리스트 조회
- 여행 전체 리스트에서 확인된 아이디를 입력하면 해당 여행 정보 조회
<br/>

## 💡예외처리



- 오류 메시지 출력

<br/>

## 1️⃣여행,여정의 등록,수정 시 맞지 않는 데이터일 경우

### 1. 데이터 입력 형식

- 날짜 등 컬럼의 형식 맞지 않을 때
- 입력 길이가 컬럼의 size를 초과하였을 경우
- null일 경우
       
<br/>

### 2. 여행과 여정 날짜의 논리성

- 여행의 종료 날짜가 시작 날짜보다 앞에 있는 경우<br/>
- 여정의 과정 종료 일시가 시작 일시보다 앞에 있는 경우<br/>
- 각 여정의 시작, 종료일시가 속한 여행의 날짜범위 밖에 있을 경우<br/>
<br/>

## 2️⃣여행 조회 및 수정 시 없는 데이터일 경우

- 여행 id를 조회하였으나 해당 여행이 없는 경우
<br/>

## 📝 [API 명세서 링크](https://kwonminwoos-organization.gitbook.io/trip-itinerary-project-api/)
## 📝 [프로젝트 설계서 링크](https://brick-freckle-cec.notion.site/2-c7bdf106d0ce4b01b64c8681174f8b4f?pvs=4)

<br/>

## 🗂 ERD

![image](https://github.com/Kwonminwoo/KDT_Y_BE_Toy_Project2_work/assets/132974447/27fa7cbf-51ae-4c3a-9037-597c980233ed)

<br/>

## 🕰️ 개발 기간



- 23.10.23 ~ 23.10.27

<br/>

## 🧑‍🤝‍🧑 멤버 구성



- [권민우](https://github.com/Kwonminwoo)
- [백인권](https://github.com/BackInGone)
- [임경민](https://github.com/pabu-lim)
- [유현](https://github.com/yuhyun1)

<br/>

## ⚙️ 개발 환경

실행 조건
- application.yml 파일의 datasource username, password를 환경에 맞추어 설정
- 연결 될 mysql에 trip_itinerary 데이터베이스 생성 후 실행
<br>

- Java 17
- Spring 3.1.5
- Gradle 8.3
- 의존성
    - Spring Web
    - Lombok
    - JPA
    - Mysql
    - Validation
