package com.lucent.skeleton.clients.dto.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopifyScriptTagMasterPayload {
    @JsonProperty("script_tag")
    ShopifyScriptTagPayload shopifyScriptTagPayload;
}
