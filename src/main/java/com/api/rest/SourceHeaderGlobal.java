/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.api.rest;

/**
 *
 * @author marco
 */
public final class SourceHeaderGlobal {


 ////////////// by mapvon

    
    public static final String HEADER_AUTHENTICATION = "Authentication";

    public static final String HEADER_AUTH_BEARER = "Bearer ";

    public static final String HEADER_AUTH_X = "X-Auth ";
    
     public static final String SECRET_KEY = "mySecret";  // #chiave segreta utilizzata da jwt
     
    public static final Long EXPIRATION_TOKEN =  new Long(7200);  // #periodo di validita del token(s) 7200=2h
    
    
    
}
