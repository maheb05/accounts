package com.my.ms.le.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(name = "Account",description = "Schema to hold Account information")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountsDTO {
	
	@Schema(description = "Account Number of Eazy Bank account", example = "1234560000")
	@NotEmpty(message = "AccountNumber can not be a null or empty")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "AccountNumber must be 10 digits")
	private Long accountNumber;
	
	@Schema(description = "Account type of Eazy Bank account", example = "savings")
	@NotEmpty(message = "Account type can not be empty")
	private String accountType;
	
	@Schema(description = "Eazy Bank branch address", example = "123 New York")
	@NotEmpty(message = "Branch address can not be empty")
	private String branchAddress;
}
