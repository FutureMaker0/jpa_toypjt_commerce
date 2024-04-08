package jpa.commerce.web.controller;

import jpa.commerce.domain.Member;
import jpa.commerce.domain.product.Product;
import jpa.commerce.service.MemberService;
import jpa.commerce.service.OrderService;
import jpa.commerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final MemberService memberService;
    private final ProductService productService;
    private final OrderService orderService;

    @GetMapping("/orders/regist")
    public String createForm(Model model) {

        List<Member> allMembers = memberService.findAllMembers();
        List<Product> allProducts = productService.findAllProducts();

        model.addAttribute("allMembers", allMembers);
        model.addAttribute("allProducts", allProducts);

        return "orders/createOrderForm";
    }

    @PostMapping("/orders/regist")
    public String createOrder(@RequestParam("memberId") Long memberId,
                              @RequestParam("productId") Long productId,
                              @RequestParam("count") int count) {

        orderService.registOrder(memberId, productId, count);
        return "redirect:/orderList";
    }



}
