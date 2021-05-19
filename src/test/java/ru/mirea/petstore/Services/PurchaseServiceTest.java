package ru.mirea.petstore.Services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Models.Purchase;
import ru.mirea.petstore.Models.User;
import ru.mirea.petstore.Repositories.IPurchaseRepository;
import ru.mirea.petstore.Repositories.IUserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {
    @InjectMocks
    private PurchaseService purchaseService;
    @Mock
    private IPurchaseRepository iPurchaseRepository;
    @Captor
    private ArgumentCaptor<Purchase> captor;

    private Purchase purchase1, purchase2, purchase3;
    @BeforeEach
    void init() {
        purchase1 = new Purchase();
        purchase1.setId(1);
        purchase1.setUserId(1);
        purchase1.setProductId(1);
        purchase1.setProductCount(1);

        purchase2 = new Purchase();
        purchase2.setId(2);
        purchase2.setUserId(1);
        purchase2.setProductId(2);
        purchase2.setProductCount(1);

        purchase3 = new Purchase();
        purchase3.setId(3);
        purchase3.setUserId(2);
        purchase3.setProductId(3);
        purchase3.setProductCount(1);
    }
    @Test
    void addPurchase() {
        purchaseService.addPurchase(purchase1);
        Mockito.verify(iPurchaseRepository).save(captor.capture());
        Purchase captured = captor.getValue();
        assertEquals(1, captured.getId());
    }

    @Test
    void getPurchaseByUserAndProductIds() {
        Mockito.when(iPurchaseRepository.findByUserIdAndProductId(1, 2)).thenReturn(purchase2);
        assertEquals(purchase2, iPurchaseRepository.findByUserIdAndProductId(1, 2));
    }

    @Test
    void getPurchaseByIdAndUserId() {
        Mockito.when(iPurchaseRepository.findByIdAndUserId(1, 1)).thenReturn(purchase1);
        assertEquals(purchase1, iPurchaseRepository.findByIdAndUserId(1, 1));
    }

    @Test
    void getPurchaseById() {
        Mockito.when(iPurchaseRepository.findById(3)).thenReturn(purchase3);
        assertEquals(purchase3, iPurchaseRepository.findById(3));
    }

    @Test
    void getUserPurchases() {
        Mockito.when(iPurchaseRepository.findById(3)).thenReturn(purchase3);
        assertEquals(purchase3, iPurchaseRepository.findById(3));
    }

    @Test
    void deletePurchase() {
        purchaseService.deletePurchase(1);
        Mockito.verify(iPurchaseRepository).deleteById(1);
    }

    @Test
    void deletePurchasesByUserId() {
        purchaseService.deletePurchasesByUserId(1);
        Mockito.verify(iPurchaseRepository).deleteAllByUserId(1);
    }
}