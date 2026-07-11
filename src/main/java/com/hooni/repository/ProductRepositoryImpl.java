package com.hooni.repository;

import com.hooni.db.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Product> getProducts(String category, String brand, int[] prices, String[] keywords) {
        StringBuilder q = new StringBuilder("select p from Product p where 1=1");
        if (category != null && !category.trim().isEmpty()) {
            q.append(" and p.category = :category");
        }
        if (brand != null && !brand.trim().isEmpty()) {
            q.append(" and p.brand = :brand");
        }
        if (prices != null && prices.length >= 2) {
            q.append(" and p.finalPrice between :minPrice and :maxPrice");
        }
        if (keywords != null && keywords.length > 0) {
            q.append(" and (");
            for (int i = 0; i < keywords.length; i++) {
                q.append(" lower(p.title) like :kw").append(i);
                if (i < keywords.length - 1) q.append(" or");
            }
            q.append(" )");
        }

        TypedQuery<Product> query = em.createQuery(q.toString(), Product.class);

        if (category != null && !category.trim().isEmpty()) {
            query.setParameter("category", category);
        }
        if (brand != null && !brand.trim().isEmpty()) {
            query.setParameter("brand", brand);
        }
        if (prices != null && prices.length >= 2) {
            query.setParameter("minPrice", BigDecimal.valueOf(prices[0]));
            query.setParameter("maxPrice", BigDecimal.valueOf(prices[1]));
        }
        if (keywords != null && keywords.length > 0) {
            for (int i = 0; i < keywords.length; i++) {
                query.setParameter("kw" + i, "%" + keywords[i].toLowerCase() + "%");
            }
        }

        return query.getResultList();
    }
}