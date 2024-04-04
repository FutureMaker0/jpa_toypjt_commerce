package jpa.commerce.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpa.commerce.domain.Address;
import jpa.commerce.domain.Member;
import jpa.commerce.domain.Order;
import jpa.commerce.domain.OrderStatus;
import jpa.commerce.domain.product.Concert;
import jpa.commerce.domain.product.Product;
import jpa.commerce.exception.StockUnderZeroException;
import jpa.commerce.repository.OrderRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//@Rollback(value = false)
public class OrderServiceTest {

    @PersistenceContext EntityManager em;
    //@Autowired EntityManager em;

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;

    @Test
    public void 테스트_상품주문() throws Exception {
        //given - 회원정보, 상품정보, 수량정보
        Member member = createMember();
        Product product = createConcert("cn1", 100, 10000); //콘서트명, 재고, 가격
        int orderCount = 5;

        //when
        Long orderId = orderService.registOrder(member.getId(), product.getId(), orderCount);

        //then
        Order findOrder = orderRepository.findOrderById(orderId);

        /**
         * 테스트를 몇 개 더해보자.
         */
        assertEquals("두 객체가 같은지 테스트", findOrder, orderRepository.findOrderById(orderId));
        assertEquals("상품주문 시 상태가 디폴트 값 ORDER로 셋팅되어 있는지 테스트", OrderStatus.ORDER, findOrder.getOrderStatus());
        assertEquals("주문 시 선택한 주문상품의 갯수가 정확히 일치하는지 테스트", 1, findOrder.getOrderProducts().size());
        assertEquals("주문 가격이 정확히 일치하는지 테스트", 50000, findOrder.getTotalPrice());
        assertEquals("주문된 수량만큼 전체 재고에 반영되어 빠지는지 테스트", 95, product.getStockQuantity());

    }

    @Test(expected = StockUnderZeroException.class)
    public void 테스트_상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Product product = createConcert("cn1", 100, 10000); //콘서트명, 재고, 가격
        int orderQuantity = 101;

        //when
        orderService.registOrder(member.getId(), product.getId(), orderQuantity);

        //then
        fail("StockUnderZeroException 예외가 제대로 터지지 않았을 경우 fail로 내려온다.");
    }

    @Test
    public void 테스트_주문취소() throws Exception {
        //given
        Member member = createMember();
        Product product = createConcert("cn1", 100, 10000); //콘서트명, 재고, 가격
        int orderQuantity = 99;

        Long orderId = orderService.registOrder(member.getId(), product.getId(), orderQuantity);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order findOrder = orderRepository.findOrderById(orderId);

        assertEquals("주문취소 후 주문 상태가 CANCEL 인지 테스트", OrderStatus.CANCEL, findOrder.getOrderStatus());
        assertEquals("주문취소 후 재고 상태가 원래대로 원복 되는지 테스트", 100, product.getStockQuantity());

    }





    private Member createMember() {
        Member member = new Member();
        member.setName("m1");
        member.setAddress(new Address("한국", "부산", "12345"));
        em.persist(member);

        return member;
    }

    private Concert createConcert(String name, int stockQuantity, int price) {
        Concert concert = new Concert();
        concert.setName(name);
        concert.setStockQuantity(stockQuantity);
        concert.setPrice(price);
        em.persist(concert);

        return concert;
    }


}