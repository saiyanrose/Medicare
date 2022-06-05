package com.medicare.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.medicare.entity.Category;
import com.medicare.entity.Products;
import com.medicare.service.CategoryService;

@Controller
public class MainController {
	@Autowired
	private CategoryService categoryService;

	@RequestMapping("")
	public String index(Model model) {
		List<Category> category = categoryService.categoryItems();
		model.addAttribute("parent", category);
		return "index";
	}

	@GetMapping("/sub-category/{id}")
	public String subCategory(@PathVariable(name = "id") Integer id,Model model) {
		try {
			List<Category>category=categoryService.categoryItems();
			model.addAttribute("parent",category);
			Set<Products> subCategory=categoryService.getSubCategory(id);		
			model.addAttribute("sub",subCategory);
		}catch(Exception ex) {
			model.addAttribute("message",ex.getMessage());
		}
		return "index";
	}

	@RequestMapping("/about")
	public String about(Model model) {
		return "about";
	}

	@RequestMapping("/contact")
	public String contact(Model model) {
		return "about";
	}

}
