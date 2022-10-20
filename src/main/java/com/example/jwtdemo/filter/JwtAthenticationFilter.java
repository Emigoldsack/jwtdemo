package com.example.jwtdemo.filter;

import com.example.jwtdemo.service.CustomUserDetailService;
import com.example.jwtdemo.util.JwtUtil;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

//call this filter only once per request
@Component
public class JwtAthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
 
        //get the JWT token from request header
        //validate that JWT token
        String bearerToken = request.getHeader("Authorization");
        String username = null;
        String token = null;
        
        //check if token exist or has bearer text
        if(bearerToken != null && bearerToken.startsWith("Bearer ")){
           //extract JWT token from BearerToken
           token = bearerToken.substring(7);
           
           try{
               //extract userName from the token 
               username = jwtUtil.extractUsername(token);
               
               //get userDetails for this user
               UserDetails userDetails = customUserDetailService.loadUserByUsername(username);
               
               //security checks
               if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                   
                   UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                   upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   
                   SecurityContextHolder.getContext().setAuthentication(upat);
                   
               }else{
                   System.out.println("Invalid Token!!");
               }
               
           }catch(Exception ex){
               ex.printStackTrace();
           }
        }else{
            System.out.println("Invalid Bearer Token Format!!");
        }
        //if all is well forward the filter request to the request endpoint
        filterChain.doFilter(request, response);
    }
    
}
