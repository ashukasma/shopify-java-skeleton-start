package com.lucent.skeleton.service.shopify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucent.skeleton.clients.dto.ShopifyResponse;
import com.lucent.skeleton.clients.dto.ShopifyWebhookDTO;
import com.lucent.skeleton.clients.dto.ShopifyWebhookMasterDTO;
import com.lucent.skeleton.clients.service.ShopifyClient;
import com.lucent.skeleton.config.ShopifyConfigReader;
import com.lucent.skeleton.dto.RestApiResponse;
import com.lucent.skeleton.entities.StoreDetails;
import com.lucent.skeleton.repository.StoreBillingRepository;
import com.lucent.skeleton.repository.StoreDetailsRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ShopifyWebhookService {
    private static final Logger logger = LogManager.getLogger(ShopifyBillingService.class);

    @Autowired
    ShopifyClient shopifyClient;

    @Autowired
    ShopifyConfigReader shopifyConfigReader;



    @Autowired
    StoreDetailsRepository storeDetailsRepository;

    @Autowired
    StoreBillingRepository storeBillingRepository;

    public boolean createWebhooks(Integer storeId){
        RestApiResponse restApiResponse = RestApiResponse.buildFail();

        StoreDetails storeDetails;
        storeDetails = storeDetailsRepository.findById(storeId).orElse(null);
        if (storeDetails == null) {
            return false;
        }

        String webhookTopics = shopifyConfigReader.getWebhook_topics();
        String webhookListenerUrl = shopifyConfigReader.getWebhook_listener_url();
        String format = "json";
        String[] webhookList = webhookTopics.split(",");
        Boolean bUpdate = false;
        for(int i=0; i< webhookList.length; i++){
            try {

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

                String topic = webhookList[i];
                ShopifyWebhookDTO shopifyWebhookDTO = new ShopifyWebhookDTO(topic, webhookListenerUrl, format);
                ShopifyWebhookMasterDTO shopifyWebhookMasterDTO = new ShopifyWebhookMasterDTO(shopifyWebhookDTO);

                String requestBody = objectMapper.writeValueAsString(shopifyWebhookMasterDTO);

                String myShopifyUrl = storeDetails.getMyShopfiyUrl();
                String webhookUrl = shopifyConfigReader.getWebhook_url();
                webhookUrl = webhookUrl.replace("{version}", shopifyConfigReader.getApi_version());
                webhookUrl = "https://" + myShopifyUrl + webhookUrl;

                ShopifyResponse shopifyResponse = new ShopifyResponse();
                shopifyResponse = shopifyClient.postDataToShopify(webhookUrl, storeDetails.getAccessToken(), requestBody);
                if (shopifyResponse.getSuccess()) {
                    String responseString = shopifyResponse.getResponse();
                    bUpdate = true;
                }
                else{
                    bUpdate = false;
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
