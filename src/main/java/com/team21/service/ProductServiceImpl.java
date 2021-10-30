package com.team21.service;

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
		System.out.print(name);
		if (product == null)
			throw new ProductMSException("Service.PRODUCT_DOES_NOT_EXISTS");

		ProductDTO productDTO = ProductDTO.createDTO(product);

		return productDTO;

	}

}
