package de.hey_car;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.hey_car.dto.User;
import de.hey_car.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UsersIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private String id;
    private String confirmCode;


    public void setup() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(User.builder().firstName("Test").lastName("test").address("test")
                .country("India").email("tetst@rrgqe.com").mobile(321354646L).build());
        MvcResult value = mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
        mapper.registerModule(new JavaTimeModule());

        UserEntity userEntity = mapper.readValue(value.getResponse().getContentAsString(), UserEntity.class);
        id = userEntity.getId();
        confirmCode = userEntity.getConfirmationCode();
    }

    @Test
    public void testSaveUsers() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(User.builder().firstName("Test").lastName("test").address("test")
                .country("India").email("tetst@wgq.com").mobile(321354646L).build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testConfirmUser() throws Exception {
        setup();
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/" + confirmCode + "/" + id + "/confirm")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testGetUser() throws Exception {
        setup();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/" + id)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
