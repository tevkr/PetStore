package ru.mirea.petstore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.petstore.Models.Purchase;

import java.util.List;

@Repository
public interface IPurchaseRepository extends JpaRepository<Purchase, Integer> {
    Purchase findByUserIdAndProductId(int userId, int ProductId);
    Purchase findByIdAndUserId(int id, int userId);
    List<Purchase> findAllByUserId(int userId);
    Long deleteById(int id);
    Purchase findById(int id);
    @Transactional
    Long deleteAllByUserId(int userId);
}
