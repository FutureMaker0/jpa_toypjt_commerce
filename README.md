# spring_web_service (spring jpa 기반 커머스 웹사이트 개발)
jpa 기반 커머스 웹 애플리케이션 토이 프로젝트 리포지토리

## 엔티티
  - Member
  - Product(Food, Book, Concert)
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
  - 기타 요구사항(기본)
    - 상품은 재고 관리를 해야한다. 주문 및 주문취소 시 재고 수량 조정
    - 상품은 카테고리로 분류할 수 있다.
    - 상품의 종류는 Food, Book, Concert 가 있다.
    - 상품 주문 시 배송 정보를 입력할 수 있다.
  - 기타 요구사항(심화)
    - 로그인 및 권한 부여
    - 파라미터 Validation 및 Exception 예외 처리
   
## 도메인 모델과 테이블 간단 설계
|순번|이미지|비고|
|:---:|:-----:|:---:|
|1|<img width="500" alt="스크린샷 2024-03-25 오후 4 05 18" src="https://github.com/FutureMaker0/django_webex/assets/120623320/b50d307c-7e30-4c20-8fe9-48e06fb276bb">||

### 간단 설명
  - N:N 매핑 관계 내용은 생략했으며, 추후 상세 표기 예정
  - 상품 분류: 상품은 Food, Book, Concert 로 분류되는데 상품이라는 공통 속성을 사용하므로 상속 구조로 표현
  - 주문<>상품은 N:N 관계이나 잘 사용하지 않는 경우이므로, 중간에 주문상품이라는 엔티티를 두어 N:N을 1:N, N:1로 풀어냄

## 엔티티 분석
|순번|이미지|비고|
|:---:|:-----:|:---:|
|1|<img width="500" alt="스크린샷 2024-03-25 오후 5 08 39" src="https://github.com/FutureMaker0/django_webex/assets/120623320/e88026dd-9d68-4a36-b6cd-7161b4084c32">||

- Member(회원): name, address, List<Orders> 를 가진다.
- Order(주문): 주문과 상품(주문상품)은 일대다 관계다. 주문은 주문의 주체인 회원정보, 배송정보, 주문날짜, 주문상태를 데이터를 가진다. 주문상태는 ENUM 타입으로 정의하며 [ORDER, CANCEL] 두 가지 상태를 가진다.
- OrderProduct(주문상품): 주문된 상품정보, 주문총금액(orderPrice), 주문수량(count) 정보를 가진다.
- Product(상품): 상품이름, 상품가격, 재고수량을 가진다. 주문이 발생하면 상품의 수량이 감소하며, 취소하면 원복된다. 상품의 종류로 Food, Book, Concert가 있고 가지는 속성은 다 다르다.
- Delivery(배송): 주문과 배송은 일대일 관계이다. 주문 하나 당 하나의 배송정보를 가진다.(물론 그렇지 않을 수도 있지만 당 프로젝트에서는 이와 같이 정의한다.)
- Category(카테고리): 상품과 다대다 관계이다. 실제 다대다 관계는 많이 사용되지 않고 분리시켜주는 것이 좋으나, 당 프로젝트에서는 그대로 사용한다. parent, child 를 사용하여 부모, 자식 카테고리를 연결한다.
- Address(주소): value type(임베디드 타입)으로 정의한다. 회원의 주소, 배송을 위한 주소 등에 활용하기 위해 정의한다.

## 테이블 분석 - 실제 DB에 생성될 테이블과 인스턴스
|순번|이미지|비고|
|:---:|:-----:|:---:|
|1|<img width="500" alt="스크린샷 2024-03-26 오전 11 58 02" src="https://github.com/FutureMaker0/django_webex/assets/120623320/71543f72-0ffa-44ea-bf46-2ba60bcef8ed">||

- Member(회원): 임베디드 타입 address 정보가 Member, Delivery 테이블에 포함되었다.
- Product(상품): Food, Book, Concert 세 가지 타입을 하나의 테이블로 통합했다. 이들의 구분은 DTYPE을 통해 한다.

