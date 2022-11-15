package com.example.online_shop_spring_boot.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;


@Getter

@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserLoginDTO {
    @NotBlank(message = "Username can not be null")
    private String username;
    @NotBlank(message = "Password can not be null")
    private String password;
}
