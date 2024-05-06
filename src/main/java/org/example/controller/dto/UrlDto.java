package org.example.controller.dto;

public record UrlDto(@JsonProperty String id,
                     @JsonProperty String longURL,
                     @JsonProperty String shortURL) {

}
