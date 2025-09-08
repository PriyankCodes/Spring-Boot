package com.tss.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tss.security.entity.Role;
import com.tss.security.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create default roles if they don't exist
        if (!roleRepository.existsByRolename("ADMIN")) {
            Role adminRole = new Role();
            adminRole.setRolename("ADMIN");
            roleRepository.save(adminRole);
        }

        if (!roleRepository.existsByRolename("USER")) {
            Role userRole = new Role();
            userRole.setRolename("USER");
            roleRepository.save(userRole);
        }

        if (!roleRepository.existsByRolename("MODERATOR")) {
            Role moderatorRole = new Role();
            moderatorRole.setRolename("MODERATOR");
            roleRepository.save(moderatorRole);
        }
    }
}
