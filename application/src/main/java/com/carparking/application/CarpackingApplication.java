package com.carparking.application;

import com.carparking.application.repository.AccountRepository;
import com.carparking.application.repository.RoleRepository;
import com.carparking.core_entity.entities.Account;
import com.carparking.core_entity.entities.RoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
@EntityScan("com.carparking.*")
@ComponentScan("com.carparking.*")
public class CarpackingApplication extends SpringBootServletInitializer {
    @Autowired
    RoleRepository roleRepository;
    public static void main(String[] args) {
        SpringApplication.run(CarpackingApplication.class, args);
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CarpackingApplication.class);
    }

    @Bean
    CommandLineRunner init( AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return args -> {
            RoleModel adminRole = roleRepository.findByName("ADMIN");
            if (adminRole == null) {
                adminRole = new RoleModel();
                adminRole.setName("ADMIN");
                roleRepository.save(adminRole);
            }
            RoleModel userRole = roleRepository.findByName("USER");
            if (userRole == null) {
                userRole = new RoleModel();
                userRole.setName("USER");
                roleRepository.save(userRole);
            }

            Account adminAccount = accountRepository.findByUsername("admin");
            if (adminAccount == null) {
                RoleModel adminRoleData = roleRepository.findByName("ADMIN");
                List<RoleModel> roles = new ArrayList<>();
                roles.add(adminRoleData);
                adminAccount = new Account();
                adminAccount.setUsername("admin");
                adminAccount.setPassword(bCryptPasswordEncoder.encode("123456"));
                adminAccount.setFullName("Quản trị viên");
                adminAccount.setEmail("admin@gmail.com");
                adminAccount.setRoles(roles);
                accountRepository.save(adminAccount);
            }
        };
    }

}
