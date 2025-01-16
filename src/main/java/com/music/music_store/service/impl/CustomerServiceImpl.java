package com.music.music_store.service.impl;

import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.music.music_store.dto.req.CustomerReq;
import com.music.music_store.dto.req.CustomerUpdateReq;
import com.music.music_store.dto.req.PageReq;
import com.music.music_store.dto.res.CustomerRes;
import com.music.music_store.entity.Customer;
import com.music.music_store.entity.User;
import com.music.music_store.repository.CustomerRepository;
import com.music.music_store.service.CustomerService;
import com.music.music_store.service.UserService;
import com.music.music_store.util.LogUtil;
import com.music.music_store.util.MapperUtil;
import com.music.music_store.util.SortUtil;
import com.music.music_store.util.ValidationUtil;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomerServiceImpl implements CustomerService {
    private final UserService userService;
    private final EntityManager entityManager;
    private final ValidationUtil validationUtil;
    private final CustomerRepository customerRepository;

    @Override
    public Page<CustomerRes> getAll(PageReq req) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter("deletedFilter");
        filter.setParameter("isDeleted", req.isDeleted());
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize(), SortUtil.parseSort(req.getSort()));
        Page<Customer> customers = customerRepository.findAll(pageable);
        session.disableFilter("deletedFilter");
        return customers.map(MapperUtil::toCustomerRes);
    }

    @Override
    public CustomerRes getById(String id) {
        return MapperUtil.toCustomerRes(getOne(id));
    }

    @Override
    public Customer getOne(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer Not Found"));
    }

    @Override
    public CustomerRes create(CustomerReq req) {
        validationUtil.validate(req);
        User user = userService.getOne(req.getUserId());
        Customer customer = Customer.builder()
                .name(req.getName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .user(user)
                .address(req.getAddress())
                .build();
        customerRepository.saveAndFlush(customer);
        return MapperUtil.toCustomerRes(customer);
    }

    @Override
    public CustomerRes update(String id, CustomerUpdateReq req) {
        LogUtil.info("updating customer");
        validationUtil.validate(req);
        Customer customer = getOne(id);
        customer.setName(req.getName());
        customer.setEmail(req.getEmail());
        customer.setPhone(req.getPhone());
        customerRepository.saveAndFlush(customer);
        LogUtil.info("finished updating customer");
        return MapperUtil.toCustomerRes(customer);
    }

    @Override
    public void delete(String id) {
        LogUtil.info("deleting customer");
        Customer customer = getOne(id);
        customer.setDeleted(!customer.isDeleted());
        customerRepository.save(customer);
        LogUtil.info("finished deleting customer");
    }

    @Override
    public boolean existsByIdAndUserId(String id, String userId) {
        return customerRepository.existsByIdAndUserId(id, userId);
    }
}
