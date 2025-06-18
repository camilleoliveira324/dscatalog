package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ProductDTO {

    private Long id;
    private String name;
    private Instant date;
    private Double price;
    private String description;
    private String imgUrl;

    public List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, Instant date, Double price, String description, String imgUrl) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.price = price;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    public ProductDTO(Product p) {
        id = p.getId();
        name = p.getName();
        date = p.getDate();
        price = p.getPrice();
        description = p.getDescription();
        imgUrl = p.getImgUrl();

        for (Category c : p.getCategories()) {
            categories.add(new CategoryDTO(c));
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getDate() {
        return date;
    }

    public Double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}
