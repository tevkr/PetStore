package ru.mirea.petstore.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Models.ProductTypeDetailed;
import ru.mirea.petstore.Repositories.IProductTypeDetailedRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductTypeDetailedService {
    private IProductTypeDetailedRepository iProductTypeDetailedRepository;

    @Autowired
    public ProductTypeDetailedService(IProductTypeDetailedRepository iProductTypeDetailedRepository) {
        this.iProductTypeDetailedRepository = iProductTypeDetailedRepository;
    }
    public List<ProductTypeDetailed> getAllProductTypesDetailed() {
        return iProductTypeDetailedRepository.findAll();
    }

    public List<ProductTypeDetailed> getProductTypesDetailedByPetIdnProductTypeId(int petId, int productTypeId) {
        return iProductTypeDetailedRepository.findAllByPetIdAndProductTypeId(petId, productTypeId);
    }
    public void addProductTypeDetailed(ProductTypeDetailed productTypeDetailed) {
        iProductTypeDetailedRepository.save(productTypeDetailed);
    }
    public void deleteProductTypeDetailed(int id) {
        iProductTypeDetailedRepository.deleteById(id);
    }
    public ProductTypeDetailed getProductTypeDetailedById(int id) {
        return iProductTypeDetailedRepository.findById(id);
    }
}
