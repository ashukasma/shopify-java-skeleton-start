package com.lucent.skeleton.repository;


import org.springframework.data.repository.CrudRepository;
import com.lucent.skeleton.entities.GlobalShopifyScript;

public interface ScriptRepository extends CrudRepository<GlobalShopifyScript, Integer> {
    @Override
    Iterable<GlobalShopifyScript> findAll();

}
