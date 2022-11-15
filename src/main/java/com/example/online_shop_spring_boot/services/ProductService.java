package com.example.online_shop_spring_boot.services;


import com.example.online_shop_spring_boot.domains.Product;
import com.example.online_shop_spring_boot.domains.ProductImages;
import com.example.online_shop_spring_boot.domains.Uploads;
import com.example.online_shop_spring_boot.dto.ProductCreateDTO;
import com.example.online_shop_spring_boot.enums.Currency;
import com.example.online_shop_spring_boot.enums.Gender;
import com.example.online_shop_spring_boot.enums.ProductType;
import com.example.online_shop_spring_boot.repository.ProductImgRepository;
import com.example.online_shop_spring_boot.repository.ProductRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository rep;
    private final UploadsService service;

    private final ProductImgRepository productImgRepository;

    @Value("${img.upload.location}")
    String location;
    public void save(ProductCreateDTO dto) {
//        Uploads cover= service.upload(dto.getCover());



        String generatedImgName = manageImg(dto.getCover ());


        Product product = Product
                .builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .type( ProductType.findByName(dto.getType()))
                .gender( Gender.findByName(dto.getGender()))
                .currency( Currency.findByName(dto.getCurrency()))
                .cover ( generatedImgName )
                .build();
        Product save = rep.save ( product );
        addImg ( dto.getCover (),save.getId () );
    }

    private String manageImg(MultipartFile cover) {

        String generatedValue = String.valueOf(System.currentTimeMillis());
        String imageType = ".jpg";

        try {
            cover.transferTo(new File(location + generatedValue + imageType));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return generatedValue + imageType;

    }


    public List<Product> findAll() {
        return rep.findAll();
    }

    public void delete(Long id) {

        ProductImages byUserId = productImgRepository.findByUserId ( id );
        productImgRepository.deleteById ( byUserId.getId () );
        rep.deleteById(id);
    }

    public Optional<Product> get(Long id) {
        Optional<Product> product = rep.findById(id);
        return product;
    }

//    public List<Product> swatchAll(ProductType watch) {
//        int page=0;
//        int size=10;
//        Pageable pageable=PageRequest.of(page,size,Sort.by("id").descending());
//        return rep.swatchAll(watch);
//    }

    public List<Product> shoesAll(ProductType shoes) {
        int page=0;
        int size=4;
        Pageable pageable=PageRequest.of(page,size,Sort.by("id").descending());
        return rep.shoesAll(shoes,pageable);
    }

    public Page<Product> productAll(@NonNull String searchQuery, Optional<Integer> pageOptional, Optional<Integer> limitOptional) {
        int page = pageOptional.orElse(0);
        int size = limitOptional.orElse(8);
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return rep.findAll(searchQuery.toLowerCase(),pageable);
    }

    public Optional<Product> finById(Long id) {
        return rep.findById(id);
    }

    public void update(Long id, ProductCreateDTO dto) {

//        Uploads upload = service.upload(dto.getCover());
//        Product product= Product
//                .builder()
//                .id(id)
//                .name(dto.getName())
//                .currency(Currency.findByName(dto.getCurrency()))
//                .type(ProductType.findByName(dto.getType()))
//                .gender(Gender.findByName(dto.getGender()))
//                .price(dto.getPrice())
//                .cover(upload)
//                .build();
//        rep.save(product);
//    }

    }

    public void addImg(MultipartFile img, Long productId) {

        Optional<Product> byId = rep.findById ( productId );

        String generatedImgName = manageImg(img);
        ProductImages build = ProductImages.builder()
                .product(byId.get ())
                .img(generatedImgName)
                .build();

        productImgRepository.save(build);
    }
}
