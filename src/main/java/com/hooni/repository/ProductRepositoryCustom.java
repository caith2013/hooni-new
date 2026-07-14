package com.hooni.repository;

import com.hooni.db.Product;
import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> getProducts(String category, String brand, int[] prices, String[] keywords);
    List<Product> findProductsByCategory(String[] keywords);
    List<Product> findProductsByBrand(String[] keywords);
    List<Product> findProductsByTitle(String[] keywords);
    List<Product> findProductsByUpc(String[] keywords);
}