package com.cine8.service;

import com.cine8.dto.ChangePasswordDTO;
import com.cine8.dto.UpdateUserDTO;
import com.cine8.entity.User;
import com.cine8.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Save user
    public User saveUser(User user) {


        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado.");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("El username ya existe.");
        }

        user.setRole("USER");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // Find All
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Find by id
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("El id de usuario no existe."));
    }

    // Find by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("El email no existe."));
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    // Change Password
    public void changePassword(ChangePasswordDTO requestPasswordDTO) {

        // Obtiene el usuario autenticado del contexto de seguridad de Spring
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No autenticado");
        }

        // Usamos el email del token en vez del id para evitar manipulación del cliente
        String email = authentication.getName();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("El email no existe."));

        if (!passwordEncoder.matches(requestPasswordDTO.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("La contraseña actual no es correcta.");
        }


        user.setPassword(passwordEncoder.encode(requestPasswordDTO.getNewPassword()));
        userRepository.save(user);
    }

    //Update
    public User updateUser(UpdateUserDTO updateUserDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No autenticado");
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("El email no existe."));

        if (updateUserDTO.getName() != null) {
            user.setName(updateUserDTO.getName());
        }

        if (updateUserDTO.getSurnames() != null) {
            user.setSurnames(updateUserDTO.getSurnames());
        }

        if (updateUserDTO.getUsername() != null) {
            user.setUsername(updateUserDTO.getUsername());
        }

        if (updateUserDTO.getEmail() != null) {
            user.setEmail(updateUserDTO.getEmail());
        }

        return userRepository.save(user);
    }

}
