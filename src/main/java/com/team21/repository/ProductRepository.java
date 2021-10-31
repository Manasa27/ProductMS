package com.team21.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.team21.entity.ProductEntity;

public interface ProductRepository extends CrudRepository<ProductEntity, String> {

	public ProductEntity findByProdId(String id);

	public ProductEntity findByProductName(String name);

	public List<ProductEntity> findByCategory(String category);

	public List<ProductEntity> findAll();
	
    public List<ProductEntity> findAllBysellerId(String sellerId);

}
