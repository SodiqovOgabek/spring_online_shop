package com.example.online_shop_spring_boot.repository;

import com.example.online_shop_spring_boot.domains.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author "Sodiqov Ogabek"
 * @since 11/9/2022 3:54 PM (Wednesday)
 * online_shop_spring_boot/IntelliJ IDEA
 */
public interface ProductImgRepository extends JpaRepository<ProductImages,Long> {

    @Query("select t from ProductImages t where t.product.id=:id")
    ProductImages findByUserId(@Param ( "id" ) Long id);
}
