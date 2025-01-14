package com.music.music_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music.music_store.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    boolean existsByIdAndUserId(String id, String userId);

}
