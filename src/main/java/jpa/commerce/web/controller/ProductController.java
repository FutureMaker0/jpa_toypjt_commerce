package jpa.commerce.web.controller;

import jpa.commerce.domain.product.Book;
import jpa.commerce.domain.product.Concert;
import jpa.commerce.domain.product.Product;
import jpa.commerce.domain.product.UploadFile;
import jpa.commerce.file.FileStore;
import jpa.commerce.service.ProductService;
import jpa.commerce.web.form.ConcertDataForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final FileStore fileStore;

    @GetMapping("/products/regist")
    public String createForm(Model model) {
        model.addAttribute("concertDataForm", new ConcertDataForm());
        return "products/createProductForm";
    }

    @PostMapping("/products/regist")
    public String createProduct(@Validated ConcertDataForm concertDataForm,
                                BindingResult errorResult,
                                RedirectAttributes redirectAttributes) throws IOException {
        if (errorResult.hasErrors()) {
            return "products/createProductForm";
        }

        UploadFile uploadFile = fileStore.storeFile(concertDataForm.getUploadFile());
//        List<UploadFile> uploadFileList = fileStore.storeFiles(concertDataForm.getUploadFileList());

        Concert concert = new Concert();
        concert.setName(concertDataForm.getName());
        concert.setPrice(concertDataForm.getPrice());
        concert.setStockQuantity(concertDataForm.getStockQuantity());
        concert.setDirector(concertDataForm.getDirector());
        concert.setActor(concertDataForm.getActor());
        concert.setUploadFile(uploadFile);
//        concert.setUploadFileList(uploadFileList);

        productService.registProduct(concert);

//        redirectAttributes.addAttribute("productId", concert.getId());
//        return "redirect:/products/{productId}";
        return "redirect:/products";
    }

    @GetMapping("/products")
    public String productList(Model model) {
        List<Product> allProducts = productService.findAllProducts();
        model.addAttribute("allProducts", allProducts);
        return "products/productList";
    }

    @GetMapping("/products/{productId}")
    public String productDetail(@PathVariable("productId") Long productId, Model model) {
        Concert findProduct = (Concert) productService.findProductById(productId);
        model.addAttribute("findProduct", findProduct);

        return "products/productDetail";
    }

    //== 첨부파일 다운로드 로직 ==//
    @GetMapping("/download/{productId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("productId") Long productId) throws MalformedURLException {
        Concert findProduct = (Concert) productService.findProductById(productId);
        log.info("product name =", findProduct.getName());

        String storeFileName = findProduct.getUploadFile().getStoreFileName();
        String uploadFileName = findProduct.getUploadFile().getUploadFileName();

        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(storeFileName));
        log.info("uploadFileName =", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
    }

    @GetMapping("/products/{productId}/edit")
    public String updateForm(@PathVariable("productId") Long productId, Model model) {
        Concert findProduct = (Concert) productService.findProductById(productId); //형변환
        log.info("findProduct name =", findProduct.getName());

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
    public String updateProduct(@PathVariable("productId") Long productId,
                                @Validated ConcertDataForm concertDataForm,
                                BindingResult errorResult) {
        if (errorResult.hasErrors()) {
            return "products/updateProductForm";
        }
        productService.updateProduct(productId,
                concertDataForm.getName(),
                concertDataForm.getPrice(),
                concertDataForm.getStockQuantity(),
                concertDataForm.getDirector(),
                concertDataForm.getActor());
        return "redirect:/products";
    }


}
