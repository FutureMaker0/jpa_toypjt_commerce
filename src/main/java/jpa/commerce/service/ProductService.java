package jpa.commerce.service;

import jpa.commerce.domain.Category;
import jpa.commerce.domain.product.Concert;
import jpa.commerce.domain.product.Product;
import jpa.commerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 기본 옵션은 readOnly=true 로 하여 최소한의 성능향상
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * 상품 등록
     */
    @Transactional
    public void registProduct(Product product) {
        //isDuplicateProduct(product);
        productRepository.regist(product);
    }

//    private void isDuplicateProduct(Product product) {
//        List<Product> findProducts = productRepository.findProductByName(product.getName());
//        if (!findProducts.isEmpty()) {
//            throw new IllegalStateException("이미 등록된 상품입니다.");
//        }
//    }

    /**
     * 개별 상품 조회 - id값 활용
     */
    public Product findProductById(Long productId) {
        return productRepository.findProductById(productId);
    }

    /**
     * 전체 상품 조회
     */
    public List<Product> findAllProducts() {
        return productRepository.findAllProducts();
    }

    @Transactional
    public void updateProduct(Long productId, String name, int price, int stockQuantity, String director, String actor) {
        Concert product = (Concert) productRepository.findProductById(productId);
        product.setName(name);
        product.setPrice(price);
        product.setStockQuantity(stockQuantity);
        product.setDirector(director);
        product.setActor(actor);
    }
}
