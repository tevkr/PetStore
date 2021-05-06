package ru.mirea.petstore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Models.ProductTypeDetailed;

import java.util.List;

@Repository
public interface IProductTypeDetailedRepository extends JpaRepository<ProductTypeDetailed, Integer> {
    List<ProductTypeDetailed> findAllByPetIdAndProductTypeId(int petId, int productTypeId);
    Long deleteById(int id);
    ProductTypeDetailed findById(int id);
}
