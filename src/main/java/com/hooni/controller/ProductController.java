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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
        if (pid != null) {
            model.addAttribute("product", productRepo.findById(pid).orElse(null));
            return "product_detail";
        }
        model.addAttribute("products", productRepo.findAll());
        model.addAttribute("brands",    productRepo.findDistinctBrands());
        model.addAttribute("categories",productRepo.findDistinctCategories());

        Pageable pageable = PageRequest.of(0, 50);
        Page<Product> page = productRepo.findAllProducts(pageable);
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

        return "products_home";
    }

    /** AJAX product search */
    @GetMapping("/searchproductajax")
    @ResponseBody
    public List<Product> searchProducts(@RequestParam(name = "q", defaultValue = "") String query) {
        // TODO: implement full-text search; returning all for now
        return productRepo.findAll();
    }
}
