package com.lucent.skeleton.clients.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class ShopifyWebhookDTO {
    public String topic;
    public String address;
    public String format;
}
