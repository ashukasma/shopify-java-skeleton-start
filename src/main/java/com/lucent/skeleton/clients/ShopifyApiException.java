package com.lucent.skeleton.clients;

public class ShopifyApiException extends Exception {
    public ShopifyApiException() {
        super();
    }
    public ShopifyApiException(String message, Throwable cause) {
        super(message, cause);
    }
    public ShopifyApiException(String message) {
        super(message);
    }
    public ShopifyApiException(Throwable cause) {
        super(cause);
    }
}
