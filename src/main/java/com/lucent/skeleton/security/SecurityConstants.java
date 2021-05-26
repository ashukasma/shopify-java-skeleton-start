package com.lucent.skeleton.security;


/**
 *
 * @author ashish
 */

public class SecurityConstants {
    public final static String SECRET = "secretkeytogeneratetoken";
    public final static String TOKEN_PREFIX = "Bearer ";
    public final static String HEADER_STRING = "Authorization";
    public final static long EXPIRATION_TIME = 3*20*60*1000; // 20min * 60s * 1000
}

