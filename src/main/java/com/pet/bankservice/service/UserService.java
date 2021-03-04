package com.pet.bankservice.service;

import com.pet.bankservice.entity.User;

public interface UserService {
    User save(User user);

    User get(Long id);

    User findByPhoneNumber(String phoneNumber);

    void removeById(Long id);
}
