package org.example.social_meli.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.social_meli.dto.UserResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deberia dejar de seguir a un usuario")
    void postUnfollowUser() throws Exception {
        Integer userId = 1;
        Integer userIdToUnfollow = 2;

        String url = String.format("/users/%d/unfollow/%d", userId, userIdToUnfollow);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType("application/json"))
                .andDo(print())
                .andExpect(content().string("{\"user_id\":1,\"user_name\":\"wcalderwood0\",\"follower\":[]}"))
                .andExpect(status().isOk());
    }
}