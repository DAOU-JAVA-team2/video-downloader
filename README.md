## DAOU LOADER: 동영상 다운로드 프로그램
***"다우기술 ITS부문 신입사원 JAVA 활용 프로젝트"***

프로젝트 목적: 유튜브 크롤링과 GUI를 활용한 MVC 패턴의 동영상 다운로드 프로그램 구현을 통한 Java 및 프로그래밍 이해도 향상

팀원: 조준하, 황한울, 김병준, 조완규

프로젝트 기간: 240325~240329
* * *

### 프로젝트 설치


1. 클론
```
git clone https://github.com/DAOU-JAVA-team2/video-downloader.git
```
2. 폴더이동
```
cd develop/
```
3. 코드로 빌드시(IP 주소 확인 요망)
```
Intellij 사용 권장
```
4. DB 조회 및 수정
```
Heidi SQL을 통해 Maria DB를 조회 및 수정 가능
```
* * *
### 프로젝트 설계 구조
#### 1. Overview
![스크린샷 2024-03-29 095048](https://github.com/DAOU-JAVA-team2/video-downloader/assets/164966737/5750e807-cdac-4149-a99c-4a6354c6d437)

#### 2. 디자인 패턴

2.1. Server 디자인 패턴
	
![스크린샷 2024-03-29 095558](https://github.com/DAOU-JAVA-team2/video-downloader/assets/164966737/311502f1-0dd1-45e7-8f63-e40ca17e7263)

2.2. Client 디자인 패턴

![스크린샷 2024-03-29 095633](https://github.com/DAOU-JAVA-team2/video-downloader/assets/164966737/0e8b074a-b264-4592-b07a-b1528f4b027c)
******
### 프로젝트 핵심 기능 설명

####  핵심 기능 요약
![스크린샷 2024-03-29 101456](https://github.com/DAOU-JAVA-team2/video-downloader/assets/164966737/9c37b77b-44f0-4c19-9a33-ab19f5404d54)


#### 1. 서버 클라이언트 부문

![스크린샷 2024-03-29 111018](https://github.com/DAOU-JAVA-team2/video-downloader/assets/164966737/7a376930-2a28-4387-9468-21338b239130)
- MVC 패턴기반의 구조 설계를 통해 서버 클라이언트 구축의 효율성 재고



#### 2.데이터 관리 부문
![그림7](https://github.com/DAOU-JAVA-team2/video-downloader/assets/164966737/d9ff8fe6-41d1-4e3a-908e-9524476f211a)

- JDBC 기반으로 MVC 패턴의 모델을 구현하여 Maria DB를 DAO(DTO)로 연동할 수 있도록 코드 작성

#### 3. 크롤링 관련 구현 부문
![그림6](https://github.com/DAOU-JAVA-team2/video-downloader/assets/164966737/41fca1bb-dcec-40de-b68d-1c785550a445)   

- Json Parse Tree를 기반으로 동영상 다운로드 코드 구현

#### 3. 화면 구현 부문


![그림3](https://github.com/DAOU-JAVA-team2/video-downloader/assets/164966737/aa0e2477-da6f-4144-9931-7febfda8625d)
- [프레임 - 패널 - 컴포넌트] 구조 설정을 통한 가독성 향상 및 디버깅 대응 용이성 증대
- 이러한 요소를 활용하여 아래와 같이 화면 구현

![그림2](https://github.com/DAOU-JAVA-team2/video-downloader/assets/164966737/256fdb88-64bb-4d98-a265-ea00d5ac098c)
- 상기 구조와 다양한 레이아웃을 적용하여 화면 설계

