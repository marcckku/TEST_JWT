package com.api.rest.jwt.controller.security;

import com.api.rest.SourceHeaderGlobal;
import com.api.rest.jwt.security.JwtAuthenticationRequest;
import com.api.rest.jwt.security.JwtTokenUtil;
import com.api.rest.jwt.service.JwtAuthenticationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
//import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class AuthenticationRestController {

   /// @Value("${jwt.header}")   /// NON FUNZIONA INJECTION QUI
 //  private final String tokenHeader="Baerer ";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    
  
    
    
   

    
    @RequestMapping(value = "public/login", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest, /**Device device,*/ HttpServletResponse response) throws AuthenticationException, JsonProcessingException {

        // Effettuo l autenticazione
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Genero Token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);  ////device
        
      
        
        response.setHeader(SourceHeaderGlobal.HEADER_AUTH_X,token);/// <<<<<<<<---------------  SourceHeaderGlobal.HEADER_AUTH_X by mpavon
        // Ritorno il token
        return ResponseEntity.ok(new JwtAuthenticationResponse(userDetails.getUsername(),userDetails.getAuthorities()));
    }

    @RequestMapping(value = "protected/refresh-token", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getHeader(SourceHeaderGlobal.HEADER_AUTH_X);/// <<<<<<<<---------------  SourceHeaderGlobal.HEADER_AUTH_X by mpavon
        UserDetails userDetails =
                (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (jwtTokenUtil.canTokenBeRefreshed(token)) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            response.setHeader(SourceHeaderGlobal.HEADER_AUTH_X,refreshedToken); //// <<<<<<<<---------------  SourceHeaderGlobal.HEADER_AUTH_X by mpavon

            return ResponseEntity.ok(new JwtAuthenticationResponse(userDetails.getUsername(),userDetails.getAuthorities()));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

}