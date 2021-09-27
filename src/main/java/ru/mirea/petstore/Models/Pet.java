package ru.mirea.petstore.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Map;

/**
 * Класс представляющий модель питомца
 * @author Яновский Владислав
 */
@Entity
@Getter
@Setter
@Table(name = "pets")
public class Pet {
    /**
     * Идентификатор питомца
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    /**
     * Имя питомца
     */
    @Column(name="name")
    private String name;

    /**
     * Переопределенный метод ToString() для конверцатии экземпляря объекта в строговое представление
     * @return Возвращает строковое представление экземпляра класса
     */
    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
