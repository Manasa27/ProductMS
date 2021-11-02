package com.team21.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.team21.dto.BuyerDTO;
import com.team21.dto.ProductDTO;
import com.team21.dto.SubscribedProductDTO;
import com.team21.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private Environment environment;

	@Value("${user.uri}")
	String userUri;

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

	// Delete all products by specific seller
	@DeleteMapping(value = "products/seller/delete/{sellerId}")
	public ResponseEntity<String> deleteSellerProducts(@PathVariable String sellerId) {
		try {
			productService.deleteSellerProducts(sellerId);
			String result = "Product stock removed successfully";
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
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

	// Reduce Stock after ordering of Products
	@GetMapping(value = "/product/reduce/stock/{prodId}/{quantity}")
	public ResponseEntity<Boolean> reduceStock(@PathVariable String prodId, @PathVariable Integer quantity) {
		try {
			Boolean result = productService.reduceStock(prodId, quantity);
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()), e);
		}
	}

	// Update stock when seller increases stock
	@PutMapping(value = "/product/update/stock", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateStock(@RequestBody ProductDTO productDTO) {
		try {
			if (productService.updateStock(productDTO)) {
				return new ResponseEntity<String>("Stock updated successfully!", HttpStatus.OK);
			} else
				throw new Exception("Stock cannot be updated!");
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	// Get all products
	@GetMapping(value = "/product/viewAll")
	public ResponseEntity<List<ProductDTO>> viewAllProducts() {
		try {
			List<ProductDTO> list = productService.viewAllProducts();
			return new ResponseEntity<>(list, HttpStatus.OK);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	// Adding Subscription for buyer
	@PostMapping(value = "/product/subscriptions/add/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addSubscription(@RequestBody SubscribedProductDTO subscribedProductDTO) {
		try {
			BuyerDTO buyerDTO = new RestTemplate()
					.getForObject(userUri + "userMS/buyer/" + subscribedProductDTO.getBuyerId(), BuyerDTO.class);
			String result = productService.addSubscrption(subscribedProductDTO, buyerDTO);
			return new ResponseEntity<String>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(environment.getProperty(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}

	// get specific subscription details
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "/product/subscriptions/get/{buyerId}/{prodId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SubscribedProductDTO> getSpecificSubscription(@PathVariable String buyerId,
			@PathVariable String prodId) {
		try {
			SubscribedProductDTO s = productService.getSubscriptionDetails(buyerId, prodId);
			return new ResponseEntity<SubscribedProductDTO>(s, HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(environment.getProperty(e.getMessage()), HttpStatus.BAD_REQUEST);
		}

	}
}
