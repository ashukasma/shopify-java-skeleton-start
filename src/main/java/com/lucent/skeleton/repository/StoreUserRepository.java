package com.lucent.skeleton.repository;



import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.lucent.skeleton.entities.StoreUser;

/**
 *
 * @author lucent
 */
@Repository
public interface StoreUserRepository extends CrudRepository<StoreUser, Long>{
    StoreUser findByUsername(String username);
    StoreUser getById(Long id);
    String save(String s);
}