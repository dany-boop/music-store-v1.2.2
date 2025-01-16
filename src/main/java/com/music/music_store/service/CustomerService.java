package com.music.music_store.service;

import org.springframework.data.domain.Page;

import com.music.music_store.dto.req.CustomerReq;
import com.music.music_store.dto.req.CustomerUpdateReq;
import com.music.music_store.dto.req.PageReq;
import com.music.music_store.dto.res.CustomerRes;
import com.music.music_store.entity.Customer;

public interface CustomerService {
    Page<CustomerRes> getAll(PageReq req);

    CustomerRes getById(String id);

    Customer getOne(String id);

    CustomerRes create(CustomerReq req);

    CustomerRes update(String id, CustomerUpdateReq req);

    void delete(String id);

    boolean existsByIdAndUserId(String id, String userId);
}