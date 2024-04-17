package com.blogapp.services;

import java.util.List;

import com.blogapp.payloads.UserDto;

public interface UserService{
	UserDto createUser(UserDto userDto);
	UserDto updateUser(UserDto userDto, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto>getAllUsers(int pageNumber,int pageSize,String sortDir,String sortBy);
	void deleteUser(Integer userId);
}
