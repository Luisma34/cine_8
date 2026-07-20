package com.luisdeveloper.portfolio.cine8back.service;

import com.luisdeveloper.portfolio.cine8back.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.luisdeveloper.portfolio.cine8back.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User saveUser(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya esá registrado.");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("El username ya existe.");
        }

        return userRepository.save(user);
    }

    public List<User> findAll() {

        return userRepository.findAll();
    }


    public void findById(Integer id) {
        userRepository.findById(id);
    }

    public void findByemail(String email) {
        userRepository.findByEmail(email);
    }

    public void delete(Integer id) {

        userRepository.deleteById(id);
    }


}
