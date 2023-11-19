package ro.redteam.taskmanagementsystem.integration_tests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ro.redteam.taskmanagementsystem.models.dtos.UserDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Transactional
@AutoConfigureTestDatabase
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateUserShouldPass() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setFirstName("Maria");
        userDTO.setLastName("Ioana");
        userDTO.setEmail("mariaioana@gmail.com");

        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        UserDTO userDTOConverted = objectMapper.readValue(resultAsString, new TypeReference<>() {
        });

        assertEquals(userDTOConverted.getFirstName(), userDTO.getFirstName());
        assertEquals(userDTOConverted.getLastName(), userDTO.getLastName());
        assertEquals(userDTOConverted.getEmail(), userDTO.getEmail());
    }

    @Test
    void testCreateUserWithEmptyFiledShouldFail() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("");
        userDTO.setFirstName("");
        userDTO.setLastName("");

        MvcResult result = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        assertTrue(resultAsString.contains("Filed empty"));
    }
}