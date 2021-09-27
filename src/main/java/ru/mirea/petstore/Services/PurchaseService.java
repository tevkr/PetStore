package ru.mirea.petstore.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.mirea.petstore.Models.Purchase;
import ru.mirea.petstore.Repositories.IPurchaseRepository;

import java.util.List;

/**
 * Класс-сервис для передачи данных из табилцы Бд с товарами добавленными в корзину в контроллер
 * @author Яновский Владислав
 */
@Service
@RequiredArgsConstructor
public class PurchaseService {
    /**
     * Интерфейс-репозиторий получающий данные из таблицы БД с товарами добавленными в корзину
     */
    private IPurchaseRepository iPurchaseRepository;

    /**
     * Конструктор класса-сервиса
     * @param iPurchaseRepository Интерфейс-репозиторий получающий данные из таблицы БД с товарами добавленными в корзину
     */
    @Autowired
    public PurchaseService(IPurchaseRepository iPurchaseRepository) {
        this.iPurchaseRepository = iPurchaseRepository;
    }

    /**
     * Метод добавляет товар добавленный в корзину в БД
     * @param purchase Объект товара добавленного в корзину
     */
    public void addPurchase(Purchase purchase) {
        iPurchaseRepository.save(purchase);
    }

    /**
     * Метод получает товар добавленный в корзину по переданным идентификаторам
     * @param userId Идентификатор пользователя
     * @param productId Идентификатор товара
     * @return Возвращает товар добавленный в корзину
     */
    public Purchase getPurchaseByUserAndProductIds(int userId, int productId) {
        return iPurchaseRepository.findByUserIdAndProductId(userId, productId);
    }

    /**
     * Метод получает товар добавленный в корзину по переданным идентификаторам
     * @param id Идентификатор товара добавленного в корзину
     * @param userId Идентификатор пользователя
     * @return Возвращает товар добавленный в корзину
     */
    public Purchase getPurchaseByIdAndUserId(int id, int userId) {
        return iPurchaseRepository.findByIdAndUserId(id, userId);
    }

    /**
     * Метод получает товар добавленный в корзину по идентификатору
     * @param id Идентификатор товара добавленного в корзину
     * @return Возвращает товар добавленный в корзину
     */
    public Purchase getPurchaseById(int id) {
        return iPurchaseRepository.findById(id);
    }

    /**
     * Метод получает список товаров добавленных в корзину пользователем
     * @param userId Идентификатор пользователя
     * @return Возвращает список товаров добавленных в корзину пользователем
     */
    public List<Purchase> getUserPurchases(int userId) {
        return iPurchaseRepository.findAllByUserId(userId);
    }

    /**
     * Метод удаляет товар добавленный в корзину по идентификатору
     * @param id Идентификатор товара добавленного в корзину
     */
    public void deletePurchase(int id) {
        iPurchaseRepository.deleteById(id);
    }

    /**
     * Метод удаляет стовары добавленные в корзину пользователем
     * @param userId Идентификатор пользователя
     */
    public void deletePurchasesByUserId(int userId) {
        iPurchaseRepository.deleteAllByUserId(userId);
    }
}
