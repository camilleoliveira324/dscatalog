package com.devsuperior.dscatalog.util;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;

import java.time.Instant;

public class Factory {

    public static Product createProduct() {
        var p = new Product(1L, "Phone", Instant.parse("2020-07-13T20:50:07.12345Z"), 800.0, "Good phone","https://raw.githubusercontent.com/devsuperior");
        p.getCategories().add(new Category(2L, "Electronics"));
        return p;
    }

    public static ProductDTO createProductDTO() {
        var p = new Product(1L, "Phone", Instant.parse("2020-07-13T20:50:07.12345Z"), 800.0, "Good phone","https://raw.githubusercontent.com/devsuperior");
        p.getCategories().add(new Category(2L, "Electronics"));
        return new ProductDTO(p);
    }

    public static ProductDTO createProductDTOWithoutId() {
        var p = new ProductDTO(null, "Phone", Instant.parse("2020-07-13T20:50:07.12345Z"), 800.0, "Good phone","https://raw.githubusercontent.com/devsuperior");
        p.getCategories().add(new CategoryDTO(2L, "Electronics"));
        return p;
    }
}
