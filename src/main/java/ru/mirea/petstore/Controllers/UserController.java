package ru.mirea.petstore.Controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.mirea.petstore.Models.*;
import ru.mirea.petstore.Services.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UserController {

    private final PetService petService;
    private final ProductService productService;
    private final ProductTypeService productTypeService;
    private final ProductTypeDetailedService productTypeDetailedService;
    private final UserService userService;
    private final PurchaseService purchaseService;
    private final EmailService emailService;

    @Autowired
    public UserController(PetService petService,
                          ProductService productService,
                          ProductTypeService productTypeService,
                          ProductTypeDetailedService productTypeDetailedService,
                          UserService userService,
                          PurchaseService purchaseService,
                          EmailService emailService) {
        this.petService = petService;
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.productTypeDetailedService = productTypeDetailedService;
        this.userService = userService;
        this.purchaseService = purchaseService;
        this.emailService = emailService;
    }
    private String getUserRole(Authentication authentication) {
        if (authentication == null)
            return "GUEST";
        else
            return ((User)userService.loadUserByUsername(authentication.getName())).getRole();
    }

    private int getUserId(Authentication authentication) {
        if (authentication == null)
            return 0;
        else
            return ((User)userService.loadUserByUsername(authentication.getName())).getId();
    }

    private int getTotalPrice(List<Purchase> purchases) {
        int result = 0;
        for (Purchase purchase: purchases) {
            result += productService.getProductById(purchase.getProductId()).getPrice() * purchase.getProductCount();
        }
        return result;
    }

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

    @GetMapping
    public String index(Authentication authentication, Model model) {
        model.addAttribute("userRole", getUserRole(authentication));
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        return "UserController/index";
    }
    @GetMapping("/about")
    public String about(Authentication authentication, Model model) {
        model.addAttribute("userRole", getUserRole(authentication));
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        return "UserController/about";
    }
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

    @GetMapping("/sign")
    public String sign() {
        return "UserController/signup";
    }

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

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

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
    @PostMapping(value = "/changePurchases", params = "delete")
    public String deletePurchase(Authentication authentication, @RequestParam int purchaseId,  Model model) {
        int userId = getUserId(authentication);
        if (purchaseService.getPurchaseByIdAndUserId(purchaseId, userId) != null) {
            purchaseService.deletePurchase(purchaseId);
        }
        return "redirect:/purchases";
    }
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

    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) { }
    }

    //@GetMapping("/login")
    //public String showLoginForm(Model model) {
    //    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //    if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
    //        return "login";
    //    }
    //    return "redirect:/";
    //}
}
