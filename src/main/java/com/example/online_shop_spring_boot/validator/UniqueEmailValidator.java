package com.example.online_shop_spring_boot.validator;

import com.example.online_shop_spring_boot.domains.AuthUser;
import com.example.online_shop_spring_boot.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private AuthRepository repository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<AuthUser> byUsername = repository.findByUsername(value);
        return byUsername.isEmpty();
    }

}
