package com.music.music_store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.music.music_store.constant.OrderStatus;
import com.music.music_store.entity.Transaction;

@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, String>, JpaSpecificationExecutor<Transaction> {
    Optional<Transaction> findOrderByCustomerIdAndOrderStatus(String customerId, OrderStatus orderStatus);

}
