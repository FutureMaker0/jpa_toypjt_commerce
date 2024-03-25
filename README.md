# spring_web_service (spring jpa 기반 커머스 웹사이트 개발)
jpa 기반 커머스 웹 애플리케이션 토이 프로젝트 리포지토리

## 엔티티
  - Member
  - Product(pc, labtop, tablet)
  - Category
  - Order
  - Delivery
  - Address(Value Type)
  - Etc

## 엔티티 기능 요구사항
  - Member
    - CREATE(regist())
    - READ(list())
  - Product
    - CREATE(regist())
    - UPDATE(edit())
    - READ(list())
    - DELETE(destroy()) : 추후
  - Order
    - CREATE(regist())
    - READ
      - findOne() : 1개 프로덕트 조회
      - findAll() : 모든 프로덕트 조회
    - READ(edit()) : 추후
    - DELETE(destroy())
  - 기타 요구사항
    - 상품은 재고 관리를 해야한다. 주문 및 주문취소 시 재고 수량 조정
    - 상품은 카테고리로 분류할 수 있다.
    - 상품의 종류는 pc, labtop, tablet 이 있다.
    - 상품 주문 시 배송 정보를 입력할 수 있다.
   
## 도메인 모델과 테이블 간단 설계
|순번|이미지|비고|
|---|-----|---|
|1|<img width="500" alt="스크린샷 2024-03-25 오후 4 05 18" src="https://github.com/FutureMaker0/django_webex/assets/120623320/b50d307c-7e30-4c20-8fe9-48e06fb276bb">||

### 간단 설명
  - N:N 매핑 관계 내용은 생략했으며, 추후 상세 표기 예정
  - 상품 분류: 상품은 pc, labtop, tablet으로 분류되는데 상품이라는 공통 속성을 사용하므로 상속 구조로 표현
  - 주문<>상품은 N:N 관계이나 잘 사용하지 않는 경우이므로, 중간에 주문상품이라는 엔티티를 두어 N:N을 1:N, N:1로 풀어냄

## 회원 엔티티 분석
|순번|이미지|비고|
|---|-----|---|
|1|<img width="600" alt="스크린샷 2024-03-25 오후 5 08 39" src="https://github.com/FutureMaker0/django_webex/assets/120623320/e88026dd-9d68-4a36-b6cd-7161b4084c32">||



  
