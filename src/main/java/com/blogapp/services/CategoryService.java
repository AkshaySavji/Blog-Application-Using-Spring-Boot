package com.blogapp.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.blogapp.payloads.CategoryDto;

public interface CategoryService {
	//Create 
	CategoryDto createCategory(CategoryDto categoryDto);

	//Update
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	//Delete
	public void deleteCategory(Integer categoryId);

	//get
	CategoryDto getCategory(Integer categoryId);
	
	//get all
	List<CategoryDto> getAllCategory();
	
	
}
