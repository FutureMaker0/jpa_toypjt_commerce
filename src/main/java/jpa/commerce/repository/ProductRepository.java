package jpa.commerce.repository;

import jakarta.persistence.EntityManager;
import jpa.commerce.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final EntityManager em;

    // 상품등록
    public void regist(Product product) {
        if (product.getId() == null) {
            em.persist(product);
        } else {
            em.merge(product);
        }
    }

    // id 값으로 개별 상품 조회
    public Product findProductById(Long id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAllProducts() {
        return em.createQuery(
                "select p from Product p"
        ).getResultList();
    }

}
