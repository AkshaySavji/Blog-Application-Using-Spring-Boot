package com.blogapp.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blogapp.entities.Category;
import com.blogapp.entities.Comment;
import com.blogapp.entities.User;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private Integer postId;
	
	@NotEmpty
	@Size(min=4,message = "It sould not be blank and must be greater than 4 characters")
	private String title;
	
	@NotEmpty
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;	
	
	private Set<CommentDto>comment=new HashSet<>();

}
