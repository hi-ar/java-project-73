package hexlet.code.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.junit5.api.DBRider;
import hexlet.code.dto.UserDto;
import hexlet.code.model.QUser;
import hexlet.code.model.User;
import hexlet.code.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DBRider
//@DataSet("users.yaml")
public class UserControllerTest {
    public static final String PATH = "/api/users";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(UserControllerTest.class);

    @Test
    // Файл с данными для тестов (фикстуры)
    //@DataSet(value="datasets/users.yaml",transactional = true) //nw
    @DataSet("users.yaml")
    void testWelcomePage() throws Exception {
        logger.info("§§§§§ Now Table contains : " + userRepository.findAll());
        //v1
        MockHttpServletResponse response = mockMvc
                .perform(get("/welcome"))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HTTP_OK);
        assertThat(response.getContentAsString()).contains("Welcome to Spring");
    }
/*
    @Test
    void testCreateUserPositive() throws Exception {
        // отправить json на эндпоинт,
        UserDto userDto = new UserDto("One", "First", "1@gmail.com", "111");
        ObjectMapper mapper = new ObjectMapper();
        String jsonUserDto = mapper.writeValueAsString(userDto);
        logger.info(jsonUserDto + " §§§§§§§§");
        // проверяем, что отображается созданный юзер
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post(PATH)
                        .content(jsonUserDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HTTP_OK);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("One", "First", "1@gmail.com");
        //обратиться по айди чтобы отобразить карточку
        long id = findByDto(userDto).getId();
        MockHttpServletResponse response1 = mockMvc
                .perform(get(PATH + "/" + id))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HTTP_OK);
        assertThat(response.getContentAsString()).contains("One", "First", "1@gmail.com");
    }

    @Test
    void testCreateUserNegative() throws Exception {
        UserDto userDto = new UserDto("", "First", "1@gmail.com", "111");
        ObjectMapper mapper = new ObjectMapper();
        String jsonUserDto = mapper.writeValueAsString(userDto);
        logger.info(jsonUserDto + " §§§§§§§§");
        // проверяем, что отображается созданный юзер
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.post(PATH)
                        .content(jsonUserDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HTTP_BAD_REQUEST);
        assertThat(response.getErrorMessage()).contains("Invalid request content");
//        assertThat(response.getContentAsString()).contains("should not be empty"); //how?
    }

    @Test
    void testUpdateUserPositive() throws Exception {
        UserDto userDto = new UserDto("One", "First", "1@gmail.com", "111");
        UserDto userDto1 = new UserDto("Jan", "Mon", "111@gmail.com", "111111");
        ObjectMapper mapper = new ObjectMapper();
        String jsonUserDto = mapper.writeValueAsString(userDto);
        String jsonUserDto1 = mapper.writeValueAsString(userDto);
        //без фикстуры! создаем
        MockHttpServletResponse response1 = mockMvc
                .perform(MockMvcRequestBuilders.post(PATH)
                        .content(jsonUserDto1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Обновляем и проверяем, что отображается обновленный юзер
        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders.put(PATH + "/1")
                        .content(jsonUserDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        logger.info("§§§§§ Now Table contains : " + userRepository.findAll());
        assertThat(response.getStatus()).isEqualTo(HTTP_OK);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("One", "First", "1@gmail.com");
    }
    @Test
    void testGetUserByIdPositive() throws Exception {
        //без риквестбилдера
        MockHttpServletResponse response = mockMvc
                .perform(get(PATH + "/1"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HTTP_OK);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Mon", "Jan", "1@gmail.com");
    }
    @Test
    void testGetUsersPositive() throws Exception {
        //без риквестбилдера
        MockHttpServletResponse response = mockMvc
                .perform(get(PATH))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HTTP_OK);
        assertThat(response.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response.getContentAsString()).contains("Mon", "Jan", "1@gmail.com");
    }
    @Test
    void testDeleteUserPositive() throws Exception {
        //без риквестбилдера
        MockHttpServletResponse response = mockMvc
                .perform(delete(PATH + "/2"))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HTTP_OK);

        MockHttpServletResponse response1 = mockMvc
                .perform(get(PATH))
                .andReturn()
                .getResponse();

        assertThat(response1.getContentType()).isEqualTo(MediaType.APPLICATION_JSON.toString());
        assertThat(response1.getContentAsString()).doesNotContain("Tue", "Feb", "2@gmail.com");
    }
 */
    private User findByDto(UserDto dto) {
        List<User> usersToReturn = (List) userRepository.findAll(
                QUser.user.firstName.eq(dto.getFirstName())
                        .and(QUser.user.lastName.eq(dto.getLastName()))
                        .and(QUser.user.email.eq(dto.getEmail()))
        );
        if (usersToReturn.size() != 1) {
            throw new ResponseStatusException(HttpStatus.MULTIPLE_CHOICES,
                    "Can't specify User, there are few, with such combination"); //после сообщения еще есть экспшен
        }
        return usersToReturn.get(0);
    }
}
