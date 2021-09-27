package ru.mirea.petstore.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Класс представляющий модель подкатегории товаров
 * @author Яновский Владислав
 */
@Entity
@Getter
@Setter
@Table(name = "product_types_detailed")
public class ProductTypeDetailed {
    /**
     * Идентификатор подкатегории
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
     * Идентификатор категории
     */
    @Column(name="product_type_id")
    private int productTypeId;
    /**
     * Навание подкатегории
     */
    @Column(name="name")
    private String name;
}
