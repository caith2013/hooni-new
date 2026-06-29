package com.hooni.repository;

import com.hooni.db.Product;
import com.hooni.db.ProductCounterBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select new com.hooni.db.ProductCounterBean(COUNT(p), p.brand) " +
            "from Product p group by p.brand order by p.brand")
    List<ProductCounterBean> findDistinctBrands();

    @Query("select new com.hooni.db.ProductCounterBean(COUNT(p), p.category) " +
            "from Product p group by p.category order by p.category")
    List<ProductCounterBean> findDistinctCategories();
}
