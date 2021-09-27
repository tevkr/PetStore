package ru.mirea.petstore.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mirea.petstore.Models.*;
import ru.mirea.petstore.Services.*;

import java.util.Comparator;

/**
 * Контроллер для админ панели
 * <p>
 * Данный класс обрабатывает все запросы поступающие по адресам, начимающимся с /admin
 * </p>
 * @author Яновский Владислав
*/
@Controller
@RequestMapping("/admin")
public class AdminController {
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
     * Конструктор для контроллера админ панели
     * @param petService Сервис для питомцев
     * @param productService Сервис для товаров
     * @param productTypeService Сервис для категорий товаров
     * @param productTypeDetailedService Сервис для подкатегорий товаров
     * @param userService Сервис для пользователей
     */
    @Autowired
    public AdminController(PetService petService,
                          ProductService productService,
                          ProductTypeService productTypeService,
                          ProductTypeDetailedService productTypeDetailedService,
                          UserService userService) {
        this.petService = petService;
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.productTypeDetailedService = productTypeDetailedService;
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
     * Метод слушающий все GET запросы по /admin
     * @param authentication Объект идентифицирующий пользователя обратившегося к методу
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает главную страницу админ панели
     */
    @GetMapping
    public String index(Authentication authentication, Model model) {
        model.addAttribute("userName", authentication.getName());
        return "AdminController/admin-index";
    }

    /**
     * Метод слушающий все GET запросы по /admin/pets
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с питомцами в админ панели
     */
    @GetMapping("/pets")
    public String pets(Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        return "AdminController/admin-pets";
    }

    /**
     * Метод слушающий все GET запросы по /admin/pets/{id}
     * @param id Идентификатор питомца
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с конкретным питомцем в админ панели
     */
    @GetMapping("/pets/{id}")
    public String showPet(@PathVariable("id") int id, Model model) {
        model.addAttribute("pet", petService.getPetById(id));
        return "AdminController/admin-pet-change";
    }

    /**
     * Метод слушающий все POST запросы по /admin/pets/create, создает нового питомца
     * @param name Имя питомца
     * @return Возвращает страницу с питомцами в админ панели
     */
    @PostMapping("/pets/create")
    public String petsCreate(@RequestParam String name) {
        Pet newPet = new Pet();
        newPet.setName(name);
        petService.addPet(newPet);
        return "redirect:/admin/pets";
    }

    /**
     * Метод слушающий все POST запросы по /admin/pets/change, изменяет существующего питомца
     * @param id Идентификатор питомца
     * @param name Имя питомца
     * @return Возвращает страницу с питомцами в админ панели
     */
    @PostMapping("/pets/change")
    public String petsChange(@RequestParam int id, @RequestParam String name) {
        Pet pet = petService.getPetById(id);
        pet.setName(name);
        petService.addPet(pet);
        return "redirect:/admin/pets";
    }

    /**
     * Метод слушающий все POST запросы по /admin/pets/delete, удаляет существующего питомца
     * @param id Идентификатор питомца
     * @return Возвращает страницу с питомцами в админ панели
     */
    @PostMapping("/pets/delete")
    public String petsDelete(@RequestParam int id) {
        petService.deletePet(id);
        return "redirect:/admin/pets";
    }

    /**
     * Метод слушающий все GET запросы по /admin/product-types
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с категориями товаров в админ панели
     */
    @GetMapping("/product-types")
    public String productTypes(Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("productTypeService", productTypeService);
        return "AdminController/admin-product-types";
    }

    /**
     * Метод слушающий все GET запросы по /admin/product-types/{id}
     * @param id Идентификатор категории товара
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с конкретной категорией товара в админ панели
     */
    @GetMapping("/product-types/{id}")
    public String showProductType(@PathVariable("id") int id, Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("productType", productTypeService.getProductTypeById(id));
        return "AdminController/admin-product-type-change";
    }

    /**
     * Метод слушающий все POST запросы по /admin/product-types/create, создает новую категорию товара
     * @param petId Идентификатор питомца
     * @param name Название категории товара
     * @return Возвращает страницу с категориями товаров в админ панели
     */
    @PostMapping("/product-types/create")
    public String productTypesCreate(@RequestParam int petId, @RequestParam String name) {
        ProductType newProductType = new ProductType();
        newProductType.setPetId(petId);
        newProductType.setName(name);
        productTypeService.addProductType(newProductType);
        return "redirect:/admin/product-types";
    }

    /**
     * Метод слушающий все POST запросы по /admin/product-types/change, изменяет существующую категорию товара
     * @param id Идентификатор категории товара
     * @param petId Идентификатор питомца
     * @param name Название категории товара
     * @return Возвращает страницу с категориями товаров в админ панели
     */
    @PostMapping("/product-types/change")
    public String productTypesChange(@RequestParam int id,@RequestParam int petId, @RequestParam String name) {
        ProductType productType = productTypeService.getProductTypeById(id);
        productType.setPetId(petId);
        productType.setName(name);
        productTypeService.addProductType(productType);
        return "redirect:/admin/product-types";
    }

    /**
     * Метод слушающий все POST запросы по /admin/product-types/delete, удаляет существующую категорию товара
     * @param id Идентификатор категории товара
     * @return Возвращает страницу с категориями товаров в админ панели
     */
    @PostMapping("/product-types/delete")
    public String productTypesDelete(@RequestParam int id) {
        productTypeService.deleteProductType(id);
        return "redirect:/admin/product-types";
    }

    /**
     * Метод слушающий все GET запросы по /admin/product-types-detailed
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с подкатегориями товаров в админ панели
     */
    @GetMapping("/product-types-detailed")
    public String productTypesDetailed(Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("productTypeService", productTypeService);
        model.addAttribute("productTypeDetailedService", productTypeDetailedService);
        return "AdminController/admin-product-types-detailed";
    }

    /**
     * Метод слушающий все GET запросы по /admin/product-types-detailed/{id}
     * @param id Идентификатор подкатегории товара
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с конкретной подкатегорией товара в админ панели
     */
    @GetMapping("/product-types-detailed/{id}")
    public String showProductTypeDetailed(@PathVariable("id") int id, Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("productTypeService", productTypeService);
        model.addAttribute("productTypeDetailed", productTypeDetailedService.getProductTypeDetailedById(id));
        return "AdminController/admin-product-type-detailed-change";
    }

    /**
     * Метод слушающий все POST запросы по /admin/product-types-detailed/create, создает новую подкатегорию товара
     * @param petId Идентификатор питомца
     * @param productTypeId Идентификатор категории товара
     * @param name Назване подкатегории
     * @return Возвращает страницу с подкатегориями товаров в админ панели
     */
    @PostMapping("/product-types-detailed/create")
    public String productTypesDetailedCreate(@RequestParam int petId, @RequestParam int productTypeId, @RequestParam String name) {
        ProductTypeDetailed newProductTypeDetailed = new ProductTypeDetailed();
        newProductTypeDetailed.setPetId(petId);
        newProductTypeDetailed.setProductTypeId(productTypeId);
        newProductTypeDetailed.setName(name);
        productTypeDetailedService.addProductTypeDetailed(newProductTypeDetailed);
        return "redirect:/admin/product-types-detailed";
    }

    /**
     * Метод слушающий все POST запросы по /admin/product-types-detailed/change, изменяет существующую подкатегорию товара
     * @param id Идентификатор подкатегории товара
     * @param petId Идентификатор питомца
     * @param productTypeId Идентификатор категории товара
     * @param name Назване подкатегории
     * @return Возвращает страницу с подкатегориями товаров в админ панели
     */
    @PostMapping("/product-types-detailed/change")
    public String productTypesDetailedChange(@RequestParam int id,@RequestParam int petId, @RequestParam int productTypeId, @RequestParam String name) {
        ProductTypeDetailed productTypeDetailed = productTypeDetailedService.getProductTypeDetailedById(id);
        productTypeDetailed.setPetId(petId);
        productTypeDetailed.setProductTypeId(productTypeId);
        productTypeDetailed.setName(name);
        productTypeDetailedService.addProductTypeDetailed(productTypeDetailed);
        return "redirect:/admin/product-types-detailed";
    }

    /**
     * Метод слушающий все POST запросы по /admin/product-types-detailed/delete, удаляет существующую подкатегорию товара
     * @param id Идентификатор подкатегории товара
     * @return Возвращает страницу с подкатегориями товаров в админ панели
     */
    @PostMapping("/product-types-detailed/delete")
    public String productTypesDetailedDelete(@RequestParam int id) {
        productTypeDetailedService.deleteProductTypeDetailed(id);
        return "redirect:/admin/product-types-detailed";
    }

    /**
     * Метод слушающий все GET запросы по /admin/products
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с товарами в админ панели
     */
    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("productTypeService", productTypeService);
        model.addAttribute("productTypeDetailedService", productTypeDetailedService);
        model.addAttribute("productService", productService);
        return "AdminController/admin-products";
    }

