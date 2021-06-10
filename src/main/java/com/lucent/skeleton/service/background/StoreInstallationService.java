package com.lucent.skeleton.service.background;

import com.lucent.skeleton.config.ShopifyConfigReader;
import com.lucent.skeleton.entities.StoreDetails;
import com.lucent.skeleton.entities.StorePlanDetails;
import com.lucent.skeleton.repository.StorePlanRepository;
import com.lucent.skeleton.service.shopify.ShopifyScriptTagService;
import com.lucent.skeleton.service.shopify.ShopifyWebhookService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class StoreInstallationService extends Thread {
    private static final Logger logger = LogManager.getLogger(StoreInstallationService.class);
    private StoreDetails storeDetails;

    private ShopifyConfigReader shopifyConfigReader;

    private StorePlanRepository storePlanRepository;

    private ShopifyScriptTagService shopifyScriptTagService;

    private ShopifyWebhookService shopifyWebhookService;

    public StoreInstallationService(StoreDetails storeDetails,
                                    ShopifyConfigReader shopifyConfigReader,
                                    StorePlanRepository storePlanRepository,
                                    ShopifyScriptTagService shopifyScriptTagService,
                                    ShopifyWebhookService shopifyWebhookService){
        this.storeDetails = storeDetails;
        this.shopifyConfigReader = shopifyConfigReader;
        this.storePlanRepository = storePlanRepository;
        this.shopifyScriptTagService = shopifyScriptTagService;
        this.shopifyWebhookService = shopifyWebhookService;
        logger.info("StoreInstallationService - Constructor");
    }

    @Override
    public void run() {
        logger.info("StoreInstallationService :" + storeDetails.getMyShopfiyUrl());
        try {
            logger.info("StoreInstallationService - Plan");
            if (storeDetails != null) {
                List<StorePlanDetails> storePlanDetailsList = storePlanRepository.findByStoreId(storeDetails.getId());
                if (storePlanDetailsList.size() == 0) {
                    StorePlanDetails storePlanDetails = StorePlanDetails.build(storeDetails, shopifyConfigReader.getMax_charge());
                    storePlanRepository.save(storePlanDetails);
                }
                logger.info("StoreInstallationService - createWebhooks");
                shopifyWebhookService.createWebhooks(storeDetails.getId());
            }
        }
        catch(Exception ex){
            logger.info(ex.getMessage());
        }
    }
}
