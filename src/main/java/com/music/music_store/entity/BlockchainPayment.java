package com.music.music_store.entity;

import java.time.LocalDateTime;

import com.music.music_store.constant.BlockChainStatus;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_blockchain_payment")
public class BlockchainPayment extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "crypto_currency_id", nullable = false)
    private CryptoCurrency cryptoCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "blockchain_status", length = 50)
    private BlockChainStatus blockchainStatus;

    @Column(name = "blockchain_tx_hash", unique = true, nullable = true)
    private String blockchainTransactionHash;

    @Column(name = "wallet_address", nullable = false)
    private String walletAddress;

    @Column(name = "is_testnet")
    private Boolean isTestnet;

    @Column(name = "blockchain_submitted_at")
    private LocalDateTime blockchainSubmittedAt;

    @Column(name = "blockchain_confirmed_at")
    private LocalDateTime blockchainConfirmedAt;
}
