package ru.mirea.petstore.Controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.mirea.petstore.Models.*;
import ru.mirea.petstore.Services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Контроллер для пользователей
 * <p>
 * Данный класс обрабатывает все запросы поступающие по адресам, начимающимся с /
 * </p>
 * @author Яновский Владислав
 */
@Controller
@RequestMapping("/")
public class UserController {

    /**
     * Сервис для питомцев
     */
    private final PetService petService;
    /**
     * Сервис для товаров
     */
    private final ProductService productService;
    /**
     * Сервис для категорий товаров
     */
    private final ProductTypeService productTypeService;
    /**
     * Сервис для подкатегорий товаров
     */
    private final ProductTypeDetailedService productTypeDetailedService;
    /**
     * Сервис для пользователей
     */
    private final UserService userService;
    /**
     * Сервис для покупок/корзины
     */
    private final PurchaseService purchaseService;
    /**
     * Сервис для отправки сообщений
     */
    private final EmailService emailService;
    /**
     * Сервис для фильтрации моделей по полю
     */
    private final CriteriaService criteriaService;

    /**
     * Конструктор контроллера для пользователей
     * @param petService Сервис для питомцев
     * @param productService Сервис для товаров
     * @param productTypeService Сервис для категорий товаров
     * @param productTypeDetailedService Сервис для подкатегорий товаров
     * @param userService Сервис для пользователей
     * @param purchaseService Сервис для покупок/корзины
     * @param emailService Сервис для отправки сообщений
     * @param criteriaService Сервис для фильтрации моделей по полю
     */
    @Autowired
    public UserController(PetService petService,
                          ProductService productService,
                          ProductTypeService productTypeService,
                          ProductTypeDetailedService productTypeDetailedService,
                          UserService userService,
                          PurchaseService purchaseService,
                          EmailService emailService,
                          CriteriaService criteriaService) {
        this.petService = petService;
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.productTypeDetailedService = productTypeDetailedService;
        this.userService = userService;
        this.purchaseService = purchaseService;
        this.emailService = emailService;
        this.criteriaService = criteriaService;
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
     * Метод используется для получения идентификатора пользователя в БД по его идентифицирующему объекту
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @return Возвращает идентификатор пользователя в БД
     */
    private int getUserId(Authentication authentication) {
        if (authentication == null)
            return 0;
        else
            return ((User)userService.loadUserByUsername(authentication.getName())).getId();
    }

    /**
     * Метод используется для вычисления полной стоимоти товаров в корзине
     * @param purchases Товары добавленные в корзину
     * @return Возвращает полную стоимость товаров в корзине
     */
    private int getTotalPrice(List<Purchase> purchases) {
        int result = 0;
        for (Purchase purchase: purchases) {
            result += productService.getProductById(purchase.getProductId()).getPrice() * purchase.getProductCount();
        }
        return result;
    }

    /**
     * Метод используется для составления сообщения для отправки пользователю на почту
     * @param userPurchases Товары в корзине пользователя
     * @return Возвращает письмо для отправки пользователю
     */
    private String createMessageForUser(List<Purchase> userPurchases) {
        List<Product> userProducts = new ArrayList<>();
        for (Purchase purchase: userPurchases) {
            userProducts.add(productService.getProductById(purchase.getProductId()));
        }
        String result = "Здравствуйте, ваш заказ:<br>";
        for (int i = 0; i < userProducts.size(); i++) {
            result += (i + 1) + ") " + userProducts.get(i).getName() + " (Количество: " + userPurchases.get(i).getProductCount() + ", Стоимость: " + (userProducts.get(i).getPrice()*userPurchases.get(i).getProductCount()) + " р.)<br>";
        }
        result += "Общая стоимость: " + getTotalPrice(userPurchases) + " р.<br>";
        result += "С вами скоро свяжется менеджер, ожидайте.";
        return result;
    }

    /**
     * Метод используется для составления сообщения для отправки менеджеру на почту
     * @param user Пользователь оформивший заказ
     * @param address Адрес доставки
     * @param telephone Телефон пользователя
     * @param userPurchases Товары в корзине пользователя
     * @return Возвращает письмо для отправки менеджеру
     */
    private String createMessageForManager(User user, String address, String telephone, List<Purchase> userPurchases) {
        List<Product> userProducts = new ArrayList<>();
        for (Purchase purchase: userPurchases) {
            userProducts.add(productService.getProductById(purchase.getProductId()));
        }
        String result = "Информация о заказчике:<br>";
        result += "Почта: " + user.getEmail() + "<br>";
        result += "Имя пользователя: " + user.getUsername() + "<br>";
        result += "Телефон: " + telephone + "<br>";
        result += "Адрес: " + address + "<br>";
        result += "----------------------<br>";
        result += "Заказ:<br>";
        for (int i = 0; i < userProducts.size(); i++) {
            result += (i + 1) + ") " + userProducts.get(i).getName() + " (Количество: " + userPurchases.get(i).getProductCount() + ", Стоимость: " + (userProducts.get(i).getPrice()*userPurchases.get(i).getProductCount()) + " р.)<br>";
        }
        result += "Общая стоимость: " + getTotalPrice(userPurchases) + " р.<br>";
        return result;
    }

    /**
     * Метод слушающий все GET запросы по /
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает главную страницу
     */
    @GetMapping
    public String index(Authentication authentication, Model model) {
        model.addAttribute("userRole", getUserRole(authentication));
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        return "UserController/index";
    }

    /**
     * Метод слушающий все GET запросы по /about
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу "О сайте"
     */
    @GetMapping("/about")
    public String about(Authentication authentication, Model model) {
        model.addAttribute("userRole", getUserRole(authentication));
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        return "UserController/about";
    }

    /**
     * Метод слушающий все GET запросы по /products
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param IpetId Идентификатор питомца (Необязательный параметр)
     * @param IproductTypeId Идентификатор категории товара (Необязательный параметр)
     * @param IproductTypeDetailedId Идентификатор подкатегории товара (Необязательный параметр)
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с товарами
     */
    @GetMapping("/products")
    public String products(Authentication authentication,
                           @RequestParam(name = "pId", required = false) Integer IpetId,
                           @RequestParam(name = "pTId", required = false) Integer IproductTypeId,
                           @RequestParam(name = "pTDId", required = false) Integer IproductTypeDetailedId,
                           Model model) {
        model.addAttribute("userRole", getUserRole(authentication));
        int petId = (IpetId == null) ? 0:IpetId;
        int productTypeId = (IproductTypeId == null) ? 0:IproductTypeId;
        int productTypeDetailedId = (IproductTypeDetailedId == null) ? 0:IproductTypeDetailedId;

        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("pId", petId);
        model.addAttribute("pTId", productTypeId);
        model.addAttribute("pTDId", productTypeDetailedId);

        String petTitle = "";
        if (petService.getPetById(petId) != null)
            petTitle = petService.getPetById(petId).getName();
        model.addAttribute("petTitle", petTitle);
        model.addAttribute("productTypes", productTypeService.getProductTypesByPetId(petId));
        model.addAttribute("sortProductTypesById", Comparator.comparing(ProductType::getId));
        model.addAttribute("productTypesDetailed", productTypeDetailedService.getProductTypesDetailedByPetIdnProductTypeId(petId, productTypeId));
        model.addAttribute("sortProductTypesDetailedById", Comparator.comparing(ProductTypeDetailed::getId));
        model.addAttribute("products", productService.getProductsByIds(petId, productTypeId, productTypeDetailedId));
        model.addAttribute("sortProductsById", Comparator.comparing(Product::getId));
        return "UserController/products";
    }

    /**
     * Метод слушающий все GET запросы по /product
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param Status Статус добавления в корзину
     * @param IproductId Идентификато товара
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с конкретным товаром
     */
    @GetMapping("/product")
    public String product(Authentication authentication, @ModelAttribute("Status") final String Status, @RequestParam(name = "productId", required = false) Integer IproductId, Model model) {
        model.addAttribute("Status", Status);
        model.addAttribute("userRole", getUserRole(authentication));
        int productId = (IproductId == null) ? 0:IproductId;
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        String productDetails = productService.getProductById(productId).getProductDetails();
        String productDescription = productService.getProductById(productId).getDescription();
        String[] productDetailsArr = productDetails.split("\n");
        String[] productDescriptionArr = productDescription.split("\n");
        model.addAttribute("product", productService.getProductById(productId));
        model.addAttribute("productDetails", productDetailsArr);
        model.addAttribute("productDescription", productDescriptionArr);

        return "UserController/product";
    }

    /**
     * Метод слушающий все GET запросы по /sign
     * @return Возвращает страницу с регистрацией
     */
    @GetMapping("/sign")
    public String sign() {
        return "UserController/signup";
    }

    /**
     * Метод слушающий все POST запросы по /sign
     * @param request Объект содержащий запрос, поступивший от пользователя
     * @param email Почта пользователя
     * @param username Никнейм пользователя
     * @param password Пароль пользователя
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с регистрацией (если данные пользователя уже заняты) или главную страницу
     */
    @PostMapping("/sign")
    public String signCreate(HttpServletRequest request, @RequestParam String email, @RequestParam String username, @RequestParam String password, Model model) {
        if (userService.loadUserByUsername(username) != null) {
            model.addAttribute("Status", "user_exists");
            return "UserController/signup";
        }
        else {
            userService.create(email,username,password);
            authWithHttpServletRequest(request, username, password);
            return "redirect:/";
        }
    }

    /**
     * Метод слушающий все GET запросы по /logout, служит для выхода пользователя из системы
     * @return Возвращает главную страницу сайта
     */
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    /**
     * Метод слушающий все POST запросы по /addPurchase, служит для добавления товара в корзину
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param redirectAttributes Объект используемый для передачи flash-атрибутов
     * @param id Идентификатор товара
     * @param count Количество добавляемого в корзину товара
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с добавленным товаром
     */
    @PostMapping("/addPurchase")
    public RedirectView addPurchase(Authentication authentication,final RedirectAttributes redirectAttributes, @RequestParam int id, @RequestParam int count, Model model) {
        String userRole = getUserRole(authentication);
        if (userRole == "GUEST") {
            model.addAttribute("Status", "user_guest");
            redirectAttributes.addFlashAttribute("Status", "user_guest");
        }
        else {
            int userId = getUserId(authentication);
            Purchase purchase = purchaseService.getPurchaseByUserAndProductIds(userId, id);
            if (purchase == null) {
                Purchase newPurchase = new Purchase();
                newPurchase.setUserId(getUserId(authentication));
                newPurchase.setProductId(id);
                newPurchase.setProductCount(count);
                purchaseService.addPurchase(newPurchase);
                model.addAttribute("Status", "OK");
                redirectAttributes.addAttribute("Status", "OK");
            }
            else {
                if (purchase.getProductCount() + count > 10)
                {
                    model.addAttribute("Status", "count_overflow");
                    redirectAttributes.addAttribute("Status", "count_overflow");
                }
                else
                {
                    purchase.setProductCount(purchase.getProductCount() + count);
                    purchaseService.addPurchase(purchase);
                    model.addAttribute("Status", "OK");
                    redirectAttributes.addAttribute("Status", "OK");
                }
            }
        }
        final RedirectView redirectView = new RedirectView("/product?productId="+id, true);
        return redirectView;
    }

    /**
     * Метод слушающий все GET запросы по /purchases
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с корзиной
     */
    @GetMapping("/purchases")
    public String purchases(Authentication authentication, Model model) {
        String userRole = getUserRole(authentication);
        int userId = getUserId(authentication);
        model.addAttribute("totalPrice", getTotalPrice(purchaseService.getUserPurchases(userId)));
        model.addAttribute("userRole", userRole);
        model.addAttribute("userName", authentication.getName());
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("purchases", purchaseService.getUserPurchases(userId));
        model.addAttribute("sortPurchasesById", Comparator.comparing(Purchase::getId));
        model.addAttribute("productsService", productService);
        return "UserController/purchases";
    }

    /**
     * Метод слушающий все POST запросы по /changePurchases, служит для удаления товара из корзины
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param purchaseId Идентификатор добавленного в корзину товара
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с корзиной
     */
    @PostMapping(value = "/changePurchases", params = "delete")
    public String deletePurchase(Authentication authentication, @RequestParam int purchaseId,  Model model) {
        int userId = getUserId(authentication);
        if (purchaseService.getPurchaseByIdAndUserId(purchaseId, userId) != null) {
            purchaseService.deletePurchase(purchaseId);
        }
        return "redirect:/purchases";
    }

    /**
     * Метод слушающий все POST запросы по /changePurchases, служит для изменения колличества товаров в корзине
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param purchaseIds Идентификаторы добавленных в корзину товаров
     * @param productCounts Колличество добавленных в корзину товаров
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с корзиной
     */
    @PostMapping(value = "/changePurchases", params = "change")
    public String changeCountOfPurchases(Authentication authentication,
                                         @RequestParam(value="purchaseIds[]") int[] purchaseIds,
                                         @RequestParam(value="productCounts[]") int[] productCounts,
                                         Model model) {
        int userId = getUserId(authentication);
        if (purchaseIds.length == productCounts.length) {
            for (int i = 0; i < purchaseIds.length; i++) {
                if (purchaseService.getPurchaseByIdAndUserId(purchaseIds[i], userId) != null) {
                    Purchase purchase = purchaseService.getPurchaseById(purchaseIds[i]);
                    purchase.setProductCount(productCounts[i]);
                    purchaseService.addPurchase(purchase);
                }
            }
        }
        return "redirect:/purchases";
    }

    /**
     * Метод слушающий все POST запросы по /sendPurchases, служит для отправки информации пользователю и менеджеру
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param address Адрес доставки
     * @param telephone Телефон пользователя
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с корзиной
     */
    @SneakyThrows
    @PostMapping(value = "/sendPurchases")
    public String sendPurchases(Authentication authentication,
                                @RequestParam(value="address") String address,
                                @RequestParam(value="telephone") String telephone,
                                Model model) {
        User user = (User)userService.loadUserByUsername(authentication.getName());
        String userMessage = createMessageForUser(purchaseService.getUserPurchases(user.getId()));
        String managerMessage = createMessageForManager(user, address, telephone, purchaseService.getUserPurchases(user.getId()));
        emailService.sendmail(user.getEmail(), userMessage, false);
        emailService.sendmail("vlad.nomerov@yandex.ru", managerMessage, true);
        purchaseService.deletePurchasesByUserId(user.getId());
        return "redirect:/purchases";
    }

    /**
     * Метод для аутентификации, существует ли пользователь или нет
     * @param request Объект содержащий запрос, поступивший от пользователя
     * @param username Никнейм пользователя
     * @param password Пароль пользователя
     */
    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) { }
    }

    /**
     * Метод слушающий все GET запросы по /search
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param searchString Строка для поиска
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с поиском по сайту
     */
    @GetMapping("/search")
    public String searchProducts(Authentication authentication,
                                 @RequestParam(name = "name", required = false) String searchString,
                                 Model model) {
        model.addAttribute("userRole", getUserRole(authentication));
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("products", criteriaService.getByProductName(searchString));
        model.addAttribute("sortProductsById", Comparator.comparing(Product::getId));
        if (searchString == null) searchString = "null";
        model.addAttribute("searchName", searchString);
        return "UserController/search";
    }
}
