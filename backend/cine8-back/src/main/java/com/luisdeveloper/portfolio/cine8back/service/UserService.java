package com.luisdeveloper.portfolio.cine8back.service;

import com.luisdeveloper.portfolio.cine8back.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.luisdeveloper.portfolio.cine8back.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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


    public User findById(Integer id) {

        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("El id de usuario no existe."));
    }

    public User findByemail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("El email no existe."));
    }

    public void delete(Integer id) {

        userRepository.deleteById(id);
    }

    public void changePassword( String oldPassword, String newPassword) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No autenticado");
        }
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("El email no existe."));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("La contraseña actual no es correcta.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);
    }


}
