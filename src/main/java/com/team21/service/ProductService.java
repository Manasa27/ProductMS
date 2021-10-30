package com.team21.service;

import java.util.List;

import com.team21.dto.ProductDTO;
import com.team21.exception.ProductMSException;

public interface ProductService {

	public String addProduct(ProductDTO productDTO) throws ProductMSException;

	public String deleteProduct(String id) throws ProductMSException;

	public ProductDTO getProductByName(String name) throws ProductMSException;

	public List<ProductDTO> getProductByCategory(String category) throws ProductMSException;

}
