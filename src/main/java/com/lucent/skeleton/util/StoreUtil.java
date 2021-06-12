package com.lucent.skeleton.util;

import com.lucent.skeleton.entities.*;
import com.lucent.skeleton.exception.ShopifyException;
import com.lucent.skeleton.exception.enums.ShopifyExceptionEnum;
import com.lucent.skeleton.repository.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StoreUtil {

    private static final Logger logger = LogManager.getLogger(StoreUtil.class);

    @Autowired
    private StoreDetailsRepository storeDetailsRepository;

    @Autowired
    private StorePlanDetailsRepository storePlanDetailsRepository;


    public StoreDetails getByMyShopifyUrl(String url) throws ShopifyException {
        StoreDetails storeDetails = storeDetailsRepository.findByMyShopfiyUrl(url);
        if (storeDetails == null) {
            logger.error("[StoreUtil] Invalid store name: {}", url);
            throw new ShopifyException(ShopifyExceptionEnum.INVALID_STORE);
        }
        return storeDetails;
    }

    public StoreDetails getByStoreId(Long storeId) throws ShopifyException {
        StoreDetails storeDetails = storeDetailsRepository.findById(storeId).orElse(null);
        if (storeDetails == null) {
            logger.error("[StoreUtil] Invalid store name: {}", storeId);
            throw new ShopifyException(ShopifyExceptionEnum.INVALID_STORE);
        }
        return storeDetails;
    }

    public Map<Long, StoreDetails> getStoreDetailsMap() {
        return ((List<StoreDetails>) storeDetailsRepository.findAll()).stream().collect(Collectors.toConcurrentMap(StoreDetails::getId, c -> c));
    }

    public List<StorePlanDetails> getStorePlanDetailsById(Set<Long> ids) {
        return (List<StorePlanDetails>) storePlanDetailsRepository.findAllById(ids);
    }
}
