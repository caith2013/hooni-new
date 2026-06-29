package com.hooni.controller;

import com.hooni.db.Product;
import com.hooni.repository.ProductRepository;
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
