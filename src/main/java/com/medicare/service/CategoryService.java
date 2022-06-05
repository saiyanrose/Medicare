package com.medicare.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.medicare.entity.Category;
import com.medicare.entity.Products;
import com.medicare.repository.CategoryRepository;
import com.medicare.repository.ProductRepository;

@Service
@Transactional
public class CategoryService {
	public static final int Product_Per_Page=4;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;

	public List<Category> categoryItems() {		
		return (List<Category>) categoryRepository.findAll();
	}
	
	public List<Products> productLists(){
		return (List<Products>) productRepository.findAll();
	}
	
	public Set<Products> getSubCategory(int id){
		Category category=categoryRepository.findById(id).get();
		Set<Products>subCategory=category.getProducts();		
		return subCategory;
	}
	
	public void checkEnabledStatus(Integer id,boolean enabled) {
		productRepository.updateEnabledStatus(id, enabled);
	}

	public Products getProductById(int id) {		
		return productRepository.findById(id).get();
	}
	
	public Products saveProduct(Products products) {
		boolean isUpdating=(products.getId()!=0);
		if(isUpdating) {
			Products existingProduct=productRepository.findById(products.getId()).get();
		}
		return productRepository.save(products);
	}
	
	public void deleteProduct(int id) throws ProductNotFoundException {
		Long products =productRepository.countById(id);
		if(products==0 || products==null) {
			throw new ProductNotFoundException("Product Not Found");			 
		}
		productRepository.deleteById(id);
	}

	public Page<Products> listByPage(int pageNumber,String sortField,String sortDir,String keyword) {
		Sort sort=Sort.by(sortField);
		sort=sortDir.equals("asc") ? sort.ascending() : sort.descending();
		Pageable pageable=PageRequest.of(pageNumber-1,Product_Per_Page,sort);		
		if(keyword!=null) {
			return productRepository.findAll(keyword, pageable);
		}
		return productRepository.findAll(pageable);
	}
	public Page<Products> viewByPage(int pageNumber,String keyword) {		
		Pageable pageable=PageRequest.of(pageNumber-1,Product_Per_Page);		
		if(keyword!=null) {
			return productRepository.findAll(keyword, pageable);
		}
		return productRepository.findAll(pageable);
	}	
	
}
