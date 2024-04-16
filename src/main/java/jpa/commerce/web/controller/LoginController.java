package jpa.commerce.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
public class LoginController {

    @RequestMapping("/loginHome")
    public String loginHome() {
        log.info("loginHome controller");
        return "loginHome";
    }
}