    /**
     * Метод слушающий все GET запросы по /admin/products/{id}
     * @param id Идентификатор товара
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с конкретным товаром в админ панели
     */
    @GetMapping("/products/{id}")
    public String showProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("productTypeService", productTypeService);
        model.addAttribute("productTypeDetailedService", productTypeDetailedService);
        model.addAttribute("product", productService.getProductById(id));
        return "AdminController/admin-product-change";
    }

    /**
     * Метод слушающий все POST запросы по /admin/products/create, создает новый товар
     * @param petId Идентификатор питомца
     * @param productTypeId Идентификатор категории товара
     * @param productTypeDetailedId Идентификатор подкатегории товара
     * @param name Название товара
     * @param price Цена товара
     * @param weight Вес товара
     * @param productDetails Краткое описание товара
     * @param description Полное описание товара
     * @param image Картинка товара
     * @return Возвращает страницу с товарами в админ панели
     */
    @PostMapping("/products/create")
    public String productCreate(@RequestParam int petId,
                                @RequestParam int productTypeId,
                                @RequestParam int productTypeDetailedId,
                                @RequestParam String name,
                                @RequestParam int price,
                                @RequestParam String weight,
                                @RequestParam String productDetails,
                                @RequestParam String description,
                                @RequestParam MultipartFile image) {
        Product newProduct = new Product();
        newProduct.setPetId(petId);
        newProduct.setProductTypeId(productTypeId);
        newProduct.setProductTypeDetailedId(productTypeDetailedId);
        newProduct.setName(name);
        newProduct.setPrice(price);
        newProduct.setWeight(weight);
        newProduct.setProductDetails(productDetails);
        newProduct.setDescription(description);
        productService.addProduct(newProduct);
        int lastId = newProduct.getId();
        productService.saveImage(image, lastId);
        return "redirect:/admin/products";
    }

    /**
     * Метод слушающий все POST запросы по /admin/products/change, изменяет существующий товар
     * @param id Идентификатор товара
     * @param petId Идентификатор питомца
     * @param productTypeId Идентификатор категории товара
     * @param productTypeDetailedId Идентификатор подкатегории товара
     * @param name Название товара
     * @param price Цена товара
     * @param weight Вес товара
     * @param productDetails Краткое описание товара
     * @param description Полное описание товара
     * @param image Картинка товара
     * @return Возвращает страницу с товарами в админ панели
     */
    @PostMapping("/products/change")
    public String productChange(@RequestParam int id,
                                @RequestParam int petId,
                                @RequestParam int productTypeId,
                                @RequestParam int productTypeDetailedId,
                                @RequestParam String name,
                                @RequestParam int price,
                                @RequestParam String weight,
                                @RequestParam String productDetails,
                                @RequestParam String description,
                                @RequestParam MultipartFile image) {
        Product product = productService.getProductById(id);
        product.setPetId(petId);
        product.setProductTypeId(productTypeId);
        product.setProductTypeDetailedId(productTypeDetailedId);
        product.setName(name);
        product.setPrice(price);
        product.setWeight(weight);
        product.setProductDetails(productDetails);
        product.setDescription(description);
        productService.addProduct(product);
        if (!image.isEmpty())
            productService.saveImage(image, id);
        return "redirect:/admin/products";
    }

    /**
     * Метод слушающий все POST запросы по /admin/products/delete, удаляет существующий товар
     * @param id Идентификатор товара
     * @return Возвращает страницу с товарами в админ панели
     */
    @PostMapping("/products/delete")
    public String productDelete(@RequestParam int id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }

    /**
     * Метод слушающий все GET запросы по /admin/users
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с пользователями в админ панели
     */
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("sortUsersById", Comparator.comparing(User::getId));
        return "AdminController/admin-users";
    }

    /**
     * Метод слушающий все POST запросы по /users/operation, удаляет существующего пользователя
     * @param userId Идентификатор пользователя
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с пользователями в админ панели
     */
    @PostMapping(value = "/users/operation", params = "delete")
    public String deleteUser(@RequestParam(value="userId") int userId, Model model) {
        userService.deleteUserByID(userId);
        return "redirect:/admin/users";
    }

    /**
     * Метод слушающий все POST запросы по /users/operation, изменяет роли у существующих пользователей
     * @param userNames Имена пользователей
     * @param userRoles Роли пользователей
     * @param model Объект предоставляющий атрибуты, используемые для визуализации представлений
     * @return Возвращает страницу с пользователями в админ панели
     */
    @PostMapping(value = "/users/operation", params = "change")
    public String changeUsers(@RequestParam(value="userNames[]") String[] userNames,
                              @RequestParam(value="userRoles[]") String[] userRoles,
                              Model model) {
        for (int i = 0; i < userNames.length; i++) {
            User user = (User)userService.loadUserByUsername(userNames[i]);
            user.setRole(userRoles[i]);
            userService.addUser(user);
        }
        return "redirect:/admin/users";
    }
}
