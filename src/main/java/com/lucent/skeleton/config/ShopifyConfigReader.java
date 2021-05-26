package com.lucent.skeleton.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "shopify")
public class ShopifyConfigReader {
    private String secret;
    private String key;
    private String scope;
    private String url;
    private String server_url;
    private String callback;
    private String api_version;
    private String billing_callback;
    private String webhook_topics;
    private String webhook_listener_url;
    private String price_rule_url;
    private String discount_url;
    private Double max_charge;

    private Boolean testBilling;
    private String token_url;
    private String shop_url;
    private String recurring_url;
    private String recurring_charge_url;
    private String post_script_tag_url;
    private String put_script_tag_url;
    private String abandoned_cart_url;
    private String webhook_url;
    private String single_webhook_url;
    private String usage_charge_url;

    private String TWILIO_ACCOUNT_SID;
    private String TWILIO_AUTH_TOKEN;
    private String TWILIO_PHONE_NO;
    private String TWILIO_CALLBACK_URL;

    private String gupshup_whatsapp_api_key;
    private String gupshup_whatsapp_optin_api_url;
    private String gupshup_whatsapp_api_url;

    private String awssqsQueueName;
    private String awsSecretKey;
    private String awsAccessKey;

    private String adminUser;
    private String adminPassword;

    private Boolean active_abandoned_cart;
}
