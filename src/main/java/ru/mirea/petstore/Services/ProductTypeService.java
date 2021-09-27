package ru.mirea.petstore.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Repositories.IProductTypeDetailedRepository;
import ru.mirea.petstore.Repositories.IProductTypeRepository;

import java.util.List;

/**
 * Класс-сервис для передачи данных из табилцы Бд с категориями товаров в контроллер
 * @author Яновский Владислав
 */
@Service
@RequiredArgsConstructor
public class ProductTypeService {
    /**
     * Интерфейс-репозиторий получающий данные из таблицы БД с категориями товаров
     */
    private IProductTypeRepository iProductTypeRepository;
    /**
     * Конструктор класса-сервиса
     * @param iProductTypeRepository Интерфейс-репозиторий получающий данные из таблицы БД с категориями товаров
     */
    @Autowired
    public ProductTypeService(IProductTypeRepository iProductTypeRepository) {
        this.iProductTypeRepository = iProductTypeRepository;
    }

    /**
     * Метод получает список категорий товаров соответствующих переданным идентификаторам
     * @param petId Идентификатор питомца
     * @return Возвращает список категорий товаров
     */
    public List<ProductType> getProductTypesByPetId(int petId) {
        return iProductTypeRepository.findAllByPetId(petId);
    }
    /**
     * Метод получает список всех категорий товаров
     * @return Возвращает список всех категорий товаров
     */
    public List<ProductType> getAllProductTypes() {
        return iProductTypeRepository.findAll();
    }
    /**
     * Метод добавляет категорию товаров в БД
     * @param productType Объект категории товаров
     */
    public void addProductType(ProductType productType) {
        iProductTypeRepository.save(productType);
    }
    /**
     * Метод удаляет категорию товаров из БД по идентификатору
     * @param id Идентификатор категории товаров
     */
    public void deleteProductType(int id) {
        iProductTypeRepository.deleteById(id);
    }
    /**
     * Метод получает категорию товаров по идентификатору
     * @param id Идентификатор категории товаров
     * @return Возвращает искомую категорию товаров
     */
    public ProductType getProductTypeById(int id) {
        return iProductTypeRepository.findById(id);
    }
}
