package ru.mirea.petstore.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.petstore.Models.Purchase;
import ru.mirea.petstore.Repositories.IPurchaseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private IPurchaseRepository iPurchaseRepository;

    @Autowired
    public PurchaseService(IPurchaseRepository iPurchaseRepository) {
        this.iPurchaseRepository = iPurchaseRepository;
    }
    public void addPurchase(Purchase purchase) {
        iPurchaseRepository.save(purchase);
    }
    public Purchase getPurchaseByUserAndProductIds(int userId, int productId) {
        return iPurchaseRepository.findByUserIdAndProductId(userId, productId);
    }
    public Purchase getPurchaseByIdAndUserId(int id, int userId) {
        return iPurchaseRepository.findByIdAndUserId(id, userId);
    }
    public Purchase getPurchaseById(int id) {
        return iPurchaseRepository.findById(id);
    }
    public List<Purchase> getUserPurchases(int userId) {
        return iPurchaseRepository.findAllByUserId(userId);
    }
    public void deletePurchase(int id) {
        iPurchaseRepository.deleteById(id);
    }
    public void deletePurchasesByUserId(int userId) {
        iPurchaseRepository.deleteAllByUserId(userId);
    }
}
