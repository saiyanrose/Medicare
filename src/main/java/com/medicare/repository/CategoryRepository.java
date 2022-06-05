package com.medicare.repository;

import org.springframework.data.repository.CrudRepository;

import com.medicare.entity.Category;

public interface CategoryRepository extends CrudRepository<Category,Integer> {
	
}
