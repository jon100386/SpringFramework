package com.company.eventos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class HomeWebController {

    @GetMapping("/Home")
    public String home() {
        return "Home";
    }
}