### 연관관계 맵핑 분석
  - Member <> Order: 회원<>주문(일대다), 주문<>회원(다대일) 의 양방향 관계를 가진다. 이 경우 연관관계의 주인은 FK를 가진 주문으로 설정하는 것이 좋다. 이에 따라 Order.member 객체를 테이블 orders.member_id 외래 키와 맵핑한다.
  - OrderProduct <> Order: 주문상품<>주문은 다대일 양방향 관계이다. 외래키가 주문상품에 있으므로 연관관계의 주인이다. 이에 따라 OrderProduct.order를 order_product.order_id 외래키와 맵핑한다.
  - OrderProduct <> Product: 다대일 단방향 관계이다. OrderProduct.item을 테이블 orders.item_id 외래키와 맵핑한다.
    - 다대일 관계(Many-To-One): 여러 주문상품이 하나의 상품에 맵핑될 수 있다. 즉, 하나의 상품은 여러 주문상품과 연결될 수 있다. 주문상품 엔티티가 상품 엔티티를 참조하는데 사용되는 관계이다.
    - 외래키 연결: 주문상품 엔티티가 주문 아이디와 상품 아이디를 각각 외래키로 가진다는 것은, 주문상품이 특정 주문과 특정 상품에 속함을 의미한다.
    - 단방향 관계: 주문상품 엔티티가 상품 엔티티를 참조하는 단방향 관계이므로, 주문상품 엔티티를 통해 특정 주문의 상품 정보를 얻을 수 있다.
  - Order <> Delivery: 일대일 양방향 관계이다. 주문 하나당 하나의 배송정보를 가지며, 반대로 배송정보는 하나의 주문정보만을 가진다. Order.delivery를 테이블 orders.delivery_id 외래키와 맵핑한다.
  - Category <> Product: 다대다 관계이다. 여러 카테고리와 상품이 서로 맵핑될 수 있다. 하나의 카테고리에 여러 상품이 있을 수 있고, 하나의 상품이 여러 카테고리에 속해있을 수 있다. 일대다/다대일로 분리하는 것이 좋으나 당 프로젝트에서는
                          예시의 다양성을 위해 그대로 활용한다.

#### 연관관계 주인 설정과 관련된 참고사항
  - 연관관계의 주인이라는 것은, 외래 키를 어느 쪽에서 관리하느냐의 문제이다. 좀 더 접근을 편하고 효율적으로 하기 위해 많이 가진 쪽이 주인을 맡는 개념이다.
  - 이처럼 해야 유지보수가 쉽고, 향후 불필요한 업데이트 쿼리 등으로 인한 성능 저하를 최소화할 수 있다.

#### 연관관계 메서드
  - 연관관계 메서드는 주로 양방향 연관관계를 관리하기 위해 사용된다. 양방향 연관관계에서는 서로를 참조하는 두 엔티티 간에 데이터 동기화가 필요한데 이를 위해 구성한다.
  - Order <> Member:
  - Order <> OrderProduct:
  - Order <> Delivery: 

#### 상속구조 관련 테이블 맵핑 전략 (Product: Food, Book, Concert)
  @Inheritance(strategy = InheritanceType.SINGLE_TABLE):
    - 상속 구조를 단일 테이블로 매핑할 때 사용
    - 모든 서브 클래스의 필드들을 한 테이블에 통합하여 저장
    - 이 테이블에는 구분 컬럼(discriminator column)이 포함되어 있어, 어떤 클래스의 데이터인지를 식별
    - 장점은 테이블 간의 조인이 필요 없어 성능이 좋으며, 단순한 구조를 가짐. 하지만, 데이터가 많아지면 성능에 영향을 줄 수 있음
  
  @DiscriminatorColumn(name = "dtype"):
    - 단일 테이블 전략을 사용할 때, 부모 클래스와 서브 클래스를 구분하는 역할
    - "dtype" 컬럼을 통해 각 레코드가 어떤 서브 클래스에 속하는지를 구분
    - 부모 클래스와 서브 클래스 간의 차이점을 저장, 서브 클래스마다 고유한 속성이 있을 때 사용
    - 이를 통해 JPA가 올바른 서브 클래스를 인스턴스화하고 매핑할 수 있도록 해줌
    - 이러한 어노테이션들을 사용하여 단일 테이블 전략을 적용하면, 하나의 테이블에 모든 클래스의 데이터가 저장되고, 이를 구분하기 위해 discriminator 컬럼이 사용됨. 상속 구조를 데이터베이스에 매핑하는 일반적인 방법

