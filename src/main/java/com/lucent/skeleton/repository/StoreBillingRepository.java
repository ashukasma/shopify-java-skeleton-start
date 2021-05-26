package com.lucent.skeleton.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.lucent.skeleton.entities.StoreBilling;

@Repository
public interface StoreBillingRepository extends CrudRepository<StoreBilling,Integer > {
    StoreBilling findByStoreId(Long storeId);

    StoreBilling findByStoreIdAndChargeId(Long storeId, Long chargeId);
}
