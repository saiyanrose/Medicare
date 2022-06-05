package com.medicare.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.medicare.entity.Products;

public interface ProductRepository extends PagingAndSortingRepository<Products,Integer>{

	@Query("UPDATE Products u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id,boolean enabled);
	
	public Long countById(Integer id);
	
	@Query("SELECT p FROM Products p WHERE CONCAT(p.id, ' ',p.name, ' ',p.brand) LIKE %?1%")
	public Page<Products>findAll(String keyword,Pageable pageable);	
	
}
