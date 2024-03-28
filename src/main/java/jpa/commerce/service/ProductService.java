package jpa.commerce.service;

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
        productRepository.regist(product);
    }

    /**
     * 전체 상품 조회
     */
    public List<Product> findAllProducts() {
        return productRepository.findAllProducts();
    }

    /**
     * 개별 상품 조회 - id값 활용
     */
    public Product findProductById(Long productId) {
        return productRepository.findProductById(productId);
    }

}
