package com.lucent.skeleton.dto.store;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopDetailsDTO {

    @JsonProperty("id")
    private Long shopify_store_id;

    @JsonProperty("myshopify_domain")
    private String myShopfiyUrl;

    @JsonProperty("domain")
    private String storeUrl;

    @JsonProperty("name")
    private String name;

    @JsonProperty("shop_owner")
    private String ownerName;

    @JsonProperty("plan_display_name")
    private String plan;

    @JsonProperty("phone")
    private Long phoneNo;

    @JsonProperty("email")
    private String email;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("iana_timezone")
    private String timeZone;
}

