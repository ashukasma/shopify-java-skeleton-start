package com.lucent.skeleton.service.shopify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lucent.skeleton.clients.dto.*;
import com.lucent.skeleton.clients.dto.payload.ShopifyScriptTagMasterPayload;
import com.lucent.skeleton.clients.dto.payload.ShopifyScriptTagPayload;
import com.lucent.skeleton.clients.service.ShopifyClient;
import com.lucent.skeleton.config.ShopifyConfigReader;
import com.lucent.skeleton.dto.RestApiResponse;
import com.lucent.skeleton.dto.store.StoreScriptTagDTO;
import com.lucent.skeleton.dto.store.StoreScriptTagListMasterDTO;
import com.lucent.skeleton.dto.store.StoreScriptTagMasterDTO;
import com.lucent.skeleton.entities.GlobalShopifyScript;
import com.lucent.skeleton.entities.StoreDetails;
import com.lucent.skeleton.entities.StoreScript;
import com.lucent.skeleton.repository.ScriptRepository;
import com.lucent.skeleton.repository.StoreDetailsRepository;
import com.lucent.skeleton.repository.StoreScriptRepository;

import java.util.Optional;

@Service
public class ShopifyScriptTagService {
    @Autowired
    StoreScriptRepository storeScriptRepository;

    @Autowired
    ScriptRepository scriptRepository;

    @Autowired
    ShopifyConfigReader shopifyConfigReader;

    @Autowired
    StoreDetailsRepository storeDetailsRepository;

    @Autowired
    ShopifyClient shopifyClient;

    public Boolean installScriptTag(Long shopId) {
        RestApiResponse restApiResponse = RestApiResponse.buildFail();
        Optional<StoreScript> storeScripts = storeScriptRepository.findById(shopId);
        StoreDetails storeDetails;
        storeDetails = storeDetailsRepository.findById(shopId).orElse(null);
        if (storeDetails == null) {
            return false;
        }
        Iterable<GlobalShopifyScript> scriptList = scriptRepository.findAll();
        scriptList.forEach(globalShopifyScript -> {
            StoreScript storeScript = storeScriptRepository.findByStoreIdAndScriptId(shopId, globalShopifyScript.getId());
            if (storeScript == null) {
                this.insertScriptTag(globalShopifyScript, storeDetails);
            } else if (!storeScript.getVersion().equals(globalShopifyScript.getVersion())) {
                this.resetScriptTag(storeDetails);
                this.insertScriptTag(globalShopifyScript, storeDetails);
            }
        });
        return false;
    }

