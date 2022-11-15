package com.example.online_shop_spring_boot.repository;

import com.example.online_shop_spring_boot.domains.Product;
import com.example.online_shop_spring_boot.enums.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "from Product t where lower(t.name) like %:query% or lower(t.gender) like %:query% or lower(t.type) like %:query%")
    Page<Product> findAll(@Param("query") String searchParam, Pageable pageable);

    @Query(value = " from Product t where t.type = :watch")
    List<Product> shoesAll(@Param("watch") ProductType shoes, Pageable pageable);
}
