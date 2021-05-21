package com.careerit.scart.service.excpetion;

public class ProductAlreadyExistsException extends RuntimeException {

	public ProductAlreadyExistsException(String message) {
		super(message);
	}
}
