package com.lucent.skeleton.service.shopify;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

    public RestApiResponse enableRecurringCharges(String myShopifyUrl) {
        try {
            System.out.println("enableRecurringCharges");
            logger.info("enableRecurringCharges 1" + myShopifyUrl);
            return this.enableRecurringCharges(myShopifyUrl, shopifyConfigReader.getPlan_name(), 0.0, shopifyConfigReader.getMax_charge(), shopifyConfigReader.getTerms());
        }
        catch(Exception ex){
            System.out.println(ex);
            logger.info(ex.getMessage());
            return RestApiResponse.buildFail(ex.getMessage());
        }
    }

    public RestApiResponse enableRecurringCharges(String myShopifyUrl, String name, Double price) {
        logger.info("enableRecurringCharges 2" + name);
        return this.enableRecurringCharges(myShopifyUrl, name, price, shopifyConfigReader.getMax_charge(), shopifyConfigReader.getTerms());
    }


    public RestApiResponse enableRecurringCharges(String myShopifyUrl, String name, Double price, Double capAmount, String terms) {
        System.out.println("enableRecurringCharges");
        logger.info("enableRecurringCharges 3" + name);
        String recurring_url = shopifyConfigReader.getRecurring_url();
        String shopify_api_version = shopifyConfigReader.getApi_version();
        String shopify_billing_callback = shopifyConfigReader.getServer_url() + shopifyConfigReader.getBilling_callback();

        boolean testBilling = shopifyConfigReader.getTestBilling();

        StoreDetails storeDetails;
        storeDetails = storeDetailsRepository.findByMyShopfiyUrl(myShopifyUrl);
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
        Long storeId = storeDetails.getId();
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
                storeBilling.setStoreId(storeId);
                String responseString = shopifyResponse.getResponse();
                ShopBillingMasterDTO shopBillingMasterDTO = new ObjectMapper().readValue(responseString, ShopBillingMasterDTO.class);
                logger.info("storeDetailsDTO Detail {}", shopBillingMasterDTO.toString());
                BeanUtils.copyProperties(shopBillingMasterDTO.getShopBillingDTO(), storeBilling);
                storeBillingRepository.save(storeBilling);

                List<StorePlanDetails> storePlanDetailsList = storePlanRepository.findByStoreId(storeId);
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
}
