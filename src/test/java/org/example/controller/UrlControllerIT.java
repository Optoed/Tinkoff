package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.controller.dto.response.UrlCreationResponse;
import org.example.dao.repository.UrlRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@AutoConfigureMockMvc
public class UrlControllerIT {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UrlRepository urlRepository;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    void clear() {
        urlRepository.deleteAll();
    }

    @Test
    void add_url_test() throws Exception {
        String contentAsString = mockMvc.perform(post("/url/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "longurl" : "https://www.youtube.com/"
                                }
                                """))
                .andExpectAll(
                        status().isOk()
                ).andReturn().getResponse().getContentAsString();
        UrlCreationResponse urlCreationResponse = objectMapper.readValue(contentAsString, UrlCreationResponse.class);

        mockMvc.perform(get("/url/" + urlCreationResponse.id()))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.longurl", is("https://www.youtube.com/")),
                        jsonPath("$.shorturl", is("1"))
                );
    }

}
