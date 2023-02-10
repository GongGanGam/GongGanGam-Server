# 마음 속 작은 창문 “공간감”


> **공간감**은 익명의 사람과 나의 다이어리 한 페이지를 공유하고 서로 소통할 수 있는 1:1 다이어리 랜덤 공유 서비스입니다.

![mainMockup](https://user-images.githubusercontent.com/89574881/158516877-bebf1e86-42fc-474e-abd2-0321773ee7db.jpeg)



## Development Server
> 🌍 Base Uri : http://18.189.150.89:8080/
> 
> 📜 API Docs : http://18.189.150.89:8080/swagger-ui/index.html



## Features

- 일기, 답장 CRUD 지원
- 사용자 정보, 공지사항, 신고 CRUD 지원
- `multipart/form-data` 이미지 업로드 지원
- JWT 기반 인증 및 인가
- 스케쥴링된 일기 공유 매칭
- 푸시 알림 - APNs, FCM (예정)

- 소셜 로그인 - Kakao, Apple



## Built With

- Lang. : Java (JDK 17)
- Web Framework
  - Spring Boot 2.7.2
  - Spring Security
  - Spring Cloud AWS

- Dependency Management : Gradle
- ORM : JPA + Hibernate
- Documentation : Swagger + SpringDoc
