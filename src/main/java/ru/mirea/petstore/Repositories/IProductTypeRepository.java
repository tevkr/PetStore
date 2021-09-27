package ru.mirea.petstore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.petstore.Models.ProductType;

import java.util.List;

/**
 * Интерфейс-репозиторий получающий данные из таблицы БД с категориями товаров
 * @author Яновский Владислав
 */
@Repository
public interface IProductTypeRepository extends JpaRepository<ProductType, Integer> {
    /**
     * Метод ищет все категории товаров по идентификатору питомца
     * @param petId Идентификатор питомца
     * @return Возвращает список с найденными категориями товаров
     */
    List<ProductType> findAllByPetId(int petId);

    /**
     * Метод удаляет категорию по идентификатору
     * @param id Идентификатор категории
     * @return Возвращает рещультат удаления
     */
    Long deleteById(int id);

    /**
     * Медот ищет категорию по идентификатору
     * @param id Идентификатор категории
     * @return Возвращает искомую категорию
     */
    ProductType findById(int id);
}
