package com.lucent.skeleton.service.shopify;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import com.lucent.skeleton.clients.dto.ShopifyRecurBillingDTO;
import com.lucent.skeleton.clients.dto.ShopifyRecurBillingMasterDTO;
import com.lucent.skeleton.clients.dto.ShopifyResponse;
import com.lucent.skeleton.clients.service.ShopifyClient;
import com.lucent.skeleton.config.ShopifyConfigReader;
import com.lucent.skeleton.dto.RestApiResponse;
import com.lucent.skeleton.dto.store.ShopBillingMasterDTO;
import com.lucent.skeleton.entities.*;
import com.lucent.skeleton.repository.*;

@Service
public class ShopifyBillingService {

    private static final Logger logger = LogManager.getLogger(ShopifyBillingService.class);

    @Autowired
    ShopifyClient shopifyClient;

    @Autowired
    ShopifyConfigReader shopifyConfigReader;

    @Autowired
    StoreDetailsRepository storeDetailsRepository;

    @Autowired
    StoreBillingRepository storeBillingRepository;

    @Autowired
    StorePlanRepository storePlanRepository;


    public RestApiResponse verifyRecurringCharges(String myShopifyUrl, Long chargeId) {

        String recurring_charge_url = shopifyConfigReader.getRecurring_charge_url();
        String shopify_api_version = shopifyConfigReader.getApi_version();

        StoreDetails storeDetails = storeDetailsRepository.findByMyShopfiyUrl(myShopifyUrl);
        if (storeDetails == null) {
            return RestApiResponse.buildFail("No store is associated");
        }

        StoreBilling storeBilling = storeBillingRepository.findByStoreIdAndChargeId(storeDetails.getId().longValue(), chargeId);
        RestApiResponse restApiResponse = RestApiResponse.buildFail();
        myShopifyUrl = storeDetails.getMyShopfiyUrl();
        recurring_charge_url = recurring_charge_url.replace("{version}", shopify_api_version);
        recurring_charge_url = recurring_charge_url.replace("{charge_id}", chargeId.toString());
        String url = "https://" + myShopifyUrl + recurring_charge_url;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ShopifyResponse shopifyResponse = shopifyClient.getDataFromShopify(url, storeDetails.getAccessToken());
            if (shopifyResponse.getSuccess()) {
                String responseString = shopifyResponse.getResponse();
                ShopBillingMasterDTO shopBillingMasterDTO = new ObjectMapper().readValue(responseString, ShopBillingMasterDTO.class);
                logger.info("storeDetailsDTO Detail {}", shopBillingMasterDTO.toString());
                BeanUtils.copyProperties(shopBillingMasterDTO.getShopBillingDTO(), storeBilling);
                storeBillingRepository.save(storeBilling);
                restApiResponse.setSuccess(true);
                storeDetails.setCurrentBillingStatus(true);
                storeDetailsRepository.save(storeDetails);
                restApiResponse.setData(shopBillingMasterDTO.getShopBillingDTO());
            } else {
                restApiResponse.setSuccess(false);
                restApiResponse.setData(shopifyResponse.getResponse());
            }
            return restApiResponse;
        } catch (Exception e) {
            return RestApiResponse.buildFail(e.getMessage());
        }
    }

    public RestApiResponse enableRecurringCharges(Long id) {
        try {
            System.out.println("enableRecurringCharges");
            logger.info("enableRecurringCharges 1" + id);
            return this.enableRecurringCharges(id, "Starter Plan", 0.0, shopifyConfigReader.getMax_charge(), "Charges ($0.01/ Message) will be based on each message (160 characters) sent. ");
        }
        catch(Exception ex){
            System.out.println(ex);
            logger.info(ex.getMessage());
            return RestApiResponse.buildFail(ex.getMessage());
        }
    }

    public RestApiResponse enableRecurringCharges(Long id, String name, Double price) {
        logger.info("enableRecurringCharges 2" + name);
        return this.enableRecurringCharges(id, name, price, shopifyConfigReader.getMax_charge(), "Amazing");
    }


    public RestApiResponse enableRecurringCharges(Long id, String name, Double price, Double capAmount, String terms) {
        System.out.println("enableRecurringCharges");
        logger.info("enableRecurringCharges 3" + name);
        String recurring_url = shopifyConfigReader.getRecurring_url();
        String shopify_api_version = shopifyConfigReader.getApi_version();
        String shopify_billing_callback = shopifyConfigReader.getServer_url() + shopifyConfigReader.getBilling_callback();

        boolean testBilling = shopifyConfigReader.getTestBilling();

        StoreDetails storeDetails;
        storeDetails = storeDetailsRepository.findById(id.intValue()).orElse(null);
        if (storeDetails == null) {
            return RestApiResponse.buildFail("No store is associated");
        }

        if(storeDetails.getEmail().contains("spaceorange.co") == true){
            testBilling = true;
        }

        if(storeDetails.getPlan().contains("Developer Preview") || storeDetails.getPlan().contains("Development")){
            testBilling = true;
        }

        RestApiResponse restApiResponse = RestApiResponse.buildFail();
        String myShopifyUrl = storeDetails.getMyShopfiyUrl();
        recurring_url = recurring_url.replace("{version}", shopify_api_version);
        String url = "https://" + myShopifyUrl + recurring_url;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            shopify_billing_callback = shopify_billing_callback.replace("{MY_SHOPIFY_URL}", myShopifyUrl);

            ShopifyRecurBillingDTO shopifyRecurBillingDTO = new ShopifyRecurBillingDTO(name, price, testBilling, shopify_billing_callback, capAmount, terms);
            ShopifyRecurBillingMasterDTO shopifyRecurBillingMasterDTO = new ShopifyRecurBillingMasterDTO(shopifyRecurBillingDTO);
            String requestBody = objectMapper.writeValueAsString(shopifyRecurBillingMasterDTO);
            ShopifyResponse shopifyResponse = shopifyClient.postDataToShopify(url, storeDetails.getAccessToken(), requestBody);
            if (shopifyResponse.getSuccess()) {
                StoreBilling storeBilling = storeBillingRepository.findByStoreId(storeDetails.getId().longValue());
                if(storeBilling == null){
                    storeBilling = new StoreBilling();
                }
                storeBilling.setStoreId(id);
                String responseString = shopifyResponse.getResponse();
                ShopBillingMasterDTO shopBillingMasterDTO = new ObjectMapper().readValue(responseString, ShopBillingMasterDTO.class);
                logger.info("storeDetailsDTO Detail {}", shopBillingMasterDTO.toString());
                BeanUtils.copyProperties(shopBillingMasterDTO.getShopBillingDTO(), storeBilling);
                storeBillingRepository.save(storeBilling);

                List<StorePlanDetails> storePlanDetailsList = storePlanRepository.findByStoreId(storeDetails.getId());
                if (storePlanDetailsList.size() == 1) {
                    StorePlanDetails storePlanDetails = storePlanDetailsList.get(0);
                    storePlanDetails.setMaxCharge(capAmount);
                    storePlanRepository.save(storePlanDetails);
                }

                restApiResponse.setSuccess(true);
                restApiResponse.setData(shopBillingMasterDTO.getShopBillingDTO());
            } else {
                restApiResponse.setSuccess(false);
                restApiResponse.setData(shopifyResponse.getResponse());
            }
            return restApiResponse;
        } catch (Exception e) {
            System.out.println(e);
            logger.info(e.getMessage());
            return RestApiResponse.buildFail(e.getMessage());
        }
    }

