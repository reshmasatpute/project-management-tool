package com.te.projectmanagementtool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.te.projectmanagementtool.util.APIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = RecordNotFoundException.class)
	public ResponseEntity<APIResponse> handleException(RecordNotFoundException exception) {
		return new ResponseEntity<>(
				APIResponse.builder().data(null).error(true).message(exception.getMessage()).build(),
				HttpStatus.BAD_REQUEST);
	}

}
