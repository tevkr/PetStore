package ru.mirea.petstore.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mirea.petstore.Models.Pet;
import ru.mirea.petstore.Models.Product;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Models.ProductTypeDetailed;
import ru.mirea.petstore.Services.PetService;
import ru.mirea.petstore.Services.ProductService;
import ru.mirea.petstore.Services.ProductTypeDetailedService;
import ru.mirea.petstore.Services.ProductTypeService;

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

    @Autowired
    public UserController(PetService petService, ProductService productService, ProductTypeService productTypeService, ProductTypeDetailedService productTypeDetailedService) {
        this.petService = petService;
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.productTypeDetailedService = productTypeDetailedService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        return "UserController/index";
    }
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        return "UserController/about";
    }
    @GetMapping("/products")
    public String products(@RequestParam(name = "pId", required = false) Integer IpetId,
                           @RequestParam(name = "pTId", required = false) Integer IproductTypeId,
                           @RequestParam(name = "pTDId", required = false) Integer IproductTypeDetailedId,
                           Model model) {
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
    public String product(@RequestParam(name = "productId", required = false) Integer IproductId, Model model) {
        int productId = (IproductId == null) ? 0:IproductId;

        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        String productDetails = productService.getProductById(productId).getProductDetails();
        String productDescription = productService.getProductById(productId).getDescription();
        String[] productDetailsArr = productDetails.split("\n");
        String[] productDescriptionArr = productDescription.split("\n");
//        productDetails = productDetails.replace("\n","<br />");
//        productDescription = productDescription.replace("\n","<br />");
        model.addAttribute("product", productService.getProductById(productId));
        model.addAttribute("productDetails", productDetailsArr);
        model.addAttribute("productDescription", productDescriptionArr);

        return "UserController/product";
    }
}
