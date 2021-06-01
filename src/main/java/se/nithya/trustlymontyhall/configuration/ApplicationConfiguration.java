package se.nithya.trustlymontyhall.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class ApplicationConfiguration {


    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI apiDoc(Environment env) {

        String springActiveProfile = " (" + String.join(",", env.getActiveProfiles()) + ")";
        Contact contact = new Contact().email("xxx.yyy@gmail.com").name("omega");
        Info info = new Info().title(applicationName)
                .version(String.format("%s", springActiveProfile))
                .contact(contact);
        return new OpenAPI().info(info);
    }
}

