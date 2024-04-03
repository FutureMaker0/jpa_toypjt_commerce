package jpa.commerce.service;

import jpa.commerce.domain.product.Concert;
import jpa.commerce.domain.product.Food;
import jpa.commerce.domain.product.Product;
import jpa.commerce.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ProductServiceTest {

    @Autowired // 테스트 코드에서는 private final 선언 안됨 - @RequiredArgsConstructor 적용 불가.
    ProductRepository productRepository;
    @Autowired
    ProductService productService;

    @Test
    public void 테스트_상품등록() throws Exception {
        //given
        Concert concert1 = new Concert();
        concert1.setDirector("d1");
        concert1.setActor("a1");

        Concert concert2 = new Concert();
        concert2.setDirector("d2");
        concert2.setActor("a2");

        Food food = new Food();
        food.setBrand("b1");
        food.setEtc("e1");

        //when
        productService.registProduct(concert1);
        productService.registProduct(concert2);
        productService.registProduct(food);

        //then
        assertEquals("등록된 상품 종류 수가 정확해야 한다.", 3, productRepository.findAllProducts().size());
    }

    @Test
    public void 테스트_중복상품_검증() throws Exception {
        //given
        Product concert1 = new Concert();
        concert1.setName("c1");

        Product concert2 = new Concert();
        concert2.setName("c1");

        //when
        productService.registProduct(concert1);
        productService.registProduct(concert2);

        //then
        fail("테스트 검증에 실패하고 예외가 발생해야 한다. 아직 커스텀 예외 정의하지 않음.");
    }

}