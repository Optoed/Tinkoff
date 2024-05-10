package org.example.controller.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UrlCreationResponse(@JsonProperty Long id,
                                 @JsonProperty String longURL,
                                 @JsonProperty String shortURL) {
}
