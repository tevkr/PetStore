package ru.mirea.petstore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Models.ProductTypeDetailed;

import java.util.List;

/**
 * Интерфейс-репозиторий получающий данные из таблицы БД с подкатегориями товаров
 * @author Яновский Владислав
 */
@Repository
public interface IProductTypeDetailedRepository extends JpaRepository<ProductTypeDetailed, Integer> {
    /**
     * Метод ищет все подкатегории товаров по идентификатору питомца и категории товара
     * @param petId Идентификатор питомца
     * @param productTypeId Идентификатор категории товара
     * @return Возвращает список с найденными подкатегориями товаров
     */
    List<ProductTypeDetailed> findAllByPetIdAndProductTypeId(int petId, int productTypeId);

    /**
     * Метод удаляет подкатегорию по идентификатору
     * @param id Идентификатор подкатегории
     * @return Возвращает рещультат удаления
     */
    Long deleteById(int id);

    /**
     * Медот ищет подкатегорию по идентификатору
     * @param id Идентификатор подкатегории
     * @return Возвращает искомую подкатегорию
     */
    ProductTypeDetailed findById(int id);
}
