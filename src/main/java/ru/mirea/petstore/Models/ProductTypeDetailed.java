package ru.mirea.petstore.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "product_types_detailed")
public class ProductTypeDetailed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="pet_id")
    private int petId;
    @Column(name="product_type_id")
    private int productTypeId;
    @Column(name="name")
    private String name;
}
