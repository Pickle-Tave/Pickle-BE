## 1️⃣ Contributors

| Backend (Lead) | Backend | Backend |
|:--:|:--:|:--:|
| <a href="https://github.com/ht3064"> <img src="https://avatars.githubusercontent.com/u/145987233?v=4" width="140px;" alt="ht3064"/></a> | <a href="https://github.com/alswjdghks"> <img src="https://avatars.githubusercontent.com/u/132763978?v=4" width="140px;" alt="alswjdghks"/></a>| <a href="https://github.com/iiqcov"><img src="https://avatars.githubusercontent.com/u/154600308?v=4" width="140px;" alt="iiqcov"/></a> |
| 최현태 | 민정환 | 유지선 |

## 2️⃣ Project
- #### 🏠 [Project Notion](https://www.notion.so/MAIN-PAGE-ac1ac9a4178d495a8e56e3b8cf4e65ac)

## 3️⃣ Tech

### 📦️ Dependency
```
- Gradle 8.7
- Java 17
- Spring Boot 3.2.5
- MySQL 8.0.33
- QueryDSL 5.0.0
- Swagger 2.2.0
- Spring Cloud 2023.0.0
```

### 🏛️ Architecture
<p align="center">
  <img src="https://github.com/user-attachments/assets/9862be44-9252-42f6-bcb1-86259b31cdc9" width="800"/>
</p>
<br/>

### 🛠️ Tech Stack

#### Framework - <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-social&logo=Spring Boot&logoColor=white">  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-social&logo=Gradle&logoColor=white">

#### ORM - <img src="https://img.shields.io/badge/Spring Data JPA-6DB33F?style=for-the-social&logo=Databricks&logoColor=white">

#### Authorization - <img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-social&logo=springsecurity&logoColor=white">  <img src="https://img.shields.io/badge/JSON Web Tokens-000000?style=for-the-social&logo=JSON Web Tokens&logoColor=white">

#### Test - <img src="https://img.shields.io/badge/JUnit5-25A162?style=for-the-sociak&logo=junit5&logoColor=white"> 

#### Database - <img src="https://img.shields.io/badge/MySQL-4479A1.svg?style=for-the-social&logo=MySQL&logoColor=white">  <img src="https://img.shields.io/badge/Redis-%23DD0031.svg?logo=redis&logoColor=white">

#### CICD - <img src="https://img.shields.io/badge/GitHub_Actions-2088FF?logo=github-actions&logoColor=white">

#### AWS - <img src="https://img.shields.io/badge/AWS EC2-FF9900?style=for-the-&logo=amazonec2&logoColor=white"> <img src="https://img.shields.io/badge/AWS ECS-FF9900?style=for-the-&logo=amazonecs&logoColor=white"> <img src="https://img.shields.io/badge/AWS Fargate-FF9900?style=for-the-&logo=awsfargate&logoColor=white"> <img src ="https://img.shields.io/badge/AWS S3-69A31?style=for-the-social&logo=amazons3&logoColor=white"> <img src="https://img.shields.io/badge/AWS RDS-527FFF?style=for-the-social&logo=amazonrds&logoColor=white">  <img src ="https://img.shields.io/badge/AWS ElastiCache-C925D1?style=for-the-social&logo=amazonelasticache&logoColor=white"> <img src="https://img.shields.io/badge/ AWS Secretes Manager-DD344C?style=for-the-social&logo=awssecretsmanager&logoColor=white">

#### Code Coverage - <img src="https://img.shields.io/badge/SonarCloud-F3702A?logo=sonarcloud&logoColor=fff">

#### Other - <img src="https://img.shields.io/badge/ Swagger-6DB33F?style=for-the-social&logo=swagger&logoColor=white"> <img src="https://img.shields.io/badge/IntelliJIDEA-000000.svg?logo=intellij-idea&logoColor=white"> <img src="https://img.shields.io/badge/Git-F05032?logo=git&logoColor=fff">


### 🔍 ERD
<p align="center">
  <img src="https://github.com/user-attachments/assets/48463f3a-3824-4133-a5ee-76babad511a9" width="600"/>
</p>
<br/>

### 📝 API Specification
❓ [Notion API Specification](https://www.notion.so/API-82ef1d72abf34a63be13e9277bde034d)
<p align="center">
  <img src="https://github.com/user-attachments/assets/5cb03bd6-ffe1-43cb-a440-a7d9a082e097" width="600"/>
</p>
<br/>

### 📂 Directory Structure

    src
	├── main
	│   ├── java
	│   │   └── com
	│   │       └── api
    │   │           └── pickle  
	│   │                ├── domain
	│   │                │   ├── album
	│   │                │   │   ├── api
	│   │                │   │   ├── application
	│   │                │   │   ├── dao
	│   │                │   │   ├── domain
	│   │                │   │   └── dto
	│   │                │   ├── auth
	│   │                │   ├── bookmark
	│   │                │   ├── common
	│   │                │   ├── image
	│   │                │   ├── imagetag
	│   │                │   ├── member
	│   │                │   ├── membertag
	│   │                │   ├── participant
	│   │                │   ├── sharedalbum
	│   │                │   └── tag
	│   │                ├── global
	│   │                │   ├── common
	│   │                │   │    ├── constants
	│   │                │   │    └── response
	│   │                │   └── config
	│   │                │   │    ├── feign
	│   │                │   │    ├── querydsl
	│   │                │   │    ├── security
	│   │                │   │    └── swagger
	│   │                │   ├── common
	│   │                │   │    └── exception
	│   │                │   ├── security
	│   │                │   └── util
	│   │                ├── infra
	│   │                │   ├── fastapi
	│   │                │   ├── fcm
	│   │                │   ├── feign
	│   │                │   ├── jwt
	│   │                │   ├── oauth
	│   │                │   ├── properties
	│   │                │   ├── redis
	│   │                │   └── s3
	│   └── resources
	│       ├── application.yml
	│       ├── application-dev.yml
	│       ├── application-prod.yml
	│       ├── application-redis.yml
	│       ├── application-s3.yml
	│       ├── application-fastapi.yml
	│       └── application-security.yml

## 4️⃣ Convention

### 🌳 Commit Convention

```
[prefix]/이슈번호-이슈내용
```

| prefix   | definition             |
|----------|------------------------|
| feat     | 새로운 기능을 추가할 경우         |
| fix      | 기능을 수정하는 경우            |
| chore    | 프로젝트를 설정하는 경우          |
| bug      | 기능에 오류가 발생하여 수정하는 경우   |
| hotfix   | 기능 수정을 긴급하게 진행하는 경우    |
| refactor | 코드를 리팩토링 하는 경우         |
| docs     | 프로젝트 관련 문서 작업을 진행하는 경우 |
| test     | 테스트 코드를 작성하는 경우        |

### 🔖 Branch Strategy
- `main` : 배포 서버와 연결되는 브랜치
- `develop` : 개발이 완료된 최신 브랜치
- `feature` : 기능 개발이 각자 진행되는 브랜치
- `hotfix` : 배포 서버에서 발생한 버그를 수정하는 브랜치