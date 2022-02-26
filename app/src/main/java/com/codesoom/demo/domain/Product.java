package com.codesoom.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String maker;
    private Integer price;
    private String url;

    public Product() {}

    public Product(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Product(String name, String maker, Integer price) {
        this.name = name;
        this.maker = maker;
        this.price = price;
    }

    public Product(Long id, String name, String maker, Integer price, String url) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
        this.url = url;
    }

    public Product(Long id, String name, String maker, Integer price) {
        this.id = id;
        this.name = name;
        this.maker = maker;
        this.price = price;
    }

    public Product(Long id, String name, Integer price, String url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public Product(Long id, String name, Integer price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Product(String name, Integer price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }


    public String getName() {
        return name;
    }

    public String getMaker() {
        return maker;
    }

    public Long getId() {
        return id;
    }

    public Integer getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public void change(Product source) {
        this.name = source.getName();
        this.maker = source.getMaker();
        this.price = source.getPrice();
    }
}
