package ru.mirea.petstore.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.petstore.Models.Pet;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Repositories.IProductRepository;
import ru.mirea.petstore.Repositories.IProductTypeRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductTypeServiceTest {

    @InjectMocks
    private ProductTypeService productTypeService;
    @Mock
    private IProductTypeRepository iProductTypeRepository;
    @Captor
    private ArgumentCaptor<ProductType> captor;

    private ProductType productType1, productType2, productType3;
    @BeforeEach
    void init() {
        productType1 = new ProductType();
        productType1.setId(1);
        productType1.setPetId(1);
        productType1.setName("productType1");

        productType2 = new ProductType();
        productType2.setId(2);
        productType2.setPetId(1);
        productType2.setName("productType2");

        productType3 = new ProductType();
        productType3.setId(3);
        productType3.setPetId(2);
        productType3.setName("productType3");
    }
    @Test
    void getProductTypesByPetId() {
        Mockito.when(iProductTypeRepository.findAllByPetId(1)).thenReturn(List.of(productType1, productType2));
        assertEquals(List.of(productType1, productType2), iProductTypeRepository.findAllByPetId(1));
    }

    @Test
    void getAllProductTypes() {
        Mockito.when(iProductTypeRepository.findAll()).thenReturn(List.of(productType1, productType2, productType3));
        assertEquals(3, iProductTypeRepository.findAll().size());
    }

    @Test
    void addProductType() {
        productTypeService.addProductType(productType1);
        Mockito.verify(iProductTypeRepository).save(captor.capture());
        ProductType captured = captor.getValue();
        assertEquals("productType1", captured.getName());
    }

    @Test
    void deleteProductType() {
        productTypeService.deleteProductType(1);
        Mockito.verify(iProductTypeRepository).deleteById(1);
    }

    @Test
    void getProductTypeById() {
        Mockito.when(iProductTypeRepository.findById(1)).thenReturn(productType1);
        assertEquals(productType1, iProductTypeRepository.findById(1));
    }
}