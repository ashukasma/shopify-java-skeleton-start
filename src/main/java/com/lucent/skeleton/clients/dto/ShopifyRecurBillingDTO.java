package com.lucent.skeleton.clients.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopifyRecurBillingDTO {
    private String name;
    private double price;
    private boolean test;
    private String return_url;
    private double capped_amount;
    private String terms;

}
