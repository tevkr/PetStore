package ru.mirea.petstore.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="pet_id")
    private int petId;
    @Column(name="product_type_id")
    private int productTypeId;
    @Column(name="product_type_detailed_id")
    private int productTypeDetailedId;
    @Column(name="name")
    private String name;
    @Column(name="price")
    private int price;
    @Column(name="weight")
    private String weight;
    @Column(name="product_details")
    private String productDetails;
    @Column(name="description")
    private String description;

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
