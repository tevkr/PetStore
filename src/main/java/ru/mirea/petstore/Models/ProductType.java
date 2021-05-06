package ru.mirea.petstore.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "product_types")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="pet_id")
    private int petId;
    @Column(name="name")
    private String name;

    @Override
    public String toString() {
        return "ProductType{" +
                "id=" + id +
                ", petId=" + petId +
                ", name='" + name + '\'' +
                '}';
    }
}
