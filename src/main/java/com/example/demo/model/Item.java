package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "Item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "itemId")
    private Integer itemId;

    @Column(name = "itemName", nullable = false)
    private String itemName;

    @Column(name = "itemPrice", nullable = false)
    private BigInteger itemPrice;

    @Column(name = "itemDate", nullable = false)
    private LocalDateTime itemDate;

    // Getters and Setters
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigInteger getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigInteger itemPrice) {
        this.itemPrice = itemPrice;
    }

    public LocalDateTime getItemDate() {
        return itemDate;
    }

    public void setItemDate(LocalDateTime itemDate) {
        this.itemDate = itemDate;
    }
}