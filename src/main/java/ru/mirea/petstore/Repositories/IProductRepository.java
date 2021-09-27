package ru.mirea.petstore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.petstore.Models.Product;

import java.util.List;

/**
 * Интерфейс-репозиторий получающий данные из таблицы БД с товарами
 * @author Яновский Владислав
 */
@Repository
public interface IProductRepository extends JpaRepository<Product, Integer> {
    /**
     * Метод находит все товары по идентификатору питомца
     * @param petId Идентификатор питомца
     * @return Возвращает список найденных товаров
     */
    List<Product> findAllByPetId(int petId);

    /**
     * Метод находит все товары по идентификатору питомца и категории товара
     * @param petId Идентификатор питомца
     * @param productTypeId Идентификатор категории товара
     * @return Возвращает список найденных товаров
     */
    List<Product> findAllByPetIdAndProductTypeId(int petId, int productTypeId);

    /**
     * Метод находит все товары по идентификатору питомца, и категории товара, и подкатегории товара
     * @param petId Идентификатор питомца
     * @param productTypeId Идентификатор категории товара
     * @param productTypeDetailedId Идентификатор подкатегории товара
     * @return Возвращает список найденных товаров
     */
    List<Product> findAllByPetIdAndProductTypeIdAndProductTypeDetailedId(int petId, int productTypeId, int productTypeDetailedId);

    /**
     * Метод находит товар по идентификатору
     * @param id Идентификатор товара
     * @return Возвращает найденный товар
     */
    Product findById(int id);

    /**
     * Метод удаляет товар по идентификатору
     * @param id Идентификатор товара
     * @return Возвращает рещультат удаления
     */
    Long deleteById(int id);
}
