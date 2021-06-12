package com.lucent.skeleton.repository;

import org.springframework.data.repository.CrudRepository;
import com.lucent.skeleton.entities.StoreScript;

import java.util.Optional;

public interface StoreScriptRepository extends CrudRepository<StoreScript, Long> {


    @Override
    Iterable<StoreScript> findAll();

    @Override
    Optional<StoreScript> findById(Long integer);

    StoreScript findByScriptId(Long scriptId);

    StoreScript findByStoreIdAndScriptId(Long storeId, Long scriptId);

    Iterable<StoreScript> findByStoreId(Long storeId);

    StoreScript findByScriptIdAndVersion(Long scriptId, String version);

    void deleteByScriptId(Long scriptId);
}