    private Boolean insertScriptTag(GlobalShopifyScript globalShopifyScript, StoreDetails storeDetails) {
        String script_tag_url = shopifyConfigReader.getPost_script_tag_url();
        ShopifyScriptTagDTO shopifyScriptTagDTO = new ShopifyScriptTagDTO(globalShopifyScript.getSrc(), "onload");
        ShopifyScriptTagMasterDTO shopifyScriptTagMasterDTO = new ShopifyScriptTagMasterDTO(shopifyScriptTagDTO);
        ShopifyResponse shopifyResponse = new ShopifyResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            String requestBody = objectMapper.writeValueAsString(shopifyScriptTagMasterDTO);
            String myShopifyUrl = storeDetails.getMyShopfiyUrl();
            script_tag_url = script_tag_url.replace("{version}", shopifyConfigReader.getApi_version());
            script_tag_url = "https://" + myShopifyUrl + script_tag_url;
            shopifyResponse = shopifyClient.postDataToShopify(script_tag_url, storeDetails.getAccessToken(), requestBody);
            if (shopifyResponse.getSuccess()) {
                String responseString = shopifyResponse.getResponse();
                StoreScriptTagMasterDTO storeScriptTagMasterDTO = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(responseString, StoreScriptTagMasterDTO.class);
                StoreScriptTagDTO storeScriptTagDTO = new StoreScriptTagDTO();
                BeanUtils.copyProperties(storeScriptTagMasterDTO.getStoreScriptTagChildDTO(), storeScriptTagDTO, StoreScriptTagDTO.class);
                StoreScript storeScript = new StoreScript();
                storeScript.setVersion(globalShopifyScript.getVersion());
                storeScript.setScriptTagId(storeScriptTagDTO.getId());
                storeScript.setScriptId(globalShopifyScript.getId());
                storeScript.setStoreId(storeDetails.getId());
                storeScript.setCreatedAt(globalShopifyScript.getCreatedAt());
                storeScript.setUpdatedAt(globalShopifyScript.getUpdatedAt());
                storeScriptRepository.save(storeScript);
                return true;
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Boolean updateScriptTag(GlobalShopifyScript globalShopifyScript, StoreDetails storeDetails, StoreScript storeScript) {
        String script_tag_url = shopifyConfigReader.getPut_script_tag_url();
        ShopifyScriptTagPayload shopifyScriptTagPayload = new ShopifyScriptTagPayload(storeScript.getScriptTagId(), globalShopifyScript.getSrc());
        ShopifyScriptTagMasterPayload shopifyScriptTagMasterPayload = new ShopifyScriptTagMasterPayload(shopifyScriptTagPayload);
        ShopifyResponse shopifyResponse = new ShopifyResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            String requestBody = objectMapper.writeValueAsString(shopifyScriptTagMasterPayload);
            String myShopifyUrl = storeDetails.getMyShopfiyUrl();
            script_tag_url = script_tag_url.replace("{version}", shopifyConfigReader.getApi_version());
            script_tag_url = script_tag_url.replace("{id}", storeScript.getScriptTagId());
            script_tag_url = "https://" + myShopifyUrl + script_tag_url;
            shopifyResponse = shopifyClient.updateDataToShopify(script_tag_url, storeDetails.getAccessToken(), requestBody);
            if (shopifyResponse.getSuccess()) {
                String responseString = shopifyResponse.getResponse();
                StoreScriptTagMasterDTO storeScriptTagMasterDTO = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(responseString, StoreScriptTagMasterDTO.class);
                StoreScriptTagDTO storeScriptTagDTO = new StoreScriptTagDTO();
                BeanUtils.copyProperties(storeScriptTagMasterDTO.getStoreScriptTagChildDTO(), storeScriptTagDTO, StoreScriptTagDTO.class);
                storeScript.setVersion(globalShopifyScript.getVersion());
                storeScript.setScriptTagId(storeScriptTagDTO.getId());
                storeScript.setCreatedAt(globalShopifyScript.getCreatedAt());
                storeScript.setUpdatedAt(globalShopifyScript.getUpdatedAt());
                storeScriptRepository.save(storeScript);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void removeScriptTagFromDB(Long shopId) {
        Iterable<StoreScript> findByStoreId = storeScriptRepository.findByStoreId(shopId);
        findByStoreId.forEach(storeScript -> storeScriptRepository.deleteById(storeScript.getId()));
    }

    private void removeScriptTag(StoreDetails storeDetails, String scriptTagId) {
        String script_tag_url = shopifyConfigReader.getPut_script_tag_url();
        ShopifyResponse shopifyResponse = new ShopifyResponse();
        try {
            String myShopifyUrl = storeDetails.getMyShopfiyUrl();
            script_tag_url = script_tag_url.replace("{version}", shopifyConfigReader.getApi_version());
            script_tag_url = script_tag_url.replace("{id}", scriptTagId);
            script_tag_url = "https://" + myShopifyUrl + script_tag_url;
            shopifyResponse = shopifyClient.deleteDataFromShopify(script_tag_url, storeDetails.getAccessToken());
            if (shopifyResponse.getSuccess()) {
                storeScriptRepository.deleteByScriptId(Long.getLong(scriptTagId));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Boolean resetScriptTag(StoreDetails storeDetails) {
        String script_tag_url = shopifyConfigReader.getPost_script_tag_url();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            String myShopifyUrl = storeDetails.getMyShopfiyUrl();
            script_tag_url = script_tag_url.replace("{version}", shopifyConfigReader.getApi_version());
            script_tag_url = "https://" + myShopifyUrl + script_tag_url;
            ShopifyResponse shopifyResponse = shopifyClient.getDataFromShopify(script_tag_url, storeDetails.getAccessToken());
            if (shopifyResponse.getSuccess()) {
                String responseString = shopifyResponse.getResponse();
                StoreScriptTagListMasterDTO storeScriptTagListMasterDTO = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(responseString, StoreScriptTagListMasterDTO.class);
                StoreScriptTagDTO[] storeScriptTagDTOS = storeScriptTagListMasterDTO.getStoreScriptTagChildDTO();
                for (StoreScriptTagDTO sstag : storeScriptTagDTOS) {
                    this.removeScriptTag(storeDetails, sstag.getId());
                }
            }
            this.removeScriptTagFromDB(storeDetails.getId());
        } catch ( JsonProcessingException e ) {
            e.printStackTrace();
        } catch( Exception e ) {
            e.printStackTrace();
        }
        return false;
    }
}
