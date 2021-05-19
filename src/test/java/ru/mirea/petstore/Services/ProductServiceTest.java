package ru.mirea.petstore.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.petstore.Models.Pet;
import ru.mirea.petstore.Models.Product;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Repositories.IPetRepository;
import ru.mirea.petstore.Repositories.IProductRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;
    @Mock
    private IProductRepository iProductRepository;
    @Captor
    private ArgumentCaptor<Product> captor;

    private Product product1, product2, product3;
    @BeforeEach
    void init() {
        product1 = new Product();
        product1.setId(1);
        product1.setPetId(1);
        product1.setProductTypeId(1);
        product1.setProductTypeDetailedId(1);
        product1.setName("product1");
        product1.setPrice(100);
        product1.setWeight("10 kilogram");
        product1.setProductDetails("details");
        product1.setDescription("description");

        product2 = new Product();
        product2.setId(2);
        product2.setPetId(1);
        product2.setProductTypeId(1);
        product2.setProductTypeDetailedId(1);
        product2.setName("product2");
        product2.setPrice(100);
        product2.setWeight("10 kilogram");
        product2.setProductDetails("details");
        product2.setDescription("description");

        product3 = new Product();
        product3.setId(3);
        product3.setPetId(2);
        product3.setProductTypeId(1);
        product3.setProductTypeDetailedId(1);
        product3.setName("product3");
        product3.setPrice(100);
        product3.setWeight("10 kilogram");
        product3.setProductDetails("details");
        product3.setDescription("description");
    }
    @Test
    void getProductsByIds() {
        Mockito.when(iProductRepository.findAllByPetId(0)).thenReturn(List.of());
        Mockito.when(iProductRepository.findAllByPetId(1)).thenReturn(List.of(product1, product2));
        Mockito.when(iProductRepository.findAllByPetIdAndProductTypeId(1, 1)).thenReturn(List.of(product1, product2));
        Mockito.when(iProductRepository.findAllByPetIdAndProductTypeIdAndProductTypeDetailedId(1, 1, 1)).thenReturn(List.of(product1, product2));
        assertEquals(List.of(), iProductRepository.findAllByPetId(0));
        assertEquals(List.of(product1, product2), iProductRepository.findAllByPetId(1));
        assertEquals(List.of(product1, product2), iProductRepository.findAllByPetIdAndProductTypeId(1, 1));
        assertEquals(List.of(product1, product2), iProductRepository.findAllByPetIdAndProductTypeIdAndProductTypeDetailedId(1, 1, 1));
    }

    @Test
    void getProductById() {
        Mockito.when(iProductRepository.findById(3)).thenReturn(product3);
        assertEquals("product3",iProductRepository.findById(3).getName());
    }

    @Test
    void getAllProducts() {
        Mockito.when(iProductRepository.findAll()).thenReturn(List.of(product1, product2, product3));
        assertEquals(List.of(product1, product2, product3),iProductRepository.findAll());
    }

    @Test
    void deleteProduct() {
        productService.deleteProduct(1);
        Mockito.verify(iProductRepository).deleteById(1);
    }

    @Test
    void addProduct() {
        productService.addProduct(product3);
        Mockito.verify(iProductRepository).save(captor.capture());
        Product captured = captor.getValue();
        assertEquals("product3", captured.getName());
    }
}