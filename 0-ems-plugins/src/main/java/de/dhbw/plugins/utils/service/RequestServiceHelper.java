package de.dhbw.plugins.utils.service;

import java.io.IOException;
import java.net.URL;

public interface RequestServiceHelper {

    String stream(URL url) throws IOException;

}
