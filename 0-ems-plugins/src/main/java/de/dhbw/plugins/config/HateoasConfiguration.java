package de.dhbw.plugins.config;

import de.ingogriebsch.spring.hateoas.siren.SirenConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HateoasConfiguration {

    @Bean
    public SirenConfiguration sirenConfiguration() {
        return new SirenConfiguration().withEntityAndCollectionModelSubclassingEnabled(true);
    }
}
