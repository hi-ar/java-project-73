package java.hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.dto.UserDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {
    private static final String PATH = "/api/users";
    @Autowired
    MockMvc mockMvc;
    private Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Test
    void testCreateUserPositive() throws Exception {
        // отправить json на эндпоинт,
        UserDto userDto = new UserDto("One", "First", "1@gmail.com", "111");
        ObjectMapper mapper = new ObjectMapper();
        String jsonUserDto = mapper.writeValueAsString(userDto);
        logger.info(jsonUserDto + " §§§§§§§§");

        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .content(jsonUserDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
