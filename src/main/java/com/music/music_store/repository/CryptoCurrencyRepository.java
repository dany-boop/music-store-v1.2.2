package com.music.music_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.music.music_store.entity.CryptoCurrency;

@Repository
public interface CryptoCurrencyRepository extends JpaRepository<CryptoCurrency, String> {

}
