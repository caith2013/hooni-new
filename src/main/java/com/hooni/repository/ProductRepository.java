package com.hooni.repository;

import com.hooni.db.PriceRangeBean;
import com.hooni.db.Product;
import com.hooni.db.ProductCounterBean;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    @Cacheable("productCountsByBrand")
    @Query("select new com.hooni.db.ProductCounterBean(COUNT(p), p.brand) " +
            "from Product p group by p.brand order by p.brand")
    List<ProductCounterBean> findDistinctBrands();

    @Cacheable("productCountsByCategory")
    @Query("select new com.hooni.db.ProductCounterBean(COUNT(p), p.category) " +
            "from Product p group by p.category order by p.category")
    List<ProductCounterBean> findDistinctCategories();

    @Query("select p from Product p order by p.id")
    Page<Product> findAllProducts(Pageable pageable);

    @Cacheable("priceRanges")
    @Query(value = "select cast(length(convert(final_price, char))-3 as signed) as price_length, " +
            "cast(substring(convert(final_price, char),1,1) as signed) as first_digit, " +
            "count(*) as cnt " +
            "from product " +
            "where final_price is not null " +
            "group by cast(length(convert(final_price, char))-3 as signed), " +
            "cast(substring(convert(final_price, char),1,1) as signed) " +
            "order by cast(length(convert(final_price, char))-3 as signed), " +
            "cast(substring(convert(final_price, char),1,1) as signed)",
            nativeQuery = true)
    List<Object[]> getPriceRangesRaw();

    @Cacheable("productsByCategory")
    @Query("select p from Product p where p.id in " +
           "(select max(p2.id) from Product p2 group by p2.category) " +
           "order by p.creationDate asc")
    Page<Product> findAllProductsByCategory(Pageable pageable);

}
