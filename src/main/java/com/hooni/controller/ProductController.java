package com.hooni.controller;

import com.hooni.db.PriceRangeBean;
import com.hooni.db.Product;
import com.hooni.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Replaces the original ProductController + ProductManagerController.
 */
@Controller
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepo;

    public ProductController(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping
    public String productList(@RequestParam(name = "pid", required = false) Long pid,
                              Model model) {

        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("brands",    productRepo.findDistinctBrands());
        model.addAttribute("categories",productRepo.findDistinctCategories());

        Pageable pageable = PageRequest.of(0, 50);
        Page<Product> page = productRepo.findAllProductsByCategory(pageable);
        model.addAttribute("HooniItems", page.getContent());
        try {
            model.addAttribute("priceranges", productRepo.getPriceRangesRaw().stream()
                    .map(row -> new PriceRangeBean(
                            ((Number) row[0]).intValue(),
                            ((Number) row[1]).intValue(),
                            ((Number) row[2]).intValue()
                    ))
                    .toList());
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("priceranges", List.of());
        }
        if (pid != null) {
            model.addAttribute("product", productRepo.findById(pid).orElse(null));
            return "product_detail";
        }
        return "products_home";
    }

    /** AJAX product search */
    @PostMapping("/searchproductajax")
    public List<Product> searchProducts(@RequestParam(name = "category", defaultValue = "") String category,
                                        @RequestParam(name = "brand", defaultValue = "") String brand,
                                        @RequestParam(name = "price", defaultValue = "") String price,
                                        @RequestParam(name = "keywords", defaultValue = "") String keywords,
                                        @RequestParam(name = "menu", defaultValue = "") String menu) {
        int[] prices = getPrices(price);

        String[] keywordsArray = getKeywords(keywords);

        List<Product> products;
        if ((category == null || category.trim().isEmpty()) && (brand == null || brand.trim().isEmpty()) && prices == null)
        {
            products = productRepo.getProducts(keywords);
        }
        else
        {
            products = productRepo.getProducts(category,brand,prices,keywords);
        }
        tb.put("HooniItems", products);

        return productRepo.findAll();
    }

    private String[] getKeywords(String s_keywords)
    {
        if (s_keywords != null && !s_keywords.trim().isEmpty())
        {
            return s_keywords.split("\\s+");
        }
        else
        {
            return null;
        }
    }
    private int[] getPrices(String s_price)
    {
        int[] result = new int[2];
        if (s_price != null && !s_price.trim().isEmpty())
        {
            StringTokenizer st = new StringTokenizer(s_price,"::");
            result[0] = Integer.parseInt((String)st.nextElement());
            result[1] = Integer.parseInt((String)st.nextElement());
            return result;

        }
        else
        {
            return null;
        }
    }
}
