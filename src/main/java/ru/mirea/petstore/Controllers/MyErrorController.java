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

/**
 * Контроллер для страницы с ошибками
 * @author Яновский Владислав
 */
@Controller
public class MyErrorController implements ErrorController {
    /**
     * Сервис для питомцев
     */
    private final PetService petService;
    /**
     * Сервис для пользователей
     */
    private final UserService userService;

    /**
     * Конструктор для контроллера ошибок
     * @param petService Сервис для питомцев
     * @param userService Сервис для пользователей
     */
    @Autowired
    public MyErrorController(PetService petService,
                             UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }
    /**
     * Метод используется для получения роли пользователя по его идентифицирующему объекту
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @return Возвращает роль пользователя
     */
    private String getUserRole(Authentication authentication) {
        if (authentication == null)
            return "GUEST";
        else
            return ((User)userService.loadUserByUsername(authentication.getName())).getRole();
    }

    /**
     * Метод слушающий все GET запросы по /error
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с ошибкой
     */
    @RequestMapping("/error")
    public String handleError(Authentication authentication, Model model) {
        model.addAttribute("userRole", getUserRole(authentication));
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        return "error";
    }

    /**
     * Переопределенный медод getErrorPath.
     * Начиная с версии 2.3.x, Spring boot этот метод устарел.
     * @return Возвращает null
     */
    @Override
    public String getErrorPath() {
        return null;
    }
}
