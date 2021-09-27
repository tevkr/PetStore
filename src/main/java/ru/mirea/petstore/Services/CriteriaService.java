package ru.mirea.petstore.Services;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.petstore.Models.Product;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Класс-сервис для фильтрации моделей по полю
 * @author Яновский Владислав
 */
@Component
@RequiredArgsConstructor
@Transactional
public class CriteriaService {
    /**
     * Неизменяемый потокобезопасный объект с компилированным маппингом для одной базы данных
     */
    private final SessionFactory sessionFactory;
    /**
     * Однопоточный короткоживущий объект, который предоставляет связь между объектами приложения и базой данных
     */
    private Session session;

    /**
     * Конструктор определяющий session
     */
    @PostConstruct
    void init() {
        session = sessionFactory.openSession();
    }

    /**
     * Деструктор освобождающий session
     */
    @PreDestroy
    void closeSession() {
        session.close();
    }

    /**
     * Метод фильтрует модели товаров по названию товара
     * @param productName Название товара
     * @return Возвращает список товаров, название которых соответствует переданной строке
     */
    public List<Product> getByProductName(String productName) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Product> orderCriteriaQuery = builder.createQuery(Product.class);
        Root<Product> root = orderCriteriaQuery.from(Product.class);
        String searchString = "%" + productName + "%";
        orderCriteriaQuery.select(root).where(builder.like(root.get("name"), searchString));
        Query<Product> query = session.createQuery(orderCriteriaQuery);
        return query.getResultList();
    }
}
