package com.lucent.skeleton.service.shopify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lucent.skeleton.clients.dto.ShopifyCodeDTO;
import com.lucent.skeleton.clients.dto.ShopifyResponse;
import com.lucent.skeleton.clients.service.ShopifyClient;
import com.lucent.skeleton.config.ShopifyConfigReader;

@Service
public class ShopifyService {
    @Autowired
    ShopifyClient shopifyClient;

    @Autowired
    ShopifyConfigReader shopifyConfigReader;

    public ShopifyResponse getAccessToken(String myShopifyUrl, String code){
        String token_url = shopifyConfigReader.getToken_url();
        ShopifyCodeDTO shopifyCodeDTO = new ShopifyCodeDTO(shopifyConfigReader.getKey(), shopifyConfigReader.getSecret(), code);
        token_url = token_url.replace("{version}", shopifyConfigReader.getApi_version());
        String url = "https://"+myShopifyUrl + token_url;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String requestBody = objectMapper.writeValueAsString(shopifyCodeDTO);
            return shopifyClient.postDataToShopify(url,null,requestBody);
        } catch (JsonProcessingException e) {
            return new ShopifyResponse(true,e.getMessage(),0,0);
        }
    }

    public ShopifyResponse getStoreData(String myShopifyUrl, String token){
        String shop_url = shopifyConfigReader.getShop_url();
        shop_url = shop_url.replace("{version}", shopifyConfigReader.getApi_version());
        String url = "https://"+myShopifyUrl + shop_url;
        try {
            return shopifyClient.getDataFromShopify(url,token);
        } catch (Exception e) {
            return new ShopifyResponse(true,e.getMessage(),0,0);
        }
    }

    public ShopifyResponse addBilling(String myShopifyUrl, String token){
        String shop_url = shopifyConfigReader.getShop_url();
        shop_url = shop_url.replace("{version}", shopifyConfigReader.getApi_version());
        String url = "https://"+myShopifyUrl + shop_url;
        try {
            return shopifyClient.getDataFromShopify(url,token);
        } catch (Exception e) {
            return new ShopifyResponse(true,e.getMessage(),0,0);
        }
    }
}
