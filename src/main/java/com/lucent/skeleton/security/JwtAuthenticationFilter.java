package com.lucent.skeleton.security;

import com.lucent.skeleton.entities.StoreUser;
import com.lucent.skeleton.service.CustomStoreUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @author Ashish Kasma <ashukasma>
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private ShopifyJwtTokenProvider tokenProvider;

    @Autowired
    private CustomStoreUserDetailsService customStoreUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain fc) throws ServletException, IOException {
        try {
            String jwtToken = getJwtFromRequest(httpServletRequest);

            String username = tokenProvider.getShopDomain(jwtToken);
            // Once we get the token validate it.
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = customStoreUserDetailsService.loadUserByUsername(username);
                // if token is valid configure Spring Security to manually set
                if (tokenProvider.validateToken(jwtToken, userDetails)) {
                    setAuthentication(httpServletRequest, userDetails);
                }
            }
        } catch (Exception e) {

        }
        fc.doFilter(httpServletRequest, httpServletResponse);
    }

    private void setAuthentication(HttpServletRequest request, UserDetails userDetails) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }

    private String getJwtFromRequest(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(SecurityConstants.HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
