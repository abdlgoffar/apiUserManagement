package apiUserManagement;


import apiUserManagement.repositories.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import apiUserManagement.entities.User;
import apiUserManagement.models.request.RegisterUserRequest;
import apiUserManagement.models.response.CoreResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTesting {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private UserRepository userRepository;

    @Autowired
    public UserTesting(MockMvc mockMvc, ObjectMapper objectMapper, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }
    @BeforeEach
    public void deleteAllDataOnDatabase() {
        this.userRepository.deleteAll();
    }

    @Test
    void registerSuccessfully() throws Exception {

        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("Abdul Goffar");
        request.setUsername("Abdul Goffar");
        request.setPassword("Abdul Goffar 123");

        this.mockMvc.perform(
                post("/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            CoreResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
            assertEquals("OK", response.getPayload());
        });
    }
    @Test
    void registerBadRequest() throws Exception {

        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("");
        request.setUsername("");
        request.setPassword("");

        this.mockMvc.perform(
                post("/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            CoreResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
            assertNotNull(response.getError());
        });
    }

    @Test
    void registerDuplicateAccount() throws Exception {
        this.userRepository.save(new User("abdul goffar", "abdulgoffar123", "abdul goffar"));

        RegisterUserRequest request = new RegisterUserRequest();
        request.setName("abdul goffar");
        request.setUsername("abdul goffar");
        request.setPassword("abdulgoffar123");

        this.mockMvc.perform(
                post("/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            CoreResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
            assertNotNull(response.getError());
        });
    }

}

