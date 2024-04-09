package jpa.commerce.service;

import jpa.commerce.domain.*;
import jpa.commerce.domain.product.Product;
import jpa.commerce.repository.MemberRepository;
import jpa.commerce.repository.OrderRepository;
import jpa.commerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    /**
     * 주문 등록하기
     */
    @Transactional(readOnly = false)
    public Long registOrder(Long memberId, Long ProductId, int count) {
        // 주문자, 상품을 최우선적으로 조회해서 그 정보값을 갖고 있는다.

        // Member entity 조회 - Long memberId로 조회
        Member member = memberRepository.findMemberById(memberId);

        // Product entity 조회 - Long ProductId로 조회
        Product product1 = productRepository.findProductById(ProductId);
        //Product product2 = productRepository.findProductById(ProductId);

        // OrderProduct(주문상품) 조회 및 생성
        OrderProduct orderProduct1 = OrderProduct.createOrderProduct(product1, product1.getPrice(), count);
        //OrderProduct orderProduct2 = OrderProduct.createOrderProduct(product2, product2.getPrice(), count);

//        List<OrderProduct> orderProducts = new ArrayList<>();
//        for (OrderProduct orderProduct : orderProducts) {
//            orderProducts.add(orderProduct);
//        }

        // Delivery(배송정보) 조회 및 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setDeliveryStatus(DeliveryStatus.READY);

        // Order(주문) 정보 생성
        Order order = Order.createOrder(member, delivery, orderProduct1);
        //Order order = Order.createOrder(member, delivery, orderProduct1, orderProduct2);
        // Order.createOrder(member, delivery, orderProducts); //List<> 형태 파라미터 전달은 ...문법으로 안됨, 개별 전달만 가능)

        // 생성한 정보들을 가지고 최종 주문 등록
        orderRepository.regist(order);
        return order.getId();
    }

    /**
     * 주문 취소하기
     * orderId 값으로 특정 주문 조회한 다음, 미리 정의해놓은 메소드 활용하여 주문 취소
     * @Transactional 기반으로 돈다.
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        Order findOrder = orderRepository.findOrderById(orderId);
        findOrder.orderCancel();
    }

    /**
     * 주문 검색하기
     */
    public List<Order> findOrdersBySearchOption(SearchOption searchOption) {
        return orderRepository.findAllOrders(searchOption);
    }

}
