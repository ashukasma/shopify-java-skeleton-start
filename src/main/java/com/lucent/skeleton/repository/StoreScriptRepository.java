package com.lucent.skeleton.repository;

import org.springframework.data.repository.CrudRepository;
import com.lucent.skeleton.entities.StoreScript;

import java.util.Optional;

public interface StoreScriptRepository extends CrudRepository<StoreScript, Integer> {


    @Override
    Iterable<StoreScript> findAll();

    @Override
    Optional<StoreScript> findById(Integer integer);

    StoreScript findByScriptId(Integer scriptId);

    StoreScript findByStoreIdAndScriptId(Integer storeId, Integer scriptId);

    Iterable<StoreScript> findByStoreId(Integer storeId);

    StoreScript findByScriptIdAndVersion(Integer scriptId, String version);

    void deleteByScriptId(Integer scriptId);
}
