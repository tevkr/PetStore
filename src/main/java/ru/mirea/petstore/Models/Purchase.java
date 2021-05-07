package ru.mirea.petstore.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "shopping_cart")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name="user_id")
    int userId;
    @Column(name="product_id")
    int productId;
    @Column(name="product_count")
    int productCount;

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
