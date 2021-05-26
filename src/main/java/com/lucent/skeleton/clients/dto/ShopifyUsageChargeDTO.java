package com.lucent.skeleton.clients.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShopifyUsageChargeDTO {
    private Integer id;
    private String description;
    private double price;
    private double balance_used;
    private Date created_at;
}
