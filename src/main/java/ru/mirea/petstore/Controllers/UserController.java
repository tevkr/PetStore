package ru.mirea.petstore.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class UserController {
    @GetMapping
    public String index() {
        return "UserController/index";
    }
    @GetMapping("/about")
    public String about() {
        return "UserController/about";
    }
}
