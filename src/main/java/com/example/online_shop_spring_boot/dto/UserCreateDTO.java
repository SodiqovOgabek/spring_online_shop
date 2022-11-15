package com.example.online_shop_spring_boot.dto;


import com.example.online_shop_spring_boot.validator.PasswordMatches;
import com.example.online_shop_spring_boot.validator.UniqueUsername;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches(message = "Repeat password don't match")
public class UserCreateDTO {
    @UniqueUsername(message = "There is already user with this username!")
    @NotBlank(message = "Username can not be null")

    private String username;
//    @Pattern(regexp="(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$\"",message = "Enter a secure password: At least 8 characters long, containing uppercase and lowercase letters and numbers")
    @NotBlank(message = "Password can not be null")
    private String password;

    @NotBlank(message = "Repeat password can not be null")
//    @Pattern(regexp="(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$\"",message = "Enter a secure password: At least 8 characters long, containing uppercase and lowercase letters and numbers")

    private String repeatPassword;
}
