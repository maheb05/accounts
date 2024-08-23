package com.my.ms.le.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.my.ms.le.dto.ErrorResponseDTO;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		HashMap<String, String> ValidationErrors = new HashMap<>();
		List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
		allErrors.forEach( (error) -> {
			String fieldName = ((FieldError) error).getField();
			String defaultMessage = error.getDefaultMessage();
			ValidationErrors.put(fieldName, defaultMessage);
		} );
		
		return new ResponseEntity<>(ValidationErrors,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleGlobalExceptin(Exception exception, WebRequest webRequest){
		
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(webRequest.getDescription(false)
				, HttpStatus.INTERNAL_SERVER_ERROR
				, exception.getMessage()
				, LocalDateTime.now());
		return new ResponseEntity<>(errorResponseDTO,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(CustomerAlreadyExistException.class)
	public ResponseEntity<ErrorResponseDTO> handleCustomerAlreadyExistException(CustomerAlreadyExistException exception,
			WebRequest webRequest) {
		
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(webRequest.getDescription(false)
				, HttpStatus.BAD_REQUEST
				, exception.getMessage()
				, LocalDateTime.now());
		
		return new ResponseEntity<>(errorResponseDTO,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handleResourceNotFoundException(ResourceNotFoundException exception,
			WebRequest webRequest){
		
		ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(webRequest.getDescription(false)
				, HttpStatus.NOT_FOUND
				,exception.getMessage()
				, LocalDateTime.now());
		
		return new ResponseEntity<ErrorResponseDTO>(errorResponseDTO, HttpStatus.NOT_FOUND);
	}
}
