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

@Component
@RequiredArgsConstructor
@Transactional
public class CriteriaService {
    private final SessionFactory sessionFactory;
    private Session session;
    @PostConstruct
    void init() {
        session = sessionFactory.openSession();
    }
    @PreDestroy
    void closeSession() {
        session.close();
    }
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
