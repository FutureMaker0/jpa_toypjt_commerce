package jpa.commerce.web.controller;

import jpa.commerce.domain.product.Book;
import jpa.commerce.domain.product.Concert;
import jpa.commerce.domain.product.Product;
import jpa.commerce.service.ProductService;
import jpa.commerce.web.form.ConcertDataForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products/regist")
    public String createForm(Model model) {
        model.addAttribute("concertDataForm", new ConcertDataForm());
        return "products/createProductForm";
    }

    @PostMapping("/products/regist")
    public String createProduct(@Validated ConcertDataForm concertDataForm, BindingResult errorResult) {
        if (errorResult.hasErrors()) {
            return "products/createProductForm";
        }
        Concert concert = new Concert();
        concert.setName(concertDataForm.getName());
        concert.setPrice(concertDataForm.getPrice());
        concert.setStockQuantity(concertDataForm.getStockQuantity());
        concert.setDirector(concertDataForm.getDirector());
        concert.setActor(concertDataForm.getActor());

        productService.registProduct(concert);
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String productList(Model model) {
        List<Product> allProducts = productService.findAllProducts();
        model.addAttribute("allProducts", allProducts);
        return "products/productList";
    }

    @GetMapping("/products/{productId}/edit")
    public String updateForm(@PathVariable("productId") Long productId, Model model) {
        Concert findProduct = (Concert) productService.findProductById(productId); //형변환
        ConcertDataForm concertDataForm = new ConcertDataForm();
        concertDataForm.setName(findProduct.getName());
        concertDataForm.setPrice(findProduct.getPrice());
        concertDataForm.setStockQuantity(findProduct.getStockQuantity());
        concertDataForm.setDirector(findProduct.getDirector());
        concertDataForm.setActor(findProduct.getActor());

        model.addAttribute("concertDataForm", concertDataForm);
        return "products/UpdateProductForm";
    }

    @PostMapping("/products/{productId}/edit")
    public String updateProduct(@PathVariable("productId") Long productId, @ModelAttribute("concertDataForm") ConcertDataForm concertDataForm) {
        productService.updateProduct(productId,
                concertDataForm.getName(),
                concertDataForm.getPrice(),
                concertDataForm.getStockQuantity(),
                concertDataForm.getDirector(),
                concertDataForm.getActor());
        return "redirect:/products";
    }


}
