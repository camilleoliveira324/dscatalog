package com.devsuperior.dscatalog.repositories;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTest {

    private long exintingId;
    private long notExintingId;
    private long countTotalProducts;
    @BeforeEach
    void setUp() throws Exception {
        exintingId = 1L;
        notExintingId = 100L;
        countTotalProducts = 25L;
    }

    @Autowired
    private ProductRepository repository;

    @Test
    public void findByIdShouldReturnNotEmptyOptionalWhenIsExistingId() {
        Optional<Product> p = repository.findById(exintingId);

        Assertions.assertNotNull(p.get());
    }

    @Test
    public void findByIdShouldReturnEmptyOptionalWhenNotExistingId() {
        Optional<Product> p = repository.findById(notExintingId);

        Assertions.assertTrue(p.isEmpty());
    }

    @Test
    public void saveShouldPersistWithAutoincrementWhenIdIsNull() {
        Product p = Factory.createProduct();
        p.setId(null);

        p = repository.save(p);

        Assertions.assertNotNull(p.getId());
        Assertions.assertEquals(countTotalProducts + 1, p.getId());
    }

    @Test
    public void deleteShouldDeleteObjectWhenIdExist() {

        repository.deleteById(exintingId);

        Optional<Product> result = repository.findById(exintingId);
        Assertions.assertFalse(result.isPresent());
    }
}
