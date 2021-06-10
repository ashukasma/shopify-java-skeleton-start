package com.lucent.skeleton.security;

import com.lucent.skeleton.config.ShopifyConfigReader;
import com.lucent.skeleton.entities.StoreUser;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author Ashish Kasma <ashukasma>
 */
@Component
public class ShopifyJwtTokenProvider {

    @Autowired
    ShopifyConfigReader shopifyConfigReader;

    public String getShopDomain(String token) {
        String shop = getClaimFromToken(token, Claims::getIssuer);
        if (shop != null) {
            URI uri = null;
            try {
                uri = new URI(shop);
                shop = uri.getHost();
            }
            catch (URISyntaxException ex) {

            }
        }
        return shop;
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setAllowedClockSkewSeconds(30).setSigningKey(shopifyConfigReader.getSecret().getBytes(Charset.forName("UTF-8"))).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getShopDomain(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
