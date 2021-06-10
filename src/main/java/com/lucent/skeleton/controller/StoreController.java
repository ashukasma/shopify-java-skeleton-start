package com.lucent.skeleton.controller;

import com.lucent.skeleton.dto.RestApiResponse;
import com.lucent.skeleton.service.StoreService;
import com.lucent.skeleton.service.shopify.ShopifyBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("api/shopify")
public class StoreController {
    @Autowired
    ShopifyBillingService shopifyBillingService;

    @Autowired
    StoreService storeService;

    @RequestMapping("/billing/{store_id}")
    @ResponseBody
    public RestApiResponse shopifyBilling(@PathVariable Long store_id) {
        return shopifyBillingService.enableRecurringCharges(store_id);
    }

    @RequestMapping("/amountbilling/{store_id}/{max_charge}")
    @ResponseBody
    public RestApiResponse shopifyBillingWithAmount(@PathVariable Long store_id, @PathVariable double max_charge) {
        return shopifyBillingService.enableRecurringCharges(store_id, "New Updated Plan", max_charge);
    }

    @RequestMapping("/store")
    @ResponseBody
    public RestApiResponse shopifyStoreDetails(Principal principal) {
        String myShopifyUrl = principal.getName();
        return storeService.findStoreByMyShopifyUrl(myShopifyUrl);
    }

    @RequestMapping("/settings/reset/{myShopifyUrl}")
    @ResponseBody
    public RestApiResponse resetInstallation(@PathVariable String myShopifyUrl) {
        return storeService.uninstallStore(myShopifyUrl);
    }

    @RequestMapping("/settings/tour/update/{storeId}/{tourId}")
    @ResponseBody
    public RestApiResponse updateIntroTour(@PathVariable Integer storeId, @PathVariable Integer tourId) {
        return storeService.updateTour(storeId, tourId);
    }
}
