package ru.mirea.petstore.Services;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.petstore.Models.Product;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CriteriaServiceTest {
    @InjectMocks
    private CriteriaService criteriaService;

    @Test
    void getByProductName() {
        List<Product> products = new ArrayList<>();
        Product p1 = new Product();
        Product p2 = new Product();
        p1.setName("123");
        p2.setName("345");
        products.add(p1);
        products.add(p2);
        Mockito.when(criteriaService.getByProductName("3")).thenReturn(products);
        Mockito.when(criteriaService.getByProductName("12")).thenReturn(List.of(p1));
        Mockito.when(criteriaService.getByProductName("5")).thenReturn(List.of(p2));
        assertEquals(1, criteriaService.getByProductName("12").size());
        assertEquals(1, criteriaService.getByProductName("5").size());
        assertEquals(2, criteriaService.getByProductName("3").size());
    }
}