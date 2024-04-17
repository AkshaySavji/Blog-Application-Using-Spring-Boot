package com.blogapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.entities.Category;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.services.CategoryService;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	@PostMapping("/")
	public ResponseEntity<CategoryDto>createCategory(@RequestBody CategoryDto categoryDto){
		System.out.println("Creating Catgory....!!");
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	@GetMapping("/")
	public ResponseEntity <List<CategoryDto>> getAllCategory(){
		List<CategoryDto> list=this.categoryService.getAllCategory();
		return ResponseEntity.ok(list);
	}
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto>getCategoryById(@PathVariable("id")int id){
		CategoryDto categoryDto= this.categoryService.getCategory(id);
		return new ResponseEntity<CategoryDto>(categoryDto, HttpStatus.OK);
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("id")int id){
		this.categoryService.deleteCategory(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true),HttpStatus.OK);
	}
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto>updateCategory(@PathVariable("id")int id,@RequestBody CategoryDto categoryDto){
		System.out.println("Category  ::"+categoryDto.toString());
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, id);
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.ACCEPTED); 
	}
}









