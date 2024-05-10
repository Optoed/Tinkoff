package org.example.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UrlCreationRequest(@JsonProperty String longURL) {
}
