package org.example.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UrlDto(@JsonProperty String id,
                     @JsonProperty String longURL,
                     @JsonProperty String shortURL) {
}