//    public RestApiResponse createUsageCharge(MessageDeliveryStatus messageDeliveryStatus, Integer storeId, Double amount, String message){
//        String usage_charge_url = shopifyConfigReader.getUsage_charge_url();
//        String api_version = shopifyConfigReader.getApi_version();
//
//        StoreDetails storeDetails;
//        storeDetails = storeDetailsRepository.findById(storeId).orElse(null);
//        if (storeDetails == null) {
//            return RestApiResponse.buildFail("No store is associated");
//        }
//
//        StoreBilling storeBilling = storeBillingRepository.findByStoreId(storeId.longValue());
//        if(storeBilling ==  null){
//            return RestApiResponse.buildFail("No store is associated");
//        }
//        Long chargeId = storeBilling.getChargeId();
//
//
//        RestApiResponse restApiResponse = RestApiResponse.buildFail();
//        String myShopifyUrl = storeDetails.getMyShopfiyUrl();
//        usage_charge_url = usage_charge_url.replace("{version}", api_version);
//        usage_charge_url = usage_charge_url.replace("{recurring_application_charge_id}", chargeId.toString());
//        String url = "https://" + myShopifyUrl + usage_charge_url;
//        ObjectMapper objectMapper = new ObjectMapper();
//        try{
//            ShopifyUsageChargePayload shopifyUsageChargePayload = new ShopifyUsageChargePayload(message, amount);
//            ShopifyUsageChargeMasterPayload shopifyUsageChargeMasterPayload = new ShopifyUsageChargeMasterPayload(shopifyUsageChargePayload);
//            String requestBody = objectMapper.writeValueAsString(shopifyUsageChargeMasterPayload);
//            ShopifyResponse shopifyResponse = shopifyClient.postDataToShopify(url,storeDetails.getAccessToken(),requestBody);
//            if(shopifyResponse.getSuccess()){
//                String responseString = shopifyResponse.getResponse();
//                System.out.println("responseString"+responseString);
//                ShopifyUsageChargeMasterDTO shopifyUsageChargeMasterDTO =  new ObjectMapper().readValue(responseString,ShopifyUsageChargeMasterDTO.class);
//                Date createdAt = shopifyUsageChargeMasterDTO.getShopifyUsageChargeDTO().getCreated_at();
//                LocalDate localDate = createdAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                int month = localDate.getMonthValue();
//                int year = localDate.getYear();
//                MessageDeliveryTransaction messageDeliveryTransaction = MessageDeliveryTransaction.build(
//                        messageDeliveryStatus,
//                        amount,
//                        shopifyUsageChargeMasterDTO.getShopifyUsageChargeDTO().getId().longValue(),
//                        month,
//                        year
//                        );
//                messageDeliveryTransaction = messageDeliveryTransactionRespository.save(messageDeliveryTransaction);
//
//                List<StorePlanDetails> storePlanDetailsList = storePlanRepository.findByStoreId(storeDetails.getId());
//                if(storePlanDetailsList.size() == 1){
//                    StorePlanDetails storePlanDetails =  storePlanDetailsList.get(0);
//                    storePlanDetails.setCurrentCharge(shopifyUsageChargeMasterDTO.getShopifyUsageChargeDTO().getBalance_used());
//                    storePlanRepository.save(storePlanDetails);
//                }
//                return RestApiResponse.buildSuccess(messageDeliveryTransaction);
//
//            }
//            else{
//                return RestApiResponse.buildFail(shopifyResponse.getResponse());
//            }
//        }
//        catch (Exception e){
//            System.out.println(e);
//            return RestApiResponse.buildFail(e.getMessage());
//        }
//    }
}
