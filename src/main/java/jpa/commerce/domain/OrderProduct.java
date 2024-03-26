package jpa.commerce.domain;

import jakarta.persistence.*;
import jpa.commerce.domain.product.Product;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "order_product")
public class OrderProduct {

    @Id
    @GeneratedValue
    @Column(name = "order_product_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

}
