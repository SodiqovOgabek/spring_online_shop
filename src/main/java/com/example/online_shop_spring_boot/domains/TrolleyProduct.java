package com.example.online_shop_spring_boot.domains;

import com.example.online_shop_spring_boot.enums.ProductSize;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class TrolleyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private AuthUser user;
    @OneToOne
    private Product product;
    @Enumerated(EnumType.STRING)
    private ProductSize size;
    private Integer theNumber;
    private Boolean isPurchased;
    private Timestamp createAt;

}
