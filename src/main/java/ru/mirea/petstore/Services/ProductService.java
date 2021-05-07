package ru.mirea.petstore.Services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.mirea.petstore.Models.Product;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Repositories.IProductRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private IProductRepository iProductRepository;

    @Autowired
    public ProductService(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    public List<Product> getProductsByIds(int petId, int productTypeId, int productTypeDetailedId) {
        if (petId == 0)
            return iProductRepository.findAllByPetId(petId);
        if (productTypeId == 0)
            return iProductRepository.findAllByPetId(petId);
        if (productTypeDetailedId == 0)
            return iProductRepository.findAllByPetIdAndProductTypeId(petId, productTypeId);
        else
            return iProductRepository.findAllByPetIdAndProductTypeIdAndProductTypeDetailedId(petId, productTypeId, productTypeDetailedId);
    }
    public Product getProductById(int id) {
        return iProductRepository.findById(id);
    }
    public List<Product> getAllProducts() {
        return iProductRepository.findAll();
    }
    public void deleteProduct(int id) {
        iProductRepository.deleteById(id);
    }
    public void addProduct(Product newProduct) {
        iProductRepository.save(newProduct);
    }
    @SneakyThrows
    public void saveImage(MultipartFile image, int lastId) {
        Path currentPath = Paths.get("src");
        Path absolutePath = currentPath.toAbsolutePath();

    //    String folder = "media/img/products/";
        byte[] bytes = image.getBytes();
        Path path = Paths.get(absolutePath + "/main/resources/static/img/products/" + lastId + ".png");
        Files.write(path, bytes);
    }
}
