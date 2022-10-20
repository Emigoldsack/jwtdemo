package com.example.jwtdemo.config;

import com.example.jwtdemo.filter.JwtAthenticationFilter;
import com.example.jwtdemo.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class JwtConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private CustomUserDetailService customUserDetailService;
    
    @Autowired
    private JwtAthenticationFilter jwtFilter;
    
    //here we say how we want to manage our authentication process
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailService);
    }

    //whit this method we will control which endpoints are permitted and which are not
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .disable()
                .authorizeRequests()
                .antMatchers("/api/generateToken").permitAll()//only alowed this endpoint without athentication
                .anyRequest().authenticated()//for any other request, authentication should be performed
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//every request should be independent of other and server does not have to manage session
        
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
    
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
       return super.authenticationManagerBean();
    }
    
    
}
