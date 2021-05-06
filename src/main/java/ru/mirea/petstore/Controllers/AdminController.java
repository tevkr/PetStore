package ru.mirea.petstore.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mirea.petstore.Models.Pet;
import ru.mirea.petstore.Models.Product;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Models.ProductTypeDetailed;
import ru.mirea.petstore.Services.PetService;
import ru.mirea.petstore.Services.ProductService;
import ru.mirea.petstore.Services.ProductTypeDetailedService;
import ru.mirea.petstore.Services.ProductTypeService;

import java.util.Comparator;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PetService petService;
    private final ProductService productService;
    private final ProductTypeService productTypeService;
    private final ProductTypeDetailedService productTypeDetailedService;

    @Autowired
    public AdminController(PetService petService, ProductService productService, ProductTypeService productTypeService, ProductTypeDetailedService productTypeDetailedService) {
        this.petService = petService;
        this.productService = productService;
        this.productTypeService = productTypeService;
        this.productTypeDetailedService = productTypeDetailedService;
    }

    @GetMapping
    public String index() {
        return "AdminController/admin-index";
    }

    @GetMapping("/pets")
    public String pets(Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        return "AdminController/admin-pets";
    }
    @GetMapping("/pets/{id}")
    public String showPet(@PathVariable("id") int id, Model model) {
        model.addAttribute("pet", petService.getPetById(id));
        return "AdminController/admin-pet-change";
    }
    @PostMapping("/pets/create")
    public String petsCreate(@RequestParam String name) {
        Pet newPet = new Pet();
        newPet.setName(name);
        petService.addPet(newPet);
        return "redirect:/admin/pets";
    }
    @PostMapping("/pets/change")
    public String petsChange(@RequestParam int id, @RequestParam String name) {
        Pet pet = petService.getPetById(id);
        pet.setName(name);
        petService.addPet(pet);
        return "redirect:/admin/pets";
    }
    @PostMapping("/pets/delete")
    public String petsDelete(@RequestParam int id) {
        petService.deletePet(id);
        return "redirect:/admin/pets";
    }

    @GetMapping("/product-types")
    public String productTypes(Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("productTypeService", productTypeService);
        return "AdminController/admin-product-types";
    }
    @GetMapping("/product-types/{id}")
    public String showProductType(@PathVariable("id") int id, Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("productType", productTypeService.getProductTypeById(id));
        return "AdminController/admin-product-type-change";
    }
    @PostMapping("/product-types/create")
    public String productTypesCreate(@RequestParam int petId, @RequestParam String name) {
        ProductType newProductType = new ProductType();
        newProductType.setPetId(petId);
        newProductType.setName(name);
        productTypeService.addProductType(newProductType);
        return "redirect:/admin/product-types";
    }
    @PostMapping("/product-types/change")
    public String productTypesChange(@RequestParam int id,@RequestParam int petId, @RequestParam String name) {
        ProductType productType = productTypeService.getProductTypeById(id);
        productType.setPetId(petId);
        productType.setName(name);
        productTypeService.addProductType(productType);
        return "redirect:/admin/product-types";
    }
    @PostMapping("/product-types/delete")
    public String productTypesDelete(@RequestParam int id) {
        productTypeService.deleteProductType(id);
        return "redirect:/admin/product-types";
    }

    @GetMapping("/product-types-detailed")
    public String productTypesDetailed(Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("productTypeService", productTypeService);
        model.addAttribute("productTypeDetailedService", productTypeDetailedService);
        return "AdminController/admin-product-types-detailed";
    }
    @GetMapping("/product-types-detailed/{id}")
    public String showProductTypeDetailed(@PathVariable("id") int id, Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("productTypeService", productTypeService);
        model.addAttribute("productTypeDetailed", productTypeDetailedService.getProductTypeDetailedById(id));
        return "AdminController/admin-product-type-detailed-change";
    }
    @PostMapping("/product-types-detailed/create")
    public String productTypesDetailedCreate(@RequestParam int petId, @RequestParam int productTypeId, @RequestParam String name) {
        ProductTypeDetailed newProductTypeDetailed = new ProductTypeDetailed();
        newProductTypeDetailed.setPetId(petId);
        newProductTypeDetailed.setProductTypeId(productTypeId);
        newProductTypeDetailed.setName(name);
        productTypeDetailedService.addProductTypeDetailed(newProductTypeDetailed);
        return "redirect:/admin/product-types-detailed";
    }
    @PostMapping("/product-types-detailed/change")
    public String productTypesDetailedChange(@RequestParam int id,@RequestParam int petId, @RequestParam int productTypeId, @RequestParam String name) {
        ProductTypeDetailed productTypeDetailed = productTypeDetailedService.getProductTypeDetailedById(id);
        productTypeDetailed.setPetId(petId);
        productTypeDetailed.setProductTypeId(productTypeId);
        productTypeDetailed.setName(name);
        productTypeDetailedService.addProductTypeDetailed(productTypeDetailed);
        return "redirect:/admin/product-types-detailed";
    }
    @PostMapping("/product-types-detailed/delete")
    public String productTypesDetailedDelete(@RequestParam int id) {
        productTypeDetailedService.deleteProductTypeDetailed(id);
        return "redirect:/admin/product-types-detailed";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("productTypeService", productTypeService);
        model.addAttribute("productTypeDetailedService", productTypeDetailedService);
        model.addAttribute("productService", productService);
        return "AdminController/admin-products";
    }
    @GetMapping("/products/{id}")
    public String showProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("sortPetsById", Comparator.comparing(Pet::getId));
        model.addAttribute("pets", petService.getAllPets());
        model.addAttribute("productTypeService", productTypeService);
        model.addAttribute("productTypeDetailedService", productTypeDetailedService);
        model.addAttribute("product", productService.getProductById(id));
        return "AdminController/admin-product-change";
    }
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
    @PostMapping("/products/delete")
    public String productDelete(@RequestParam int id) {
        productService.deleteProduct(id);
        return "redirect:/admin/products";
    }
}
