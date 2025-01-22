package com.music.music_store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music.music_store.constant.BlockChainStatus;
import com.music.music_store.entity.BlockchainPayment;
import com.music.music_store.entity.Payment;

@Repository
public interface BlockchainPaymentRepository extends JpaRepository<BlockchainPayment, String> {
    Optional<BlockchainPayment> findByBlockchainTransactionHash(String txHash);

    List<BlockchainPayment> findByStatus(BlockChainStatus status);

    Optional<BlockchainPayment> findByPayment(Payment payment);
}
