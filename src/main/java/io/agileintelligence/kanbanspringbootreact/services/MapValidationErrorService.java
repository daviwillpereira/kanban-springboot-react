package io.agileintelligence.kanbanspringbootreact.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
public class MapValidationErrorService {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptionsService(BindingResult result) {
		if (result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			result.getAllErrors().forEach((error) -> {
				String fieldName = ((FieldError) error).getField();
				String errorMessage = error.getDefaultMessage();
				errors.put(fieldName, errorMessage);
			});
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		return null;
	}
}
