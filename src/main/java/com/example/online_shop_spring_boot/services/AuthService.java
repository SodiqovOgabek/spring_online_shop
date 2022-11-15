package com.example.online_shop_spring_boot.services;

import com.example.online_shop_spring_boot.configs.security.UserDetails;
import com.example.online_shop_spring_boot.domains.AuthRole;
import com.example.online_shop_spring_boot.domains.AuthUser;
import com.example.online_shop_spring_boot.dto.UserCreateDTO;
import com.example.online_shop_spring_boot.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authRepository.findByUsername ( username ).orElseThrow (
                () -> new UsernameNotFoundException ( "User not found" )
        );
        return new UserDetails ( authUser );
    }

    public void generate(UserCreateDTO dto) {

        AuthUser user = AuthUser
                .builder ()
                .username ( dto.getUsername () )
                .password ( passwordEncoder.encode ( dto.getPassword () ) )
                .repeatPassword ( passwordEncoder.encode ( dto.getRepeatPassword () ) )
                .active ( true )
                .roles ( List.of () )
                .build ();
        authRepository.save ( user );
    }
}
