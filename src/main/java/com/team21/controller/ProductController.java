package com.team21.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

	// Get product by name
	@GetMapping(value = "/product/get/name/{name}")
	public ResponseEntity<ProductDTO> getByProductName(@PathVariable String name) {
		try {
			ProductDTO productDTO = productService.getProductByName(name);
			return new ResponseEntity<>(productDTO, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}

	// Get product by category
	@GetMapping(value = "/product/get/category/{category}")
	public ResponseEntity<List<ProductDTO>> getByProductCategory(@PathVariable String category) {
		try {
			List<ProductDTO> productDTO = productService.getProductByCategory(category);
			return new ResponseEntity<List<ProductDTO>>(productDTO, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}

	// Get product by IdS
	@GetMapping(value = "/product/get/Id/{prodId}")
	public ResponseEntity<ProductDTO> getByProductId(@PathVariable String prodId) {
		try {
			ProductDTO productDTO = productService.getProductById(prodId);
			return new ResponseEntity<>(productDTO, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}

	// Update Stock of Products
	@GetMapping(value = "/product/update/stock/{prodId}/{quantity}")
	public ResponseEntity<Boolean> updateStock(@PathVariable String prodId, @PathVariable Integer quantity) {
		try {
			Boolean result = productService.updateStock(prodId, quantity);
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}
}
