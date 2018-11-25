package com.ecommerce.microcommerce.controller;

import com.ecommerce.microcommerce.dao.ProductDao;
import com.ecommerce.microcommerce.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class ProductController {

    // Couche données
    @Autowired
    private ProductDao productDao;

    // Produits
    @GetMapping(value = "Produits")
    public List<Product> productList() {
        return productDao.findAll();
    }

    // Produits/{id}
    @GetMapping(value = "Produits/{id}")
    public Product getProduit(@PathVariable int id) {
        return productDao.findById(id);
    }

    // Post Product
    @PostMapping(value = "/Produits")
    public ResponseEntity<Void> addProduct(@RequestBody Product product) {
        Product productSend = productDao.save(product);

        if (product == null) {
            return ResponseEntity.noContent().build();
        }

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productSend.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    // test/produits/{prixLimit}
    @GetMapping(value = "test/produits/{prixLimit}")
    public List<Product> getProduitsLimit(@PathVariable int prixLimit) {
        return productDao.findByPrixGreaterThan(prixLimit);
    }

    // test/produits/{prixLimit}
    @GetMapping(value = "test/produits_cher/{prixLimit}")
    public List<Product> getProduitsCher(@PathVariable int prixLimit) {
        return productDao.chercherProduitCher(prixLimit);
    }

}
