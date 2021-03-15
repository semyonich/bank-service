package com.pet.bankservice.service.impl;

import com.pet.bankservice.entity.User;
import com.pet.bankservice.exception.DataProcessingException;
import com.pet.bankservice.repository.UserRepository;
import com.pet.bankservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public User save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User get(Long id) {
        return userRepository.get(id)
                .orElseThrow(() -> new DataProcessingException("User not found, id=" + id));
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new DataProcessingException("User not found, phoneNumber="
                        + phoneNumber));
    }

    @Override
    public void removeById(Long id) {
        userRepository.deleteById(id);
    }
}
