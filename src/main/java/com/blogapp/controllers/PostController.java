package com.blogapp.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogapp.config.AppConstants;
import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResposne;
import com.blogapp.services.FileService;
import com.blogapp.services.PostService;
import com.blogapp.services.impl.FileServiceImpl;

@RestController
@RequestMapping("/api/")
public class PostController {

	@Autowired	
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto>createPost(@RequestBody PostDto postDto,
			@PathVariable Integer userId,
			@PathVariable Integer categoryId){
		System.out.println("userId"+userId+"categoryId"+categoryId);
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost,HttpStatus.OK);
	}
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>>getPostsByUser(@PathVariable Integer userId){
		List<PostDto> postsByUser = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postsByUser,HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>>getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> postsByCategory = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postsByCategory,HttpStatus.OK);
	}
	
	@GetMapping("/posts/")
	public ResponseEntity<PostResposne>getAllPosts
					(@RequestParam(value="pageNumber",defaultValue = AppConstants.ZERO,required = false) Integer pageNumber,
					@RequestParam(value = "pageSize",defaultValue = AppConstants.ONE, required = false) Integer pageSize,
					@RequestParam(value = "sortBy",defaultValue = AppConstants.POST_ID,required = false)String sortBy,
					@RequestParam(value = "sortDir",defaultValue = AppConstants.ASCEDING_ORDER,required = false)String sortDir
					)
	{
		System.out.println("pageNumber:" +pageNumber);
		System.out.println("pageSize"+pageSize);
		PostResposne postResposne = this.postService.getAllPost(pageNumber,pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResposne>(postResposne,HttpStatus.OK);
	}
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postById = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postById,HttpStatus.OK);
	}
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePostById(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ApiResponse("Deleted Succesfully",true);
	}
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto>updatePost(@RequestBody PostDto postDto,@PathVariable("postId")Integer postId){
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	//Search Post
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>>searchPost(@PathVariable String keyword){
		System.out.println("keyword: "+keyword);
		List<PostDto> dtoPostList = this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(dtoPostList,HttpStatus.OK);
	}
	
	//PostImageUpload
	@Value("${project.image}")
	private String path;
	
	
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto>uploadPostImage(@RequestParam("image")MultipartFile image,
				@PathVariable Integer postId) throws IOException{
		String p="D:\\Workspace\\springbootBlogApplication\\blog-app-api\\src\\main\\resources\\static\\images";
		
		System.out.println("path"+path);
		PostDto postDto = this.postService.getPostById(postId);	
		String name=this.fileService.uploadImage(p, image);
			System.out.println("Name: "+name);
			postDto.setImageName(name);
			System.out.println(postDto.getImageName());
			PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
}
