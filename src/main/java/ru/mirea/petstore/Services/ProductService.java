package ru.mirea.petstore.Services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
        String folder = "media/img/products/";
        byte[] bytes = image.getBytes();
        String[] fileFormatArr = image.getOriginalFilename().split("\\.");
        String fileFormat = fileFormatArr[fileFormatArr.length - 1];
        Path path = Paths.get(folder + lastId + "." + fileFormat);
        Files.write(path, bytes);
    }
}
