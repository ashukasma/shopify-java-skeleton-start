package com.lucent.skeleton.controller;

import com.lucent.skeleton.config.ShopifyConfigReader;
import com.lucent.skeleton.dto.RestApiResponse;
import com.lucent.skeleton.dto.store.LoginResponseDTO;
import com.lucent.skeleton.service.StoreService;
import com.lucent.skeleton.service.shopify.ShopifyBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;


@RestController("/")
public class ShopifyController {

    @Autowired
    private StoreService shopifyService;

    @Autowired
    ShopifyConfigReader shopifyConfigReader;

    @Autowired
    private ShopifyBillingService shopifyBillingService;

    @GetMapping(value = "/store_install")
    @ResponseBody
    public RedirectView store_install(@RequestParam String shop, RedirectAttributes attributes){

        String redirect_url = "https://" + shop + "/admin/oauth/authorize?client_id=" + shopifyConfigReader.getKey() + "&scope=" + shopifyConfigReader.getScope() + "&redirect_uri=" + shopifyConfigReader.getServer_url() + shopifyConfigReader.getCallback();
        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
        attributes.addAttribute("attribute", "redirectWithRedirectView");
        return new RedirectView(redirect_url);
    }

    @GetMapping(value = "/shopify_callback")
    @ResponseBody
    public RedirectView shopifycallback(@RequestParam(value = "code", required = false) String code,
                                  @RequestParam(value = "hmac", required = false) String hmac,
                                  @RequestParam(value = "shop", required = false) String shop,
                                        @RequestParam(value = "host", required = false) String host
                                  ){
        try {
            LoginResponseDTO loginResponseDTO = shopifyService.autoLogin(code,shop,hmac);
            if(loginResponseDTO.getSuccess()){
                return new RedirectView(shopifyConfigReader.getUrl()+"/login-success/"+host);
            }
            else{
                return new RedirectView(shopifyConfigReader.getUrl()+"/login-failed/Authetication failed.");
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return new RedirectView(shopifyConfigReader.getUrl()+"/ failed");
    }



    @RequestMapping(value = "/shopify_billing_callback")
    @ResponseBody
    public RedirectView shopify_billing_callback(@RequestParam String store_url, @RequestParam Long charge_id){
        RestApiResponse restApiResponse =  shopifyBillingService.verifyRecurringCharges(store_url,charge_id);
        if(restApiResponse.getSuccess()){
            return new RedirectView(shopifyConfigReader.getUrl()+"/billing-success");
        }
        else{
            return new RedirectView(shopifyConfigReader.getUrl()+"/billing-failed:Not Able to charge customer.");
        }
    }
}