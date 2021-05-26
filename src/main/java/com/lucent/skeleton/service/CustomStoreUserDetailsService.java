package com.lucent.skeleton.service;


import com.lucent.skeleton.entities.StoreUser;
import com.lucent.skeleton.repository.StoreUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lucent
 */
@Service
public class CustomStoreUserDetailsService implements UserDetailsService {

    @Autowired
    StoreUserRepository storeUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        return storeUserRepository.findByUsername(username);
    }

    void deleteUserByUsername(String username) {
        StoreUser user = storeUserRepository.findByUsername(username);
        storeUserRepository.deleteById(user.getId());
    }


    @Transactional
    public StoreUser loadUserById(Long id){
        return storeUserRepository.findById(id).get();
    }

    public StoreUser saveUser(StoreUser storeUser){
        return storeUserRepository.save(storeUser);
    }
}
