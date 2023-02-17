package de.dhbw.plugins.utils.service;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EnvironmentService implements EnvironmentServiceHelper {

    private final Environment env;

    public Optional<String> getProperty(String key){
        String value = env.getProperty(key);
        if (value == null)
            return Optional.empty();
        return Optional.of(value);
    }


}
