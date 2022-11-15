package com.example.online_shop_spring_boot.configs.security;

import com.example.online_shop_spring_boot.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@ComponentScan("com.example.online_shop_spring_boot")
@EnableWebSecurity
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    public static final String[] WHITE_LIST = new String[]{
            "/",
            "/home",
            "/auth/login",
            "/auth/register",
            "/shop",
            "/static/**",
            "/showCover/**"
    };

    @Value("${spring.security.rememberme.secret.key}")
    public String SECRET_KEY;

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf ().disable ()
                .authorizeRequests ( expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry

                                .antMatchers ( WHITE_LIST )
                                .permitAll ()
                                .anyRequest ()
                                .authenticated () )
                .formLogin (
                        httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                                .loginPage ( "/auth/login" )
                                .loginProcessingUrl ( "/auth/login" )
                                .defaultSuccessUrl ( "/home/", true ) )

                .logout ( httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer
                                .logoutRequestMatcher (
                                        new AntPathRequestMatcher ( "/auth/logout", "POST", true )
                                )
                                .logoutSuccessUrl ( "/auth/login" )
                                .deleteCookies ( "JSESSIONID", "remember" )
                                .clearAuthentication ( true )
                                .invalidateHttpSession ( true )
                                .logoutSuccessUrl ( "/home" )
                );

    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService ( authService )
                .passwordEncoder ( passwordEncoder );
    }

//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }
}
