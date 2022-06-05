package com.medicare.service;

public class ProductNotFoundException extends Exception {
	public ProductNotFoundException(String message) {
		super(message);
	}
}
