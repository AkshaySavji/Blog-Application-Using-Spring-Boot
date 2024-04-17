package com.blogapp.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.UserRepo;
import com.blogapp.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		System.out.println("Creating User...!!");
		User user=this.dtoToUser(userDto);
		User savedUsed=this.userRepo.save(user);
		return userToDto(savedUsed);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," Id",userId));
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		user.setPassword(userDto.getPassword());
		User updateduser=this.userRepo.save(user);
		return this.userToDto(updateduser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers(int pageNumber,int pageSize,String sortDir,String sortBy) {
		Sort sort=null;
		sort= sortDir.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable=PageRequest.of(pageNumber, pageSize, sort);
		Page<User> pageUser = this.userRepo.findAll(pageable);
		
		List<User> userList = pageUser.getContent();
		List<UserDto>userDtoList=new ArrayList<UserDto>();
		for(User user : userList) {
			UserDto userDto=this.userToDto(user);
			userDtoList.add(userDto);
		}
		return userDtoList;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		this.userRepo.delete(user);
	}
	public User dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		return user; 
	}
	public UserDto userToDto(User user) {
 		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		return userDto;
	}
}
