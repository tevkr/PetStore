package ru.mirea.petstore.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс представляющий модель товара
 * @author Яновский Владислав
 */
@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    /**
     * Идентификатор торава
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Идентификатор питомца
     */
    @Column(name="pet_id")
    private int petId;
    /**
     * Идентификатор категории товара
     */
    @Column(name="product_type_id")
    private int productTypeId;
    /**
     * Идентификатор подкатегории товара
     */
    @Column(name="product_type_detailed_id")
    private int productTypeDetailedId;
    /**
     * Название товара
     */
    @Column(name="name")
    private String name;
    /**
     * Цена товара
     */
    @Column(name="price")
    private int price;
    /**
     * Вес товара
     */
    @Column(name="weight")
    private String weight;
    /**
     * Краткое описание товара
     */
    @Column(name="product_details")
    private String productDetails;
    /**
     * Полное описание товара
     */
    @Column(name="description")
    private String description;
    /**
     * Переопределенный метод ToString() для конверцатии экземпляря объекта в строговое представление
     * @return Возвращает строковое представление экземпляра класса
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", petId=" + petId +
                ", productTypeId=" + productTypeId +
                ", productTypeDetailedId=" + productTypeDetailedId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", weight='" + weight + '\'' +
                '}';
    }
}
