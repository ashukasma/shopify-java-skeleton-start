package com.lucent.skeleton.exception;

import com.lucent.skeleton.exception.enums.ShopifyExceptionEnum;

public class ShopifyException extends Exception{

    private final ShopifyExceptionEnum shopifyExceptionEnum;

    public ShopifyException(ShopifyExceptionEnum shopifyExceptionEnum) {
        super();
        this.shopifyExceptionEnum = shopifyExceptionEnum;
    }

    public ShopifyExceptionEnum getException() { return shopifyExceptionEnum; }
}
