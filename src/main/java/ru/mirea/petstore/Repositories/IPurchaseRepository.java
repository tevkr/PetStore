package ru.mirea.petstore.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.petstore.Models.Purchase;

import java.util.List;

/**
 * Интерфейс-репозиторий получающий данные из таблицы БД с товарами добавленными в корзину
 * @author Яновский Владислав
 */
@Repository
public interface IPurchaseRepository extends JpaRepository<Purchase, Integer> {
    /**
     * Метод ищет товар добавленный в корзину по идентификатору пользователя и товара
     * @param userId Идентификатор пользователя
     * @param ProductId Идентификатор товара
     * @return Возвращает найденный товар добавленный в корзину
     */
    Purchase findByUserIdAndProductId(int userId, int ProductId);

    /**
     * Метод ищет товар добавленный в корзину по идентификатору товара добавленного в корзину и идентификатору пользователя
     * @param id Идентификатор товара добавленного в корзину
     * @param userId Идентификатор пользователя
     * @return Возвращает найденный товар добавленный в корзину
     */
    Purchase findByIdAndUserId(int id, int userId);

    /**
     * Метод ищет товары добавленные пользователем в корзину
     * @param userId Идентификатор пользователя
     * @return Возвращает товары добавленные пользователем в корзину
     */
    List<Purchase> findAllByUserId(int userId);

    /**
     * Метод удаляет товар добавленный в корзину
     * @param id Идентификатор товара добавленного в корзину
     * @return Возвращает рещультат удаления
     */
    Long deleteById(int id);

    /**
     * Метод ищет товар добавленный в корзину по идентификатору
     * @param id Идентификатор товара добавленного в корзину
     * @return Возвращает искомый товар добавленный в корзину
     */
    Purchase findById(int id);

    /**
     * Метод удаляет корзину товаров пользователя
     * @param userId Идентификатор пользователя
     * @return Возвращает результат удаления
     */
    @Transactional
    Long deleteAllByUserId(int userId);
}
