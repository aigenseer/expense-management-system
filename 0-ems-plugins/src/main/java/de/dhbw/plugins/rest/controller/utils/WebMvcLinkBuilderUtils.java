package de.dhbw.plugins.rest.controller.utils;

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpHeaders;

public class WebMvcLinkBuilderUtils {

    private WebMvcLinkBuilderUtils(){}

    public static HttpHeaders createLocationHeader(WebMvcLinkBuilder builder){
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(builder.toUri());
        return headers;
    }
}
