package jpa.commerce.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jpa.commerce.domain.Order;
import jpa.commerce.domain.QMember;
import jpa.commerce.domain.QOrder;
import jpa.commerce.domain.SearchOption;
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

    public List<Order> findAllOrders(SearchOption searchOption) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        return query
                .select(order)
                .from(order)
                .join(order.member, member)
                .where()
                .limit(100)
                .fetch();
    }

}
