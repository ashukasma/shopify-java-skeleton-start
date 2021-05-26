package com.lucent.skeleton.service.shopify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lucent.skeleton.clients.dto.*;
import com.lucent.skeleton.clients.dto.payload.*;
import com.lucent.skeleton.clients.service.ShopifyClient;
import com.lucent.skeleton.config.ShopifyConfigReader;
import com.lucent.skeleton.entities.*;
import com.lucent.skeleton.repository.*;
import com.lucent.skeleton.service.shopify.dto.enums.ShopifyDiscountType;

@Service
public class ShopifyDiscountService {
    private static final Logger logger = LogManager.getLogger(ShopifyBillingService.class);

    @Autowired
    ShopifyClient shopifyClient;

    @Autowired
    ShopifyConfigReader shopifyConfigReader;

    @Autowired
    StoreDetailsRepository storeDetailsRepository;

    public Boolean createDiscount(StoreDetails storeDetails, String code, String discountType, double value) {
        Long priceRuleId = createPriceRule(storeDetails, discountType, value, code);
        if (priceRuleId != 0) {
            String discountString = createDiscountCode(storeDetails, code, priceRuleId);
            if (discountString.equals((code))) {
                return true;
            }
        }
        return false;
    }

    private String createDiscountCode(StoreDetails storeDetails, String code, Long priceRuleId) {
        String discountString = null;
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            String discountUrl = shopifyConfigReader.getDiscount_url();
            discountUrl = discountUrl.replace("{version}", shopifyConfigReader.getApi_version());
            discountUrl = discountUrl.replace("{price_rule_id}", priceRuleId.toString());
            String url = "https://" + storeDetails.getMyShopfiyUrl() + discountUrl;
            ShopifyDiscountCodePayload shopifyDiscountCodePayload = new ShopifyDiscountCodePayload(
                    code
            );
            ShopifyDiscountCodeMasterPayLoad shopifyDiscountCodeMasterPayLoad = new ShopifyDiscountCodeMasterPayLoad(shopifyDiscountCodePayload);
            requestBody = objectMapper.writeValueAsString(shopifyDiscountCodeMasterPayLoad);
            ShopifyResponse shopifyResponse = shopifyClient.postDataToShopify(url,
                    storeDetails.getAccessToken(),
                    requestBody);
            if (shopifyResponse.getSuccess()) {
                String responseString = shopifyResponse.getResponse();
                ShopifyDiscountCodeMasterDTO shopifyDiscountCodeMasterDTO = new ObjectMapper()
                        .readValue(responseString, ShopifyDiscountCodeMasterDTO.class);
                discountString = shopifyDiscountCodeMasterDTO.getShopifyDiscountCodeDTO().getCode();
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return discountString;
    }

    private Long createPriceRule(StoreDetails storeDetails, String disocuntType, double value, String code) {

        Long priceRuleId = Long.getLong("0");
        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = null;
        try {
            String priceRuleUrl = shopifyConfigReader.getPrice_rule_url();
            priceRuleUrl = priceRuleUrl.replace("{version}", shopifyConfigReader.getApi_version());
            String myShopifyUrl = storeDetails.getMyShopfiyUrl();
            String url = "https://" + myShopifyUrl + priceRuleUrl;
            ShopifyPriceRulePayload shopifyPriceRulePayload = new ShopifyPriceRulePayload(
                    code,
                    "line_item",
                    "all",
                    "across",
                    ShopifyDiscountType.findByMessage(disocuntType).getCode(),
                    value*-1,
                    "all",
                    1,
                    "2017-01-19T17:59:10Z"
            );

            ShopifyPriceRuleMasterPayload shopifyPriceRuleMasterPayload = new ShopifyPriceRuleMasterPayload(shopifyPriceRulePayload);

            requestBody = objectMapper.writeValueAsString(shopifyPriceRuleMasterPayload);
            ShopifyResponse shopifyResponse = shopifyClient.postDataToShopify(url, storeDetails.getAccessToken(), requestBody);
            if (shopifyResponse.getSuccess()) {
                String responseString = shopifyResponse.getResponse();
                logger.info("createPriceRule - responseString" + responseString);
                ShopifyPriceRuleMasterDTO shopifyPriceRuleMasterDTO = new ObjectMapper().readValue(responseString, ShopifyPriceRuleMasterDTO.class);
                priceRuleId = shopifyPriceRuleMasterDTO.getShopifyPriceRuleDTO().getId();
            }
        } catch (JsonProcessingException e) {
            logger.error(e);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return priceRuleId;
    }


}
