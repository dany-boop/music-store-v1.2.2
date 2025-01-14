package com.music.music_store.entity;

import java.time.LocalDateTime;

import com.music.music_store.constant.BlockChainStatus;
import com.music.music_store.constant.PaymentMethod;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_payment")
public class Payment extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_reference", unique = true, nullable = true)
    private String paymentReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "blockchain_status", length = 50)
    private BlockChainStatus blockchainStatus;

    @Column(name = "blockchain_tx_hash", unique = true, nullable = true)
    private String blockchainTransactionHash;

    @Column(name = "amount", nullable = true)
    private Double amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDateTime paymentDate;

    @Column(name = "blockchain_submitted_at")
    private LocalDateTime blockchainSubmittedAt;

    @Column(name = "blockchain_confirmed_at")
    private LocalDateTime blockchainConfirmedAt;
}
