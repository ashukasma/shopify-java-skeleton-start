package com.lucent.skeleton.controller;

import com.lucent.skeleton.entities.StoreDetails;
import com.lucent.skeleton.repository.StoreDetailsRepository;
import com.lucent.skeleton.service.StoreService;
import com.lucent.skeleton.service.shopify.ShopifyDiscountService;
import com.lucent.skeleton.service.shopify.ShopifyScriptTagService;
import okhttp3.OkHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin
@RestController
@RequestMapping("test/")
public class TestController {

    private static final Logger logger = LogManager.getLogger(TestController.class);

    @Autowired
    private StoreDetailsRepository storeDetailsRepository;

    @Autowired
    private ShopifyScriptTagService shopifyScriptTagService;

    @Autowired
    private ShopifyDiscountService shopifyDiscountService;

    @Autowired
    private StoreService storeService;

    @GetMapping("/script")
    public void installScript() {
        shopifyScriptTagService.installScriptTag(1);
    }

    public String updateDiscountString(StoreDetails storeDetails, String template){
        String updatedTemplate = template;
        String discountRegex = "\\{discount_(\\d+)_(percentage|fixed)\\}";
        Pattern pattern = Pattern.compile(discountRegex);
        Matcher matcher = pattern.matcher(template);
        int start = 0, len = -1;
        while (matcher.find()) {
            start = matcher.start();
            len = matcher.end() - start;
        }
        if (len != -1) {
            StringBuilder sb = new StringBuilder(template);
            String discountString = template.substring(start, start+len);
            String[] strArr = discountString.split("_");
            if(strArr.length == 3) {
                double value = Double.parseDouble(strArr[1]);
                String discountType = "";
                if (strArr[2].equalsIgnoreCase("fixed}")) {
                    discountType = "Fixed";
                } else if (strArr[2].equalsIgnoreCase("percentage}")) {
                    discountType = "Percentage";
                }
                UUID uuid = UUID.randomUUID();
                String code = uuid.toString();
                if(shopifyDiscountService.createDiscount(storeDetails,code,discountType,value)) {
                    updatedTemplate = template.replace(discountString, "{discount}");
                    System.out.println("updatedTemplate" + updatedTemplate);
                    System.out.println("code" + code);
                }
            }
        }
        return updatedTemplate;
    }

    @GetMapping("/pp")
    public String test(@RequestParam(name = "name") String name) {

//        OkHttpClient client = new OkHttpClient().newBuilder().build();

        return storeService.updateStoreData().toString();
//        String template = "Hi *${customerName}*, thank you for your purchase of *${totalPrice}* from *${storeName}*. Your order is getting ready and we will notify you when it has been shipped. You can view your order here *${orderUrl}* - *${storeUrl}*. Please find discount code {discount_10_percentage}";
//        StoreDetails storeDetails = storeDetailsRepository.findByMyShopfiyUrl("whats-crm-1.myshopify.com");
//        return updateDiscountString(storeDetails, template);

//        Request.Post("http://www.example.com/page.php")
//                .bodyForm(Form.form().add("id", "10").build())
//                .execute()
//                .returnContent();


    }
}
