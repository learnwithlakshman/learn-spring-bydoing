package com.careerit.scart.api;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.careerit.scart.service.excpetion.ProductAlreadyExistsException;

@RestControllerAdvice
public class GlobalExcpetionHandler {
	@ExceptionHandler({ IllegalArgumentException.class, ProductAlreadyExistsException.class })
	public ResponseEntity<?> handleException(Exception e, HttpServletRequest request) {
		ErrorMessage error = ErrorMessage.builder().status(HttpStatus.BAD_REQUEST).message(e.getMessage())
				.path(request.getRequestURI()).dateTime(LocalDateTime.now()).build();
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
}
