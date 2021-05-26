package com.lucent.skeleton.dto.store;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class StoreScriptTagMasterDTO {
    @JsonProperty("script_tag")
    StoreScriptTagDTO storeScriptTagChildDTO;
}
