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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.blogapp.payloads.*;
import com.blogapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//Create User
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		System.out.println("CREATING USER "+userDto.toString());
		UserDto createdUserDto= this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}
	
	//Update User
	@PutMapping("/{id}")
	public ResponseEntity<UserDto>updateUser(@RequestBody UserDto dto, @PathVariable("id")int id){
		System.out.println("Updating User.....!!"+dto.toString()+" id"+id);
		UserDto updatedUser=this.userService.updateUser(dto, id);
		return new ResponseEntity<UserDto>(updatedUser,HttpStatus.CREATED);
	}
	
	//Get User By id
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("id")int id){
		System.out.println("Get user by id"+ id);
		UserDto userDto=this.userService.getUserById(id);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
	}
	//Delete User
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse>deleteUser(@PathVariable("id")int id){
		System.out.println("Deleting user"+ id);
		this.userService.deleteUser(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true),HttpStatus.OK); 
	}
	
	//Get All User
	@GetMapping("/")
	public ResponseEntity <List<UserDto>>getAllUsers(
			@RequestParam(value = "pageNumber",defaultValue = "1",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "1",required = false)Integer pageSize,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir,
			@RequestParam(value = "sortBy",defaultValue = "id",required = false)String sortBy
			){
		
		System.out.println("pageNumber"+pageNumber+" pageSize"+pageSize+" sortDir"+sortDir+" sortBy"+sortBy);

		System.out.println("get All Users");
		List<UserDto>list=this.userService.getAllUsers(pageNumber,pageSize,sortDir,sortBy);
		return new ResponseEntity<List<UserDto>>(list,HttpStatus.OK);
	}
}
