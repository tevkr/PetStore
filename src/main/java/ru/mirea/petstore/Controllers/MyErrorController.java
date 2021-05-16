package ru.mirea.petstore.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mirea.petstore.Models.Pet;
import ru.mirea.petstore.Models.User;
import ru.mirea.petstore.Services.PetService;
import ru.mirea.petstore.Services.UserService;

import java.util.Comparator;

@Controller
public class MyErrorController implements ErrorController {
    private final PetService petService;
    private final UserService userService;

    @Autowired
    public MyErrorController(PetService petService,
                             UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }
    private String getUserRole(Authentication authentication) {
        if (authentication == null)
            return "GUEST";
        else
            return ((User)userService.loadUserByUsername(authentication.getName())).getRole();
    }

    @RequestMapping("/error")
    public String handleError(Authentication authentication, Model model) {
        model.addAttribute("userRole", getUserRole(authentication));
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        return "error";
    }
    @Override
    public String getErrorPath() {
        return null;
    }
}
