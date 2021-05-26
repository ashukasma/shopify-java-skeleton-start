package com.lucent.skeleton.enums;


public enum ShopifyTopicEnum {

    ORDER_PLACED("orders/create", 1),
    ORDER_FULFILLED("orders/fulfilled", 2),
    ORDER_PARTIALLY_FULFILLED("orders/partially_fulfilled", 2),
    ABANDONED_CART("abandoned_cart", 3),
    APP_UNINSTALL("app/uninstalled", 4);

    private final String topic;
    private final Integer value;

    ShopifyTopicEnum(String topic, Integer value) {
        this.topic = topic;
        this.value = value;
    }

    public String getTopic() {
        return topic;
    }

    public Integer getValue() {
        return value;
    }

    public static ShopifyTopicEnum findByTopic(String topic) {
        for (ShopifyTopicEnum shopifyTopicEnum : ShopifyTopicEnum.values()) {
            if (shopifyTopicEnum.getTopic().equalsIgnoreCase(topic)) {
                return shopifyTopicEnum;
            }
        }
        return null;
    }
}
