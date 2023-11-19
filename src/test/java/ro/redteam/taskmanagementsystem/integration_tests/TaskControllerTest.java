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
import ro.redteam.taskmanagementsystem.models.dtos.TaskDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ro.redteam.taskmanagementsystem.enums.Priority.HIGH;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Transactional
@AutoConfigureTestDatabase
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTaskShouldPass() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("Create DTOs");
        taskDTO.setDescription("Create DTOs");
        taskDTO.setPriority(HIGH);

        MvcResult result = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();

        TaskDTO taskDTOConverted = objectMapper.readValue(resultAsString, new TypeReference<>() {
        });

        assertEquals(taskDTOConverted.getDescription(), taskDTO.getDescription());
        assertEquals(taskDTOConverted.getTitle(), taskDTO.getTitle());
        assertEquals(taskDTOConverted.getPriority(), taskDTO.getPriority());
    }

    @Test
    void testCreateTaskWithEmptyFiledShouldFail() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("");
        taskDTO.setDescription("");

        MvcResult result = mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskDTO)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        assertTrue(resultAsString.contains("Filed empty"));
    }
}