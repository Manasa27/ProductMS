package com.team21.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.team21.dto.ProductDTO;
import com.team21.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private Environment environment;

	// Add Product in Application
	@PostMapping(value = "/product/add")
	public ResponseEntity<String> addProduct(@RequestBody ProductDTO productDTO) {

		try {
			String result = productService.addProduct(productDTO);
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(environment.getProperty(e.getMessage()), HttpStatus.UNAUTHORIZED);
		}

	}

	// Delete product from Application
	@DeleteMapping(value = "/product/delete/{prodId}")
	public ResponseEntity<String> deleteProduct(@PathVariable String prodId) {

		try {
			String result = productService.deleteProduct(prodId);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(environment.getProperty(e.getMessage()), HttpStatus.NOT_FOUND);
		}
	}

}
