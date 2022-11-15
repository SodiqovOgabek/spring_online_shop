package com.example.online_shop_spring_boot.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductCreateDTO {
    private String gender;
    private String name;
    private String price;
    private String currency;
    private String type;
    private MultipartFile cover;
}
