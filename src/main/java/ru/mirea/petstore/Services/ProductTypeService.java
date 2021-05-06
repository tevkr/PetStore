package ru.mirea.petstore.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Repositories.IProductTypeDetailedRepository;
import ru.mirea.petstore.Repositories.IProductTypeRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductTypeService {
    private IProductTypeRepository iProductTypeRepository;

    @Autowired
    public ProductTypeService(IProductTypeRepository iProductTypeRepository) {
        this.iProductTypeRepository = iProductTypeRepository;
    }
    public List<ProductType> getProductTypesByPetId(int petId) {
        return iProductTypeRepository.findAllByPetId(petId);
    }
    public List<ProductType> getAllProductTypes() {
        return iProductTypeRepository.findAll();
    }

    public void addProductType(ProductType productType) {
        iProductTypeRepository.save(productType);
    }
    public void deleteProductType(int id) {
        iProductTypeRepository.deleteById(id);
    }
    public ProductType getProductTypeById(int id) {
        return iProductTypeRepository.findById(id);
    }
}
