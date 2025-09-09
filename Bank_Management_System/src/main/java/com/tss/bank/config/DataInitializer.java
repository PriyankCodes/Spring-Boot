package com.tss.bank.config;

import com.tss.bank.entity.User;
import com.tss.bank.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        initializeDefaultUsers();
    }

    private void initializeDefaultUsers() {
        // Create default admin user if not exists
        if (!userRepository.existsByEmail("admin@bank.com")) {
            User admin = new User();
            admin.setEmail("admin@bank.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setPhone("9876543210");
            admin.setRole(User.Role.ADMIN);
            admin.setStatus(User.Status.ACTIVE);
            
            userRepository.save(admin);
            log.info("Default admin user created: admin@bank.com / admin123");
        }

        // Create default customer user if not exists
        if (!userRepository.existsByEmail("customer@bank.com")) {
            User customer = new User();
            customer.setEmail("customer@bank.com");
            customer.setPassword(passwordEncoder.encode("customer123"));
            customer.setPhone("9876543211");
            customer.setRole(User.Role.CUSTOMER);
            customer.setStatus(User.Status.ACTIVE);
            
            userRepository.save(customer);
            log.info("Default customer user created: customer@bank.com / customer123");
        }
    }
}
