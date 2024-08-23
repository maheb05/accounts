package com.my.ms.le.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Customer", description = "Schema to hold Customer and Account information")
@Data @AllArgsConstructor @NoArgsConstructor
public class CustomerDTO {
	
	@Schema(description = "Name of the customer",example = "Mehaboob")
	@NotEmpty(message = "Name can not be null or empty")
	@Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
	private String name;
	
	@Schema(description = "Email of the customer",example = "mehaboob@gmail.com")
	@NotEmpty(message = "Email can not be null or empty ")
	@Email(message = "Email address should be a valid value")
	private String email;
	
	@Schema(description = "Mobile Number",example = "1234567890")
	@NotEmpty(message = "Mobile Number can not be null or empty")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	private String mobileNumber;
	
	@Schema(description = "Account details of the customer")
	private AccountsDTO accountsDetails;
}
