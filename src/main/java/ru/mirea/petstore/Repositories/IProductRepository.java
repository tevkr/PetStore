package ru.mirea.petstore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.petstore.Models.Product;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByPetId(int petId);
    List<Product> findAllByPetIdAndProductTypeId(int petId, int productTypeId);
    List<Product> findAllByPetIdAndProductTypeIdAndProductTypeDetailedId(int petId, int productTypeId, int productTypeDetailedId);
    Product findById(int id);
    Long deleteById(int id);
}
