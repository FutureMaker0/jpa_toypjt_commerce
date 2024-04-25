package jpa.commerce.web.controller;

import jpa.commerce.domain.product.Concert;
import jpa.commerce.domain.product.Product;
import jpa.commerce.domain.product.UploadFile;
import jpa.commerce.file.FileStore;
import jpa.commerce.service.ProductService;
import jpa.commerce.web.form.ConcertDataForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;
    private final FileStore fileStore;

    @Value("${file.dir}")
    private String fileDir;

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
        List<UploadFile> imageFileList = fileStore.storeFiles(concertDataForm.getImageFileList());

        Concert concert = new Concert();
        concert.setName(concertDataForm.getName());
        concert.setPrice(concertDataForm.getPrice());
        concert.setStockQuantity(concertDataForm.getStockQuantity());
        concert.setDirector(concertDataForm.getDirector());
        concert.setActor(concertDataForm.getActor());
        concert.setUploadFile(uploadFile);
        concert.setImageFileList(imageFileList);

        productService.registProduct(concert);

        //redirectAttributes.addAttribute("productId", concert.getId());
        //return "redirect:/products/{productId}";

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

    //== 업로드 이미지파일들 깨짐(엑박) 없이 확인하기 위한 로직 ==//
    @ResponseBody //이미지 파일의 경우 http와의 통신을 위해 어노테이션 필요
    @GetMapping("/images/{imageName}")
    public Resource downloadImage(@PathVariable("imageName") String imageName) throws MalformedURLException {
        UrlResource resource = new UrlResource("file:" + fileStore.getFullPath(imageName));
        return resource;
    }

    @GetMapping("/products/{productId}/edit")
    public String updateForm(@PathVariable("productId") Long productId,
                             Model model) {
        Concert findProduct = (Concert) productService.findProductById(productId); //형변환

        //UploadFile uploadFile = findProduct.getUploadFile();
        //List<UploadFile> imageFileList = findProduct.getImageFileList();

        ConcertDataForm concertDataForm = new ConcertDataForm();
        concertDataForm.setName(findProduct.getName());
        concertDataForm.setPrice(findProduct.getPrice());
        concertDataForm.setStockQuantity(findProduct.getStockQuantity());
        concertDataForm.setDirector(findProduct.getDirector());
        concertDataForm.setActor(findProduct.getActor());
        //concertDataForm.setUploadFile(uploadFile);
        //concertDataForm.setImageFileList(imageFileList);

        model.addAttribute("concertDataForm", concertDataForm);
        return "products/updateProductForm";

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
                //concertDataForm.getUploadFile());
                //concertDataForm.getImageFileList());

        return "redirect:/products";
    }

}
