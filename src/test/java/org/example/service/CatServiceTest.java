package org.example.service;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CatServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAddCat() throws Exception {
        mockMvc.perform(get("/cat/" + 4))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.age", is(15))
                );
    }
}
