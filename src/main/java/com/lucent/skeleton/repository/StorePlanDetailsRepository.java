package com.lucent.skeleton.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.lucent.skeleton.entities.StorePlanDetails;

@Repository
public interface StorePlanDetailsRepository extends CrudRepository<StorePlanDetails, Integer> {
    StorePlanDetails findByStoreId(Integer storeId);
}
