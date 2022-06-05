package com.medicare.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.medicare.entity.Category;
import com.medicare.entity.Products;
import com.medicare.service.CategoryService;
import com.medicare.service.ProductNotFoundException;

@Controller
public class ManageController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/admin-manage-products")
	public String manage(Model model) {
		return listByPage(1, model, "name", "asc", null);
	}

	@GetMapping("/admin-manage-products/page/{pageNumber}")
	public String listByPage(@PathVariable("pageNumber") int pageNumber, Model model,
			@Param("sortField") String sortField, @Param("sortDir") String sortDir, @Param("keyword") String keyword) {
		Page<Products> listByPage = categoryService.listByPage(pageNumber, sortField, sortDir, keyword);
		List<Products> allProducts = listByPage.getContent();
		long startCount = (pageNumber - 1) * CategoryService.Product_Per_Page + 1;
		long endCount = startCount + CategoryService.Product_Per_Page - 1;
		if (endCount > listByPage.getTotalElements()) {
			endCount = listByPage.getTotalElements();
		}
		model.addAttribute("list", allProducts);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("totalPage", listByPage.getTotalPages());
		model.addAttribute("totalItems", listByPage.getTotalElements());
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		String reverseSort = sortDir.equals("asc") ? "desc" : "asc";
		model.addAttribute("reverseSortDir", reverseSort);
		return "manage";
	}

	@GetMapping("/admin-manage-products/new")
	public String createProduct(Model model) {
		Products products = new Products();
		List<Category> category = categoryService.categoryItems();
		model.addAttribute("products", products);
		model.addAttribute("categories", category);
		model.addAttribute("pageTitle", "Create Product");
		return "update";
	}

	@PostMapping("/admin-manage-products/save")
	public String saveCategory(@ModelAttribute("products") @Valid Products products, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, @RequestParam("file") MultipartFile multiPartfile)
			throws IOException {
		if (bindingResult.hasErrors()) {
			return "update";
		}
		if (!multiPartfile.isEmpty()) {
			String filename = StringUtils.cleanPath(multiPartfile.getOriginalFilename()).replace(" ", "");
			products.setImage(filename);
			Products saveProduct = categoryService.saveProduct(products);
			String uploadDir = "product-photos/" + saveProduct.getId();
			FileUploadUtil.main(uploadDir, filename, multiPartfile);
		} else {
			if (products.getImage().isEmpty()) {
				products.setImage(null);
				categoryService.saveProduct(products);
			}
		}
		redirectAttributes.addFlashAttribute("message", "Product Saved Successfully.");
		return "redirect:/admin-manage-products";

	}

	@GetMapping("/admin-manage-products/{id}/enabled/{status}")
	public String enableStatus(@PathVariable(name = "id") Integer id, @PathVariable(name = "status") boolean enabled,
			Model model, RedirectAttributes redirectAttributes) {
		categoryService.checkEnabledStatus(id, enabled);
		String status = enabled ? "Enabled" : "Disabled";
		String message = "the product id " + id + " has been " + status;
		redirectAttributes.addFlashAttribute("message", message);
		return "redirect:/admin-manage-products";
	}

	@GetMapping("/admin-manage-products/edit/{id}")
	public String updateProduct(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
		try {
			Products products = categoryService.getProductById(id);
			model.addAttribute("products", products);
			model.addAttribute("pageTitle", "Update Product");
		} catch (Exception e) {
			redirectAttributes.addAttribute("message1", e.getMessage());
			return "redirect:/admin-manage-products";
		}
		return "update";
	}

	@GetMapping("/admin-manage-products/delete/{id}")
	public String deleteProduct(@PathVariable("id") int id, Model model, RedirectAttributes redirectAttributes) {
		try {
			categoryService.deleteProduct(id);
			redirectAttributes.addFlashAttribute("message", "product with id " + id + " delete successfully!");
		} catch (ProductNotFoundException e) {
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/admin-manage-products";
	}

}
