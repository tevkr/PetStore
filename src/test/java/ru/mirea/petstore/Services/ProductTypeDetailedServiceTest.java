package ru.mirea.petstore.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.petstore.Models.Pet;
import ru.mirea.petstore.Models.ProductTypeDetailed;
import ru.mirea.petstore.Repositories.IPetRepository;
import ru.mirea.petstore.Repositories.IProductTypeDetailedRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductTypeDetailedServiceTest {
    @InjectMocks
    private ProductTypeDetailedService productTypeDetailedService;
    @Mock
    private IProductTypeDetailedRepository iProductTypeDetailedRepository;
    @Captor
    private ArgumentCaptor<ProductTypeDetailed> captor;

    private ProductTypeDetailed productTypeDetailed1, productTypeDetailed2, productTypeDetailed3;
    @BeforeEach
    void init() {
        productTypeDetailed1 = new ProductTypeDetailed();
        productTypeDetailed1.setId(1);
        productTypeDetailed1.setPetId(1);
        productTypeDetailed1.setProductTypeId(1);
        productTypeDetailed1.setName("productTypeDetailed1");

        productTypeDetailed2 = new ProductTypeDetailed();
        productTypeDetailed2.setId(2);
        productTypeDetailed2.setPetId(1);
        productTypeDetailed2.setProductTypeId(1);
        productTypeDetailed2.setName("productTypeDetailed2");

        productTypeDetailed3 = new ProductTypeDetailed();
        productTypeDetailed3.setId(3);
        productTypeDetailed3.setPetId(2);
        productTypeDetailed3.setProductTypeId(2);
        productTypeDetailed3.setName("productTypeDetailed3");
    }

    @Test
    void getAllProductTypesDetailed() {
        Mockito.when(iProductTypeDetailedRepository.findAll()).thenReturn(List.of(productTypeDetailed1, productTypeDetailed2, productTypeDetailed3));
        assertEquals(3, iProductTypeDetailedRepository.findAll().size());
    }

    @Test
    void getProductTypesDetailedByPetIdnProductTypeId() {
        Mockito.when(iProductTypeDetailedRepository.findAllByPetIdAndProductTypeId(1, 1)).thenReturn(List.of(productTypeDetailed1, productTypeDetailed2));
        assertEquals(2, iProductTypeDetailedRepository.findAllByPetIdAndProductTypeId(1, 1).size());
    }

    @Test
    void addProductTypeDetailed() {
        productTypeDetailedService.addProductTypeDetailed(productTypeDetailed1);
        Mockito.verify(iProductTypeDetailedRepository).save(captor.capture());
        ProductTypeDetailed captured = captor.getValue();
        assertEquals("productTypeDetailed1", captured.getName());
    }

    @Test
    void deleteProductTypeDetailed() {
        productTypeDetailedService.deleteProductTypeDetailed(1);
        Mockito.verify(iProductTypeDetailedRepository).deleteById(1);
    }

    @Test
    void getProductTypeDetailedById() {
        Mockito.when(iProductTypeDetailedRepository.findById(2)).thenReturn(productTypeDetailed2);
        assertEquals("productTypeDetailed2", iProductTypeDetailedRepository.findById(2).getName());
    }
}