#### 다대다 관계 맵핑
  - @JoinTable 어노테이션은 카테고리와 제품 사이의 관계를 매핑하기 위해 사용. 이 관계는 'category_product'라는 중간 테이블을 통해 정의
  - name = "category_product": 중간 테이블의 이름을 지정. 이 테이블은 카테고리와 제품의 관계를 저장
  - joinColumns = @JoinColumn(name = "category_id"): 중간 테이블에서 카테고리를 참조하는 외래 키 칼럼. 여기서 'category_id'는 중간 테이블의 카테고리를 참조하는 컬럼
  - inverseJoinColumns = @JoinColumn(name = "product_id"): 중간 테이블에서 제품을 참조하는 외래 키 칼럼. 여기서 'product_id'는 중간 테이블의 제품을 참조하는 컬럼
  - 이렇게 설정하면 카테고리와 제품 간의 다대다 관계를 중간 테이블을 통해 매핑. 이러한 관계 설정은 JPA에서 다대다 관계를 효과적으로 매핑하는 방법

#### value type(임베디드 타입) 생성자 관련 주의사항 (Address)
  - Address 클래스가 @Embeddable로 표시되어 있으므로 다른 엔티티의 일부로 사용될 수 있습니다. 그러므로 JPA는 Address 클래스의 기본 생성자를 호출하여 임베디드 인스턴스를 만들 수 있어야 합니다. 
  이러한 이유로 Address 클래스에 매개변수 없는 기본 생성자가 필요하다.
  - 추가적으로, protected로 기본 생성자를 만들어 줌으로써 다른 패키지에 있는 하위 클래스에서만 접근 가능하도록 제한한다. 이를 통해 해당 클래스가 다른 패키지에서 무분별하게 사용되는 것을 방지할 수 있다.

### 엔티티 설계 시 고려사항
  - @Setter는 향후 유지보수성을 위해 무분별하게 사용하지 않는 것이 좋지만, 당 프로젝트에서는 효율성을 위해 사용
  - __ToOne(OneToOne, ManyToOne) 관계는 디폴트 값으로 즉시로딩하므로, 직접 설정을 통해 지연로딩(FetchType.LAZY)로 설정해준다.
  - cascade = Cascade.ALL 옵션을 통해 특정 엔티티가 persist 되면, 하위에 들어가 있는 개념들은 자동으로 함께 persist가 되도록 한다. 향후 리포지토리 개발에서 메소드 중복 호출을 방지할 수 있다.(최상위 엔티티만 해주면 된다.)
    - cascade 옵션은 상위 엔티티 클래스 내부에서 하위 인스턴스 선언 시 그 위에 어노테이션 옵션으로 넣는다. ex) Order, OrderProduct 가 있으면 List<OrderProduct> orderproducts 위에 옵션으로 넣는다. 
  - null exception 방지를 위해 collection은 필드에서 바로 초기화한다.

### Application Architeuture
|순번|이미지|비고|
|:---:|:-----:|:---:|
||<img width="500" alt="스크린샷 2024-03-27 오후 3 14 03" src="https://github.com/FutureMaker0/django_webex/assets/120623320/92179343-f546-4e54-af27-699df9ae8eb3">||








  
