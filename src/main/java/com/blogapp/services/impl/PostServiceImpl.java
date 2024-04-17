package com.blogapp.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResposne;
import com.blogapp.repositories.CategoryRepository;
import com.blogapp.repositories.PostRepo;
import com.blogapp.repositories.UserRepo;
import com.blogapp.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public PostDto createPost(PostDto postDto,Integer userId,Integer categoryId) {
		
		User user=userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id", userId));
		
		Category category=categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Id", categoryId));
		Post post=(modelMapper.map(postDto, Post.class));
		post.setImageName("default.jpg");
		post.setAddedDate(new Date());
		post.setCategory(category);
		post.setUser(user);
		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		System.out.println("UPDATEPOST");
		 Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		
		 post.setTitle(postDto.getTitle());
		 post.setContent(postDto.getContent());
		 post.setImageName(postDto.getImageName());
		 Post UpdatedPost = this.postRepo.save(post);
		return this.modelMapper.map(UpdatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		this.postRepo.deleteById(postId);
	}

	@Override
	public PostResposne getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDir) {
		PostResposne postResposne=new PostResposne();
		
		Sort sort=null;
		sort=sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		System.out.print("sort:  "+sort);
		
		Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post>allPosts=pagePost.getContent();
		List<PostDto>dtoList=new ArrayList<>();
		for (Post post : allPosts) {
			dtoList.add(this.modelMapper.map(post,PostDto.class));
		}
		postResposne.setContent(dtoList);
		postResposne.setPageNumber(pagePost.getNumber());
		postResposne.setPageSize(pagePost.getSize());
		postResposne.setTotalElements(pagePost.getNumberOfElements());
		postResposne.setTotalPages(pagePost.getTotalPages());
		postResposne.setLastPage(pagePost.isLast());
		return postResposne;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		   Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category=this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "id", categoryId));
		List<Post>posts= this.postRepo.findByCategory(category);
		List<PostDto>postDtoList=new ArrayList<>();
		
		for (Post post : posts) {
			PostDto dto=this.modelMapper.map(post, PostDto.class);
			postDtoList.add(dto);
		}
		return postDtoList;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "id", userId));
		List<Post>post=this.postRepo.findByUser(user);
		
		List<PostDto>dtoList=new ArrayList<>();
		for (Post post2 : post) {
			PostDto dto= this.modelMapper.map(post2, PostDto.class);
			dtoList.add(dto);
		}
		return dtoList;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> findByTitleContaining = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDto =new ArrayList<>();
		for (Post post : findByTitleContaining) {
			 postDto.add(this.modelMapper.map(post, PostDto.class));
		}
		return postDto;
	}

}
