package com.example.jwtdemo.controller;

import com.example.jwtdemo.model.JwtRequest;
import com.example.jwtdemo.model.JwtResponse;
import com.example.jwtdemo.service.CustomUserDetailService;
import com.example.jwtdemo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JwtController {
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private CustomUserDetailService customUserDetailService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/generateToken")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest){
        
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(jwtRequest.getUserName(), jwtRequest.getPassword());
        
        //authenticate the user
        authenticationManager.authenticate(upat);
        
        UserDetails userDetails = customUserDetailService.loadUserByUsername(jwtRequest.getUserName());
        
        String jwtToken = jwtUtil.generateToken(userDetails);
        
        JwtResponse jwtResponse = new JwtResponse(jwtToken);
        //return ResponseEntity.ok(jwtResponse);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
    
}
