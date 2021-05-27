# PLAY MAKER (놀이 시설 이용 매칭 서비스)

팀페이지 주소: https://github.com/kookmin-sw/capstone-2021-1/

![pm mp4_20210404_215124 270 (1)](https://user-images.githubusercontent.com/28519975/113509620-96ef6500-9591-11eb-9232-84860fafe30f.png)
![pm mp4_20210404_220449 432](https://user-images.githubusercontent.com/28519975/113509709-15e49d80-9592-11eb-9ffd-246f39e4edba.png)
![pm mp4_20210404_220500 631](https://user-images.githubusercontent.com/28519975/113509713-18df8e00-9592-11eb-8148-6fd026bef244.png)

![pm mp4_20210404_220516 786](https://user-images.githubusercontent.com/28519975/113509715-1b41e800-9592-11eb-98f2-7ef8fe243b37.png)
![pm mp4_20210404_220536 942](https://user-images.githubusercontent.com/28519975/113509719-1d0bab80-9592-11eb-8ca1-9099cda94152.png)
![pm mp4_20210404_220549 942](https://user-images.githubusercontent.com/28519975/113509725-1ed56f00-9592-11eb-83c2-bf72e1d9e561.png)



## 1. 프로잭트 소개

### 프로젝트 의미

* 회원 간의 Play를 Making 해주는 서비스
* 회원이 직접 Game 내의 Playmaker가 되어 활동
            
### 주요 기능

* 놀이 시설 이용 시 인원이 필요할 경우에 매칭 진행
* 매칭 종료 후, 상호 간 평가를 통해 유저에 대한 정보 축적 (매너, 게임이해도 등)
* 같은 취미를 공유하는 사람들끼리 크루(그룹)을 형성

### 플랫폼

* App(WebView)

### 타겟

* 여러 명이서 하는 활동을 하고 싶지만 인원이 부족한 사람
* 같은 취미를 공유하는 사람들끼리 지속적으로 연락을 취하고 싶은 사람



## 2. 소개 영상



## 3. 팀 소개
~~~~~~~~~~
이름:   이민규
학번:   20163136
역할:   팀장, Back-end(DB), App/Web Design
이메일: bomblmk97@naver.com
~~~~~~~~~~
~~~~~~~~~~
이름:   음승호
학번:   20181652
역할:   Front-end(Web), App/Web Design
이메일: dmatmdgh49@gmail.com
~~~~~~~~~~
~~~~~~~~~~
이름:   이진구
학번:   20143095
역할:   Back-end(Server), App Design
이메일: dlwlsrn9411@kookmin.ac.kr
~~~~~~~~~~



## 4. 사용법

### 4.1. 도커를 사용할 경우

 docker pull jingu9/playmaker:0.1.5   
 docker run -d -p 8080:8080 jingu9/playmaker:0.1.5 httpd


### 4.2. 직접 사용할 경우 (Java11, maven 필요)

현재 백엔드 코드는 develop 브랜치에 존재합니다.

src/main/resources의 application.yml에는 include: aws를 삭제해주셔야 합니다.

src/main/resources에 application-deploy.yml파일 생성

application-deploy.yml 내용

<pre>
<code>
spring:
  h2:
    console:
      enabled: true
      settings:
        web-allow-others: true
      path: /h2-console
  datasource:
    driver-class-name: org.h2.Driver
    url: jdbc:h2:mem:testdb
  jpa:
    hibernate:
      ddl-auto: create-drop
</code>
</pre>

mvn clean install (-DskipTests 테스트 케이스를 스킵하실 경우)
mvn spring-boot:run
