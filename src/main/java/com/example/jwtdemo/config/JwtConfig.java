package com.example.jwtdemo.config;

import com.example.jwtdemo.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class JwtConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private CustomUserDetailService customUserDetailService;
    
    //here we say how we want to manage our authentication process
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailService);
    }

    //whit this method wi will control which endpoints are permitted and which are not
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
