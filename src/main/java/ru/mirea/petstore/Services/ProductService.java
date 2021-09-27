package ru.mirea.petstore.Services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.mirea.petstore.Models.Product;
import ru.mirea.petstore.Models.ProductType;
import ru.mirea.petstore.Repositories.IProductRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Класс-сервис для передачи данных из табилцы Бд с товарами в контроллер
 * @author Яновский Владислав
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    /**
     * Интерфейс-репозиторий получающий данные из таблицы БД с товарами
     */
    private IProductRepository iProductRepository;

    /**
     * Конструктор класса-сервиса
     * @param iProductRepository Интерфейс-репозиторий получающий данные из таблицы БД с товарами
     */
    @Autowired
    public ProductService(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    /**
     * Метод получает список товаров по переданным идентификаторам
     * @param petId Идентификатор питомца
     * @param productTypeId Идентификатор категории товаров
     * @param productTypeDetailedId Идентификатор подкатегории товаров
     * @return Возвращает список товаров
     */
    public List<Product> getProductsByIds(int petId, int productTypeId, int productTypeDetailedId) {
        if (petId == 0)
            return iProductRepository.findAllByPetId(petId);
        if (productTypeId == 0)
            return iProductRepository.findAllByPetId(petId);
        if (productTypeDetailedId == 0)
            return iProductRepository.findAllByPetIdAndProductTypeId(petId, productTypeId);
        else
            return iProductRepository.findAllByPetIdAndProductTypeIdAndProductTypeDetailedId(petId, productTypeId, productTypeDetailedId);
    }

    /**
     * Метод получает товар по идентификатору
     * @param id Идентификатор товара
     * @return Возвращает искомый товар
     */
    public Product getProductById(int id) {
        return iProductRepository.findById(id);
    }

    /**
     * Метод получает список всех товаров
     * @return Возвращает список всех товаров
     */
    public List<Product> getAllProducts() {
        return iProductRepository.findAll();
    }

    /**
     * Метод удаляет товар по идентификатору
     * @param id Идентификатор товара
     */
    public void deleteProduct(int id) {
        iProductRepository.deleteById(id);
    }

    /**
     * Метод добавляет товар в БД
     * @param newProduct Объект добавляемого торава
     */
    public void addProduct(Product newProduct) {
        iProductRepository.save(newProduct);
    }

    /**
     * Метод сохраняет получанное изображение в папку сервера
     * @param image Изображение
     * @param lastId Идентификатор товара
     */
    @SneakyThrows
    public void saveImage(MultipartFile image, int lastId) {
        Path currentPath = Paths.get("src");
        Path absolutePath = currentPath.toAbsolutePath();

    //    String folder = "media/img/products/";
        byte[] bytes = image.getBytes();
        Path path = Paths.get(absolutePath + "/main/resources/static/img/products/" + lastId + ".png");
        Files.write(path, bytes);
    }
}
