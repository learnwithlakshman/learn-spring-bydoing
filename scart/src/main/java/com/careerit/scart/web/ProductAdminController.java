package com.careerit.scart.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.careerit.scart.domain.Product;
import com.careerit.scart.service.ProductService;

@Controller
@RequestMapping("/admin")
public class ProductAdminController {

	@Autowired
	private ProductService productService;

	@GetMapping(value = { "/", "/home" })
	public String viewProducts(Model model) {
		List<Product> productList = productService.getAllProducts();
		model.addAttribute("productList", productList);
		return "admin/index";
	}

	@GetMapping("/v_addnewproduct")
	public String v_addNewProduct() {
		return "admin/v_addnewproduct";
	}

	@PostMapping("/addnewproduct")
	public String addnewProduct(@ModelAttribute("product") Product product) {
		productService.addProduct(product);
		return "redirect:/admin/";

	}
}
