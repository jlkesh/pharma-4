package com.onlinepharma.onlinepharma;

import com.onlinepharma.onlinepharma.domain.auth.Users;
import com.onlinepharma.onlinepharma.enums.Language;
import com.onlinepharma.onlinepharma.enums.Status;
import com.onlinepharma.onlinepharma.properties.OpenApiProperties;
import com.onlinepharma.onlinepharma.repository.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties({
        OpenApiProperties.class
})
@RequiredArgsConstructor
public class OnlinePharmaApplication {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public static void main(String[] args) {
        SpringApplication.run(OnlinePharmaApplication.class, args);
    }

//    @Bean
    CommandLineRunner runner() {
        return (args) -> {
            Users admin = new Users();
            admin.setLanguage(Language.EN);
            admin.setSerialNumber("AB1231234");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setStatus(Status.ACTIVE);
            admin.setCreatedBy(-1L);
            userRepository.save(admin);
        };
    }


}
