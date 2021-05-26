package com.lucent.skeleton.clients.service;

import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import com.lucent.skeleton.clients.dto.ShopifyResponse;
import com.lucent.skeleton.dto.store.StoreDetailsDTO;

import java.util.Objects;

@Service
public class ShopifyClient {

    private static final Logger logger = LogManager.getLogger(ShopifyClient.class);

    private static final String FORM_URLENCODED_TYPE = "application/json; charset=UTF-8";
    private static final String CONTENT_TYPE = "Content-Type";

    private static final String USER_AGENT_STRING = "User-Agent";
    private static final String USER_AGENT_VALUE = "Google Chrome";

    private static final String TOKEN_STRING = "X-Shopify-Access-Token";
    private static final String CALL_LIMIT_STRING = "Retry-After";
    private static final String RETRY_STRING = "X-Shopify-Access-Token";

    public ShopifyResponse getDataFromShopify(String url, String token) {
        return makeCallToShopify("GET", url, token, null);
    }

    public ShopifyResponse postDataToShopify(String url, String token, String requestPayload) {
        return makeCallToShopify("POST", url, token, requestPayload);
    }

    public ShopifyResponse updateDataToShopify(String url, String token, String requestPayload) {
        return makeCallToShopify("PUT", url, token, requestPayload);
    }

    public ShopifyResponse deleteDataFromShopify(String url, String token) {
        return makeCallToShopify("DELETE", url, token, null);
    }

    public ShopifyResponse makeCallToShopify(String method, String url, String token, String requestPayload) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse(FORM_URLENCODED_TYPE);
        RequestBody body = null;
        if (requestPayload != null && !requestPayload.isEmpty()) {
            body = RequestBody.create(requestPayload, mediaType);
        }
        Request.Builder builder = new Request.Builder()
                .url(url)
                .method(method, body)
                .addHeader(CONTENT_TYPE, FORM_URLENCODED_TYPE)
                .addHeader(USER_AGENT_STRING, USER_AGENT_VALUE);
        if (token != null && !token.isEmpty()) {
            builder.addHeader(TOKEN_STRING, token);
        }
        Request request = builder.build();
        Response response;
        Integer limit = 0;
        Integer retryAfter = 0;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                logger.info("[ShopifyClient] API called successfully, response: {}", response.message());
                String responseString = Objects.requireNonNull(response.body()).string();
                limit = Integer.getInteger(response.header(TOKEN_STRING));
                retryAfter = Integer.getInteger(response.header(RETRY_STRING));
                logger.info("[ShopifyClient] API json responese: {}", responseString);
                return new ShopifyResponse(true, responseString, limit, retryAfter);
            } else {
                String responseString = Objects.requireNonNull(response.body()).string();
                System.out.println(responseString);
                limit = Integer.getInteger(response.header(TOKEN_STRING));
                retryAfter = Integer.getInteger(response.header(RETRY_STRING));
                logger.error("[ShopifyClient] Error in Shopify API, response: {}", response.message());
                return new ShopifyResponse(false, response.message() + " : " + responseString, limit, retryAfter);
            }

        } catch (Exception e) {
            logger.error("[ShopifyClient] Error in shopify calling shopify API", e);
            logger.error(e, e);
            return new ShopifyResponse(false, null, limit, retryAfter);
        }
    }

    public StoreDetailsDTO getStoreData(String myShopifyUrl) {
        return null;
    }
}
