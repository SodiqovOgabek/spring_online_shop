package com.example.online_shop_spring_boot.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TrolleyProductCreateDTO {
   private String size;
   private  Integer theNumber;
}
