package com.example.jwtdemo.service;


import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    //this method acctually does the validation for user existence
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        
        //here you can make a DB call whit the help of repository and do the validation 
        if(userName.equals("John")){
           return new User("Jonh", "secret", new ArrayList<>()); 
        }else{
            throw new UsernameNotFoundException("User does not exist!");
        }
    }
    
}
