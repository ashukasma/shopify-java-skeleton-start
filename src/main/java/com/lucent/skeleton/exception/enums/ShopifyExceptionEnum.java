package com.lucent.skeleton.exception.enums;


public enum ShopifyExceptionEnum {

    EMPTY_REQUEST(1, "Empty request"),
    INVALID_STORE(2, "Invalid store"),
    INVALID_NUMBER(3, "Invalid number"),
    EMPTY_CUSTOMER(4, "Empty customer object"),
    EMPTY_ADDRESS(5, "Empty shipping and billing address object"),
    NO_CHANNELS(6, "No message channels associated with store"),
    NO_PRICING(7, "No pricing details available"),
    NO_FULFILLMENT(8, "No fulfillment object available"),
    EMPTY_ABANDONED_CHECKOUT_URL(9, "No abandoned checkout url found"),
    NO_ABANDONED_CART_CONFIG(10, "No abandoned cart config found"),
    NO_ABANDONED_CART_STORE_DETAILS(10, "No abandoned cart details to get recent abandoned cart found"),
    NO_ORDER_CRM_CONFIG(11, "No order crm config found"),
    NO_PLAN_SELECTED(12, "NO Billing Plan has selected"),
    USAGE_CHARGE_EXPIRED(13, "Usage charges has utilised"),
    UNABLE_TO_CREATE_DISCOUNT(14, "Unable to create disocunt code");;

    private final Integer code;
    private final String message;

    ShopifyExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
