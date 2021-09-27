package ru.mirea.petstore.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс представляющий модель категории товаров
 * @author Яновский Владислав
 */
@Entity
@Getter
@Setter
@Table(name = "product_types")
public class ProductType {
    /**
     * Идентификатор категории товара
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
     * Название категории
     */
    @Column(name="name")
    private String name;
    /**
     * Переопределенный метод ToString() для конверцатии экземпляря объекта в строговое представление
     * @return Возвращает строковое представление экземпляра класса
     */
    @Override
    public String toString() {
        return "ProductType{" +
                "id=" + id +
                ", petId=" + petId +
                ", name='" + name + '\'' +
                '}';
    }
}
