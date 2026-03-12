package com.smartcart.ecommerce.common.listners;

import com.smartcart.ecommerce.modules.user.enums.Provider;
import com.smartcart.ecommerce.modules.user.enums.Roles;
import com.smartcart.ecommerce.modules.user.model.User;
import com.smartcart.ecommerce.modules.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        User admin=User
                .builder()
                .email("admin@smartcart.com")
                .name("Admin")
                .roles(Set.of(Roles.ADMIN,Roles.USER))
                .enabled(true)
                .password(passwordEncoder.encode("Admin@Smartcart"))
                .provider(Provider.USERNAME_AND_PASSWORD)
                .build();

        if(!userRepository.findByEmail("admin@smartcart.com").isPresent()){
            userRepository.save(admin);
        }
    }
}
