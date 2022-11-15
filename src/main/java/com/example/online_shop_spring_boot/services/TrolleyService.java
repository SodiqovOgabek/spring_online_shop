package com.example.online_shop_spring_boot.services;


import com.example.online_shop_spring_boot.configs.security.UserDetails;
import com.example.online_shop_spring_boot.domains.Product;
import com.example.online_shop_spring_boot.domains.TrolleyProduct;
import com.example.online_shop_spring_boot.dto.TrolleyProductCreateDTO;
import com.example.online_shop_spring_boot.enums.ProductSize;
import com.example.online_shop_spring_boot.repository.TrolleyRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrolleyService {
    private final TrolleyRepository rep;


    public void save(TrolleyProductCreateDTO dto, UserDetails userDetails, Optional<Product> product) {
        TrolleyProduct trolleyProduct = TrolleyProduct
                .builder ()
                .user ( userDetails.getUser () )
                .isPurchased ( false )
                .theNumber ( dto.getTheNumber () )
                .size ( ProductSize.findByName ( dto.getSize () ) )
                .product ( product.get () )
                .createAt ( Timestamp.valueOf ( LocalDateTime.now () ) )
                .build ();
        rep.save ( trolleyProduct );
    }


    public List<TrolleyProduct> findById(Long id) {
        return rep.trolleyAll ( id );
    }

    public TrolleyProduct findByProductId(Long product, Long user, ProductSize size) {
        return rep.findByProductUserId ( product, user, size );
    }

    public void updateTrolleyProduct(TrolleyProduct trolleyProduct, TrolleyProductCreateDTO dto) {


        TrolleyProduct trolley = TrolleyProduct
                .builder ()
                .id ( trolleyProduct.getId () )
                .user ( trolleyProduct.getUser () )
                .isPurchased ( false )
                .theNumber ( dto.getTheNumber () + trolleyProduct.getTheNumber () )
                .size ( ProductSize.findByName ( String.valueOf ( trolleyProduct.getSize () ) ) )
                .product ( trolleyProduct.getProduct () )
                .createAt ( Timestamp.valueOf ( LocalDateTime.now () ) )
                .build ();
        rep.save ( trolley );

    }


    public Optional<TrolleyProduct> get(Long id) {
        return rep.findById ( id );
    }


    public void delete(Long id, Integer theNumber) {
        Optional<TrolleyProduct> product = rep.findById ( id );
        if (product.get ().getTheNumber () > theNumber) {
            TrolleyProduct trolleyProduct = TrolleyProduct
                    .builder ()
                    .id ( id )
                    .isPurchased ( false )
                    .user ( product.get ().getUser () )
                    .product ( product.get ().getProduct () )
                    .theNumber ( product.get ().getTheNumber () - theNumber )
                    .size ( product.get ().getSize () )
                    .createAt ( Timestamp.valueOf ( LocalDateTime.now () ) )
                    .build ();
            rep.save ( trolleyProduct );
        } else {
            rep.deleteById ( id );
        }

    }

    public List<TrolleyProduct> findAll() {
        return rep.findAll ();
    }

    public Page<TrolleyProduct> productAll(@NonNull String searchQuery, Optional<Integer> page, Optional<Integer> limit) {
        int pa = page.orElse ( 0 );
        int size = limit.orElse ( 10 );
        Pageable pageable = PageRequest.of ( pa, size, Sort.by ( "id" ).descending () );
        return rep.findAll ( searchQuery.toLowerCase (), pageable );
    }

    public void update(UserDetails userDetails) {
        List<TrolleyProduct> trolleyProducts = rep.trolleyAll ( userDetails.getUser ().getId () );
        for (TrolleyProduct trolleyProduct : trolleyProducts) {
            TrolleyProduct product = TrolleyProduct
                    .builder ()
                    .id ( trolleyProduct.getId () )
                    .user ( trolleyProduct.getUser () )
                    .product ( trolleyProduct.getProduct () )
                    .size ( trolleyProduct.getSize () )
                    .theNumber ( trolleyProduct.getTheNumber () )
                    .isPurchased ( true )
                    .createAt ( Timestamp.valueOf ( LocalDateTime.now () ) )
                    .build ();
            rep.save ( product );
        }

    }

    public Page<TrolleyProduct> userProductAll(String searchQuery, Optional<Integer> page, Optional<Integer> limit, Long id) {
        int pa = page.orElse ( 0 );
        int size = limit.orElse ( 10 );
        Pageable pageable = PageRequest.of ( pa, size, Sort.by ( "id" ).descending () );
        return rep.userFindAll ( searchQuery.toLowerCase (), pageable, id );
    }
}
