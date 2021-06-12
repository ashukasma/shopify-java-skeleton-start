package com.lucent.skeleton.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucent.skeleton.clients.dto.ShopifyAccessTokenDTO;
import com.lucent.skeleton.clients.dto.ShopifyResponse;
import com.lucent.skeleton.clients.service.ShopifyClient;
import com.lucent.skeleton.config.ShopifyConfigReader;
import com.lucent.skeleton.dto.RestApiResponse;
import com.lucent.skeleton.dto.store.LoginResponseDTO;
import com.lucent.skeleton.dto.store.StoreDetailsDTO;
import com.lucent.skeleton.entities.StoreDetails;
import com.lucent.skeleton.entities.StoreUser;
import com.lucent.skeleton.repository.StoreDetailsRepository;
import com.lucent.skeleton.repository.StorePlanRepository;
import com.lucent.skeleton.security.ShopifyJwtTokenProvider;
import com.lucent.skeleton.security.SecurityConstants;
import com.lucent.skeleton.service.background.StoreInstallationService;
import com.lucent.skeleton.service.shopify.ShopifyScriptTagService;
import com.lucent.skeleton.service.shopify.ShopifyService;
import com.lucent.skeleton.service.shopify.ShopifyWebhookService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    private static final Logger logger = LogManager.getLogger(ShopifyClient.class);

    @Autowired
    private StoreDetailsRepository storeDetailsRepository;

    @Autowired
    private StorePlanRepository storePlanRepository;

    @Autowired
    private CustomStoreUserDetailsService customStoreUserDetailsService;

    @Autowired
    private ShopifyService shopifyService;

    @Autowired
    private ShopifyScriptTagService shopifyScriptTagService;

    @Autowired
    private ShopifyWebhookService shopifyWebhookService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ShopifyJwtTokenProvider jwtTokenProvider;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ShopifyConfigReader shopifyConfigReader;


    public StoreDetails saveOrUpdateStore(StoreDetails storeDetails) {
        storeDetailsRepository.save(storeDetails);
        return storeDetails;
    }

    public StoreDetails findStore(String storeName) {
        return storeDetailsRepository.findByName(storeName);
    }

    public RestApiResponse findStoreByMyShopifyUrl(String myShopifyUrl) {
        StoreDetails storeDetails = storeDetailsRepository.findByMyShopfiyUrl(myShopifyUrl);
        if (storeDetails == null) {
            RestApiResponse.buildFail("Store Not Found");
        }
        return RestApiResponse.buildSuccess(storeDetails);
    }

    public LoginResponseDTO autoLogin(String code, String shop, String hmac) {
        logger.info("Autologin - Started");
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(false, "", "");
        Boolean autoLoginSuccess = false;
        try {
            ShopifyResponse shopifyResponse = shopifyService.getAccessToken(shop, code);
            if (shopifyResponse.getSuccess()) {
                String responseString = shopifyResponse.getResponse();
                ShopifyAccessTokenDTO shopifyAccessTokenDTO = new ObjectMapper().readValue(responseString, ShopifyAccessTokenDTO.class);
                String token = shopifyAccessTokenDTO.getAccess_token();
                StoreDetails storeDetails = storeDetailsRepository.findByMyShopfiyUrl(shop);
                if (storeDetails == null) {
                    storeDetails = new StoreDetails();
                    storeDetails.setIntroTour(0);
                    ShopifyResponse shopifyResponseStoreData = shopifyService.getStoreData(shop, token);
                    if (shopifyResponseStoreData.getSuccess()) {
                        responseString = shopifyResponseStoreData.getResponse();
                        StoreDetailsDTO storeDetailsDTO = new ObjectMapper().readValue(responseString, StoreDetailsDTO.class);
                        logger.info("storeDetailsDTO Detail {}", storeDetailsDTO.toString());
                        BeanUtils.copyProperties(storeDetailsDTO.getShopDetailsDTO(), storeDetails);
                        storeDetails.setShopifyStoreId(storeDetailsDTO.getShopDetailsDTO().getShopify_store_id());
                        logger.info("Store Detail {}", storeDetails.toString());
                        storeDetails.setAccessToken(token);
                        storeDetailsRepository.save(storeDetails);
                    }
                } else {
                    storeDetails.setAccessToken(token);
                    storeDetailsRepository.save(storeDetails);
                }

//                logger.info("Autologin - scripttag");
//                shopifyScriptTagService.installScriptTag(storeDetails.getId());
//
//                logger.info("Autologin - webhook");
//                shopifyWebhookService.createWebhooks(storeDetails.getId());
//
//                logger.info("Autologin - Plan");
//
//                List<StorePlanDetails> storePlanDetailsList = storePlanRepository.findByStoreId(storeDetails.getId());
//                if(storePlanDetailsList.size() == 0){
//                    StorePlanDetails storePlanDetails =  StorePlanDetails.build(storeDetails, shopifyConfigReader.getMax_charge());
//                    storePlanRepository.save(storePlanDetails);
//                }


                logger.info("Autologin - StoreInstallationService");
                new StoreInstallationService(storeDetails,
                        shopifyConfigReader,
                        storePlanRepository,
                        shopifyScriptTagService,
                        shopifyWebhookService).start();

                StoreUser storeUser = (StoreUser) customStoreUserDetailsService.loadUserByUsername(shop);
                if (storeUser == null) {
                    String password = bCryptPasswordEncoder.encode(token);
                    StoreUser user = StoreUser.build(shop, password);
                    customStoreUserDetailsService.saveUser(user);
                } else {
                    String password = bCryptPasswordEncoder.encode(token);
                    storeUser.setPassword(password);
                    customStoreUserDetailsService.saveUser(storeUser);
                }
//                Authentication authentication = authenticationManager.authenticate(
//                        new UsernamePasswordAuthenticationToken(shop, token)
//                );
//
//                SecurityContextHolder.getContext().setAuthentication(authentication);

//                String jwtString = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
                loginResponseDTO.setMessage("Thank you for login");
                loginResponseDTO.setSuccess(true);
//                loginResponseDTO.setToken(jwtString);

            } else {
                logger.info("Access Token Failed");
            }
        } catch (JsonProcessingException | IllegalArgumentException e) {
            logger.info(e.getMessage());
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            logger.info("Autologin - Ended" + loginResponseDTO);
        }
        return loginResponseDTO;
    }

    public RestApiResponse uninstallStore(String myShopifyUrl) {
        try {

            StoreDetails storeDetails = storeDetailsRepository.findByMyShopfiyUrl(myShopifyUrl);
            shopifyScriptTagService.resetScriptTag(storeDetails);
            customStoreUserDetailsService.deleteUserByUsername(storeDetails.getStoreUrl());
        } catch (Exception e) {
            logger.log(Level.FATAL, e);
        }

        return RestApiResponse.buildFail();
    }

    public RestApiResponse updateStoreData() {
        try {
            storeDetailsRepository.findAll().forEach(storeDetails -> {
                try {
                    ShopifyResponse shopifyResponseStoreData = shopifyService.getStoreData(storeDetails.getMyShopfiyUrl(), storeDetails.getAccessToken());
                    if (shopifyResponseStoreData.getSuccess()) {
                        String responseString = shopifyResponseStoreData.getResponse();
                        StoreDetailsDTO storeDetailsDTO = new StoreDetailsDTO();
                        storeDetailsDTO = new ObjectMapper().readValue(responseString, StoreDetailsDTO.class);
                        logger.info("storeDetailsDTO Detail {}", storeDetailsDTO.toString());
                        BeanUtils.copyProperties(storeDetailsDTO.getShopDetailsDTO(), storeDetails);
                        logger.info("Store Detail {}", storeDetails.toString());
                        storeDetailsRepository.save(storeDetails);
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            logger.log(Level.FATAL, e);
        }

        return RestApiResponse.buildSuccess();
    }

    public RestApiResponse updateTour(Long storeId, Integer tourId) {
        StoreDetails storeDetails = storeDetailsRepository.findById(storeId).orElse(null);
        if(storeDetails == null){
            return RestApiResponse.buildFail("Store is not available");
        }
        storeDetails.setIntroTour(tourId);
        storeDetails = storeDetailsRepository.save(storeDetails);
        return RestApiResponse.buildSuccess(storeDetails);
    }
}
