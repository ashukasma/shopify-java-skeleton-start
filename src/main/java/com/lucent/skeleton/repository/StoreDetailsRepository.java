package com.lucent.skeleton.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.lucent.skeleton.entities.StoreDetails;

@Repository
public interface StoreDetailsRepository extends CrudRepository<StoreDetails, Long> {

    StoreDetails findByName(String name);

//    StoreDetails findByStoreId(Long storeId);

    StoreDetails findByMyShopfiyUrl(String url);
}
