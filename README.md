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
   

