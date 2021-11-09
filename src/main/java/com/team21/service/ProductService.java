package com.team21.service;

import java.util.List;

import com.team21.dto.BuyerDTO;
import com.team21.dto.ProductDTO;
import com.team21.dto.SubscribedProductDTO;
import com.team21.exception.ProductMSException;

public interface ProductService {

	public String addProduct(ProductDTO productDTO) throws ProductMSException;

	public String deleteProduct(String id) throws ProductMSException;

	public ProductDTO getProductByName(String name) throws ProductMSException;

	public List<ProductDTO> getProductByCategory(String category) throws ProductMSException;

	public ProductDTO getProductById(String id) throws ProductMSException;

	public Boolean reduceStock(String prodId, Integer quantity) throws ProductMSException;

	public Boolean updateStock(String productId, Integer quantity) throws ProductMSException;

	public List<ProductDTO> viewAllProducts() throws ProductMSException;

	public String addSubscrption(SubscribedProductDTO subscribedProductDTO, BuyerDTO buyerDTO)
			throws ProductMSException;

	public SubscribedProductDTO getSubscriptionDetails(String buyerId, String prodId) throws ProductMSException;

	public void deleteSellerProducts(String sellerId) throws ProductMSException;

	public String deleteProductofDeactiveSeller(String sellerId);

}
