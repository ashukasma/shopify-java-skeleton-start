package com.lucent.skeleton.service.shopify.dto.enums;

public enum ShopifyDiscountType {
    PERCENTAGE("percentage", "Percentage"),
    FIXED("fixed_amount", "Fixed");

    private final String code;
    private final String message;

    ShopifyDiscountType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public static ShopifyDiscountType findByMessage(String message) {
        for (ShopifyDiscountType shopifyDiscountType : ShopifyDiscountType.values()) {
            if (shopifyDiscountType.getMessage().equalsIgnoreCase(message)) {
                return shopifyDiscountType;
            }
        }
        return null;
    }
}
