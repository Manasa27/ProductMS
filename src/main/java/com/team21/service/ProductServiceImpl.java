package com.team21.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team21.dto.ProductDTO;
import com.team21.entity.ProductEntity;
import com.team21.exception.ProductMSException;
import com.team21.repository.ProductRepository;
import com.team21.validator.ProductValidator;

@Transactional
@Service(value = "productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	private static int productCount;

	static {
		productCount = 100;
	}

	// Adding Product to Application
	@Override
	public String addProduct(ProductDTO productDTO) throws ProductMSException {

		ProductEntity productEntity = productRepository.findByProductName(productDTO.getProductName());

		if (productEntity != null)
			throw new ProductMSException("Service.PRODUCT_ALREADY_EXISTS");

		ProductValidator.validateProduct(productDTO);

		productEntity = new ProductEntity();

		String id = "PROD" + productCount++;

		productEntity.setProdId(id);
		productEntity.setProductName(productDTO.getProductName());
		productEntity.setPrice(productDTO.getPrice());
		productEntity.setCategory(productDTO.getCategory());
		productEntity.setDescription(productDTO.getDescription());
		productEntity.setImage(productDTO.getImage());
		productEntity.setSubCategory(productDTO.getSubCategory());
		productEntity.setSellerId(productDTO.getSellerId());
		productEntity.setProductRating(productDTO.getProductRating());
		productEntity.setStock(productDTO.getStock());

		productRepository.save(productEntity);

		return productEntity.getProdId();
	}

	// Delete Product from Application
	@Override
	public String deleteProduct(String prodId) throws ProductMSException {

		ProductEntity productEntity = productRepository.findByProdId(prodId);

		if (productEntity == null)
			throw new ProductMSException("Service.CANNOT_DELETE_PRODUCT");

		productRepository.delete(productEntity);

		return "Product deleted successfully!";

	}

	// Get product by name
	@Override
	public ProductDTO getProductByName(String name) throws ProductMSException {

		ProductEntity product = productRepository.findByProductName(name);
		if (product == null)
			throw new ProductMSException("Service.PRODUCT_DOES_NOT_EXISTS");

		ProductDTO productDTO = ProductDTO.createDTO(product);

		return productDTO;

	}

	// Get product by category
	@Override
	public List<ProductDTO> getProductByCategory(String category) throws ProductMSException {
		List<ProductEntity> productEntities = productRepository.findByCategory(category);

		if (productEntities.isEmpty())
			throw new ProductMSException("Service.CATEGORY_ERROR");

		List<ProductDTO> productDTOs = new ArrayList<>();

		for (ProductEntity product : productEntities) {
			ProductDTO productDTO = ProductDTO.createDTO(product);

			productDTOs.add(productDTO);
		}
		return productDTOs;
	}

	// get product by Id
	@Override
	public ProductDTO getProductById(String id) throws ProductMSException {
		ProductEntity productEntity = productRepository.findByProdId(id);

		if (productEntity == null)
			throw new ProductMSException("Service.PRODUCT_DOES_NOT_EXISTS");

		ProductDTO productDTO = ProductDTO.createDTO(productEntity);

		return productDTO;
	}

	// Update Stock of Products
	@Override
	public Boolean updateStock(String prodId, Integer quantity) throws ProductMSException {

		Optional<ProductEntity> optional = productRepository.findById(prodId);
		ProductEntity product = optional.orElseThrow(() -> new ProductMSException("Product does not exist"));
		if (product.getStock() >= quantity) {
			product.setStock(product.getStock() - quantity);
			return true;
		}
		return false;
	}

	// Get all products
	@Override
	public List<ProductDTO> viewAllProducts() throws ProductMSException {
		List<ProductEntity> productEntities = productRepository.findAll();

		if (productEntities.isEmpty())
			throw new ProductMSException("There are no products to be shown.");

		List<ProductDTO> productDTOs = new ArrayList<>();

		productEntities.forEach(productEntity -> {
			ProductDTO productDTO = ProductDTO.createDTO(productEntity);
			productDTOs.add(productDTO);
		});

		return productDTOs;
	}

}
