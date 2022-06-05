package com.medicare;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.medicare.entity.Category;
import com.medicare.entity.Products;
import com.medicare.repository.CategoryRepository;
import com.medicare.repository.ProductRepository;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductsTest {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;
	
	@Test
	public void addCategoryTest() {
		Category category=new Category("Antibiotics");
		Category saveCategory=categoryRepository.save(category);
		assertThat(saveCategory.getId()).isGreaterThan(0);		
	}
	
	@Test
	public void addProductsTest() {
		Products products=new Products("default.jpg","Arthropan","netmeds",40,18,true);
		Products saveProducts=productRepository.save(products);
		assertThat(saveProducts.getId()).isGreaterThan(0);
	}
	
	@Test
	public void mapProductsTest() {
		Products products=productRepository.findById(6).get();		
		Category category=categoryRepository.findById(3).get();		
		category.addProduct(products);
		categoryRepository.save(category);
		
	}
	
	@Test
	public void getCategoryProductsTest() {
		Category category=categoryRepository.findById(3).get();	
		Set<Products>category1=category.getProducts();
		for(Products c:category1) {
			System.out.println(c.getName());
		}		
	}
	
	@Test
	public void pagingProductTest() {
		int pageNumber=1;
		int pageSize=4;
		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		Page<Products>products=productRepository.findAll(pageable);
		List<Products>content=products.getContent();
		for(Products p:content) {
			System.out.println(p.getName());
		}
	}
	
	
}
