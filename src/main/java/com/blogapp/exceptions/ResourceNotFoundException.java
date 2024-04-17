package com.blogapp.exceptions;

public class ResourceNotFoundException extends RuntimeException{
	String resourceName;
	String id;
	long fieldvalue;
	public ResourceNotFoundException(String resourceName, String id, long fieldvalue) {
		super(String.format("%s Not Found with %s : %s",resourceName,id,fieldvalue));
		this.resourceName = resourceName;
		this.id = id;
		this.fieldvalue = fieldvalue;
	}
}
