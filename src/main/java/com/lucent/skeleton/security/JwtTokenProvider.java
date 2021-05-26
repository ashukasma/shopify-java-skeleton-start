package com.lucent.skeleton.security;

import com.lucent.skeleton.entities.StoreUser;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Ashish Kasma <ashukasma>
 */
@Component
public class JwtTokenProvider {

    public String generateToken(Authentication authentication) {
        StoreUser storeUser = (StoreUser) authentication.getPrincipal();
        Date now = new Date();
        Date expDate = new Date(now.getTime() + SecurityConstants.EXPIRATION_TIME);

        Map<String, Object> mapClaims = new HashMap<>();
        mapClaims.put("id", storeUser.getId().toString());
        mapClaims.put("username", storeUser.getUsername());


        return Jwts.builder()
                .setClaims(mapClaims)
                .setIssuedAt(now)
                .setExpiration(expDate)
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException | IllegalArgumentException | UnsupportedJwtException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    // get user id
    public Long getUserIdFromJwtToken(String token){
        Claims claims= Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(token).getBody();
        String id = (String) claims.get("id");
        return Long.parseLong(id);
    }
}
