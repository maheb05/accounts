package com.my.ms.le.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.ms.le.constants.AccountsConstants;
import com.my.ms.le.dto.CustomerDTO;
import com.my.ms.le.dto.ErrorResponseDTO;
import com.my.ms.le.dto.ResponseDTO;
import com.my.ms.le.service.AccountsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@Tag(
		name = "CRUD REST APIs for Accounts in EazyBank",
		description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH AND DELETE account details"
		)
@RestController
@AllArgsConstructor
@Validated
@RequestMapping(path = "/api/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AccountsController {
	
	private AccountsService accountsService;
	
	@Operation(
			summary = "Create Account REST API",
			description = "REST API to create new Customer &  Account inside EazyBank"
			)
	@PostMapping("/createAccount")
	public ResponseEntity<ResponseDTO> createAccount(@RequestBody @Valid CustomerDTO customerDTO) {
		accountsService.createAccount(customerDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDTO(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
	}
	
	
	@Operation(
			summary = "Fetch Account Details REST API",
			description = "REST API to fetch Customer &  Account details based on a mobile number"
			)
	@GetMapping("/fetch")
	public ResponseEntity<CustomerDTO> getCustomerByMobileNumber(@RequestParam
			@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
			String mobileNumber) {
		
		CustomerDTO customerByMobileNumber = accountsService.getCustomerByMobileNumber(mobileNumber);
		return ResponseEntity.ok().body(customerByMobileNumber);
	}
	
	@Operation(summary = "Update Account Details REST API", description = "REST API to update Customer &  Account details based on a account number")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "HTTP Status OK"),
		@ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error"
						,content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
	})
	@PutMapping("/update")
	public ResponseEntity<ResponseDTO> updateAccountDeatils(@RequestBody @Valid CustomerDTO customerDTO) {
		
		boolean updateAccount = accountsService.updateAccount(customerDTO);
		if(updateAccount) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		}else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDTO( AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_UPDATE));
		}
	}
	
	@Operation(summary = "Delete Account & Customer Details REST API", description = "REST API to delete Customer &  Account details based on a mobile number")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "HTTP Status OK"),
		@ApiResponse(responseCode = "500", description = "HTTP Status Internal Status Error"
						,content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
	})
	@DeleteMapping("/deleteAccount")
	public ResponseEntity<ResponseDTO> deleteAccount(@RequestParam
			@Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
			String mobileNumber){
		
		boolean account = accountsService.deleteAccount(mobileNumber);
		if(account) {
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
		}else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseDTO(AccountsConstants.STATUS_417, AccountsConstants.MESSAGE_417_DELETE));
		}
	}

}
