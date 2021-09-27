package ru.mirea.petstore.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Models.ProductTypeDetailed;
import ru.mirea.petstore.Repositories.IProductTypeDetailedRepository;

import java.util.List;

/**
 * Класс-сервис для передачи данных из табилцы Бд с подкатегориями товаров в контроллер
 * @author Яновский Владислав
 */
@Service
@RequiredArgsConstructor
public class ProductTypeDetailedService {
    /**
     * Интерфейс-репозиторий получающий данные из таблицы БД с подкатегориями товаров
     */
    private IProductTypeDetailedRepository iProductTypeDetailedRepository;

    /**
     * Конструктор класса-сервиса
     * @param iProductTypeDetailedRepository Интерфейс-репозиторий получающий данные из таблицы БД с подкатегориями товаров
     */
    @Autowired
    public ProductTypeDetailedService(IProductTypeDetailedRepository iProductTypeDetailedRepository) {
        this.iProductTypeDetailedRepository = iProductTypeDetailedRepository;
    }

    /**
     * Метод получает список всех подкатегорий товаров
     * @return Возвращает список всех подкатегорий товаров
     */
    public List<ProductTypeDetailed> getAllProductTypesDetailed() {
        return iProductTypeDetailedRepository.findAll();
    }

    /**
     * Метод получает список подкатегорий товаров соответствующих переданным идентификаторам
     * @param petId Идентификатор питомца
     * @param productTypeId Идентификатор категории товара
     * @return Возвращает список подкатегорий товаров
     */
    public List<ProductTypeDetailed> getProductTypesDetailedByPetIdnProductTypeId(int petId, int productTypeId) {
        return iProductTypeDetailedRepository.findAllByPetIdAndProductTypeId(petId, productTypeId);
    }

    /**
     * Метод добавляет подкатегорию товаров в БД
     * @param productTypeDetailed Объект подкатегории товаров
     */
    public void addProductTypeDetailed(ProductTypeDetailed productTypeDetailed) {
        iProductTypeDetailedRepository.save(productTypeDetailed);
    }

    /**
     * Метод удаляет подкатегорию товаров из БД по идентификатору
     * @param id Идентификатор подкатегории товаров
     */
    public void deleteProductTypeDetailed(int id) {
        iProductTypeDetailedRepository.deleteById(id);
    }

    /**
     * Метод получает подкатегорию товаров по идентификатору
     * @param id Идентификатор подкатегории товаров
     * @return Возвращает искомую подкатегорию товаров
     */
    public ProductTypeDetailed getProductTypeDetailedById(int id) {
        return iProductTypeDetailedRepository.findById(id);
    }
}
