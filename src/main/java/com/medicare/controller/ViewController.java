package com.medicare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.medicare.entity.Category;
import com.medicare.entity.Products;
import com.medicare.service.CategoryService;

@Controller
public class ViewController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping("/view")
	public String view(Model model) {
		
		return viewByPage(model,1,null);
	}
	
	@GetMapping("/view/page/{pageNumber}")
	public String viewByPage(Model model,@PathVariable("pageNumber")int pageNum,@Param("keyword")String keyword) {
		Page<Products>products=categoryService.viewByPage(pageNum, keyword);
		List<Products>allProducts=products.getContent();
		long startCount = (pageNum - 1) * CategoryService.Product_Per_Page + 1;
		long endCount = startCount + CategoryService.Product_Per_Page - 1;
		if (endCount > products.getTotalElements()) {
			endCount = products.getTotalElements();
		}
		model.addAttribute("product",allProducts);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("pageNumber", pageNum);
		model.addAttribute("totalItems", products.getTotalElements());
		model.addAttribute("totalPage", products.getTotalPages());
		return "view";
	}

}
