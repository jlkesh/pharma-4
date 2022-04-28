package com.onlinepharma.onlinepharma;

import com.onlinepharma.onlinepharma.domain.auth.Users;
import com.onlinepharma.onlinepharma.enums.Language;
import com.onlinepharma.onlinepharma.enums.Status;
import com.onlinepharma.onlinepharma.properties.OpenApiProperties;
import com.onlinepharma.onlinepharma.repository.auth.UserRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

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
            admin.setPrincipal("AB1231234");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setStatus(Status.ACTIVE);
            admin.setCreatedBy(-1L);
            userRepository.save(admin);
        };
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean("messageSource")
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("i18n/messages");
        source.setDefaultLocale(Locale.forLanguageTag("uz"));
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        return source;
    }

}
