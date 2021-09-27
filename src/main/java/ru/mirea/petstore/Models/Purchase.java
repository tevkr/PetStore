package ru.mirea.petstore.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс представляющий модель добавленных товаров в корзину
 * @author Яновский Владислав
 */
@Entity
@Getter
@Setter
@Table(name = "shopping_cart")
public class Purchase {
    /**
     * Идентификатор добавленного в корзину товара
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    /**
     * Идентификатор пользователя, добавившего товар в корзину
     */
    @Column(name="user_id")
    int userId;
    /**
     * Идентификатор товара
     */
    @Column(name="product_id")
    int productId;
    /**
     * Количество товара
     */
    @Column(name="product_count")
    int productCount;
    /**
     * Переопределенный метод ToString() для конверцатии экземпляря объекта в строговое представление
     * @return Возвращает строковое представление экземпляра класса
     */
    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", userId=" + userId +
                ", productId=" + productId +
                ", productCount=" + productCount +
                '}';
    }
}
