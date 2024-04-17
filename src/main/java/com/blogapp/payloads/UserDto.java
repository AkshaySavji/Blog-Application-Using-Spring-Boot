package com.blogapp.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
	
	private int id;
	@NotEmpty
	@Size(min=4,message="It sould not be blank and must be greater than 4 characters")
	private String name;
	
	@Email
	private String email;	

	@NotEmpty
	private String password;
	
	@NotEmpty
	private String about;
}
