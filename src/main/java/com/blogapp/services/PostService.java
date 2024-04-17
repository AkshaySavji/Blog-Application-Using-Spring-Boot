package com.blogapp.services;

import java.util.List;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResposne;

public interface PostService {
	
	//Create Post
	PostDto createPost(PostDto postDto, Integer userId,Integer categoryId);
	
	//Update Post
	PostDto updatePost(PostDto postDto,Integer postId);
	
	//Delete Post
	void deletePost(Integer postId);
	
	//GetAllPosts
	PostResposne getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	//GetPostById
	PostDto getPostById(Integer postId);

	//GetAllPostByCategory
	List<PostDto>getPostsByCategory(Integer categoryId);
	
	//GetAllPostByUser
	List<PostDto>getPostsByUser(Integer userId);
	
	//Search Post
	List<PostDto>searchPost(String keyword);
	 
}
