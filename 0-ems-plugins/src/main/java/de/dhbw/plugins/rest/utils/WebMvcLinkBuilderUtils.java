package de.dhbw.plugins.rest.utils;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;

public class WebMvcLinkBuilderUtils {

    public static HttpHeaders createLocationHeader(WebMvcLinkBuilder builder){
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.toUri());
        return headers;
    }
}
