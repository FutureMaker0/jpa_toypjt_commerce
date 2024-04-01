package jpa.commerce.repository;

import jakarta.persistence.EntityManager;
import jpa.commerce.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void regist(Order order) {
        em.persist(order);
    }

    public Order findOrderById(Long id) {
        return em.find(Order.class, id);
    }

//    public List<Order> findAllOrders(SearchOption searchOption) {
//        return null;
//    }

}
