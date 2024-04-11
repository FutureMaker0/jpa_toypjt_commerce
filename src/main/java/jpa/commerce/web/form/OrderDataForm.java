package jpa.commerce.web.form;

import jpa.commerce.domain.Member;
import jpa.commerce.domain.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderDataForm {

    private List<Member> members;
    private List<Product> Products;
    int count;

}
