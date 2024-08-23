package com.my.ms.le.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue) {
		super(String.format("%s Not found with the given imput data %s : %s ", resourceName, fieldName, fieldValue));
	}
	
	// because the super method accept only one parameter
	// we are using String.format() for multiple params
	// %s in this place a value can be passed automatically
}
