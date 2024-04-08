package jpa.commerce;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpa.commerce.domain.*;
import jpa.commerce.domain.product.Concert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitializeDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInitialize1();
        initService.dbInitialize2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInitialize1() {

            Member member = createMember("m1", "한국", "부산", "12345");
            em.persist(member);

            Concert concert1 = createConcert("cn1", 10000, 100, "d1", "a1");
            em.persist(concert1);
            Concert concert2 = createConcert("cn2", 20000, 200, "d2", "a2");
            em.persist(concert2);

            Delivery delivery = createDelivery(member);

            OrderProduct orderProduct1 = OrderProduct.createOrderProduct(concert1, 10000, 10);
            OrderProduct orderProduct2 = OrderProduct.createOrderProduct(concert2, 20000, 20);

            Order order = Order.createOrder(member, delivery, orderProduct1, orderProduct2);
            em.persist(order);
        }

        public void dbInitialize2() {

            Member member = createMember("m2", "미국", "LA", "98765");
            em.persist(member);

            Concert concert3 = createConcert("cn3", 30000, 300, "d3", "a3");
            em.persist(concert3);
            Concert concert4 = createConcert("cn4", 40000, 400, "d4", "a4");
            em.persist(concert4);

            Delivery delivery = createDelivery(member);

            OrderProduct orderProduct3 = OrderProduct.createOrderProduct(concert3, 30000, 30);
            OrderProduct orderProduct4 = OrderProduct.createOrderProduct(concert4, 40000, 40);

            Order order = Order.createOrder(member, delivery, orderProduct3, orderProduct4);
            em.persist(order);
        }

        private Member createMember(String name, String country, String city, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(country, city, zipcode));
            return member;
        }

        private Concert createConcert(String name, int price, int stockQuantity, String director, String actor) {
            Concert concert = new Concert();
            concert.setName(name);
            concert.setPrice(price);
            concert.setStockQuantity(stockQuantity);
            concert.setDirector(director);
            concert.setActor(actor);
            return concert;
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

    }
}
