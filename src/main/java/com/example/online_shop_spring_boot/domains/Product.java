package com.example.online_shop_spring_boot.domains;

import com.example.online_shop_spring_boot.enums.Currency;
import com.example.online_shop_spring_boot.enums.Gender;
import com.example.online_shop_spring_boot.enums.ProductType;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String name;
    private String price;
    @Enumerated(EnumType.STRING)
    private Currency currency;
    @Enumerated(EnumType.STRING)
    private ProductType type;

    private String cover;
//    @OneToOne
//    private Uploads cover;


    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "product")
    private List<ProductImages> images;
}

