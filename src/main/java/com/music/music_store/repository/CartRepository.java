package com.music.music_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.music.music_store.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, String> {

}
