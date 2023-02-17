package de.dhbw.plugins.utils.service;

import java.util.Optional;

public interface EnvironmentServiceHelper {

    Optional<String> getProperty(String key);
}
