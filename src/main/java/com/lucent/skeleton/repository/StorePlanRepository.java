package com.lucent.skeleton.repository;


import org.springframework.data.repository.CrudRepository;
import com.lucent.skeleton.entities.StorePlanDetails;

import java.util.List;

public interface StorePlanRepository extends CrudRepository<StorePlanDetails, Integer> {
    List<StorePlanDetails> findByStoreId(Integer storeId);
}
