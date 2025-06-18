package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryService categoryService;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        return new ProductDTO(repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado.")));
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> result = repository.findAll(pageable);
        return result.map(c -> new ProductDTO(c));
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product p = new Product();
        dtoToEntity(dto, p);
        return new ProductDTO(repository.save(p));
    }

    @Transactional
    public ProductDTO update(ProductDTO dto, Long id) {
        try {
            Product p = repository.getReferenceById(id);
            dtoToEntity(dto, p);
            return new ProductDTO(repository.save(p));
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException | EmptyResultDataAccessException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void dtoToEntity(ProductDTO dto, Product p) {
        p.setName(dto.getName());
        p.setDate(dto.getDate());
        p.setDescription(dto.getDescription());
        p.setPrice(dto.getPrice());
        p.setImgUrl(dto.getImgUrl());

        p.getCategories().clear();
        for (CategoryDTO categoryDTO : dto.getCategories()) {
            categoryDTO = categoryService.findById(categoryDTO.getId());
            p.getCategories().add(new Category(categoryDTO.getId(), categoryDTO.getName()));
        }
    }
}
