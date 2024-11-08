package com.forever.app.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;


@Repository
@Transactional
public interface ProductRepo extends JpaRepository<Product, Long>{

}
