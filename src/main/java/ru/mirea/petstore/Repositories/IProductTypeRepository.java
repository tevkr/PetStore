package ru.mirea.petstore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.petstore.Models.ProductType;

import java.util.List;

@Repository
public interface IProductTypeRepository extends JpaRepository<ProductType, Integer> {
    List<ProductType> findAllByPetId(int petId);
    Long deleteById(int id);
    ProductType findById(int id);
}
