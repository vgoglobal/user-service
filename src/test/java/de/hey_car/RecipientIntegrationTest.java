package de.hey_car;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.hey_car.dto.Miner;
import de.hey_car.dto.MinerResourceType;
import de.hey_car.dto.Recipient;
import de.hey_car.repository.entity.MinerEntity;
import de.hey_car.repository.entity.RecipientEntity;
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

import static de.hey_car.dto.MinerResourceType.BANK;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class RecipientIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private String id;
    private String confirmCode;


    private void setup() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String minerRequestJson = ow.writeValueAsString(Recipient.builder()
                .address("test").code("test").currency("test").institution("test")
                .institutionType("test").name("test").number("tesr").userId("test").build());
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/recipient/create")
                .content(minerRequestJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
        mapper.registerModule(new JavaTimeModule());
        RecipientEntity minerEntity = mapper.readValue(mvcResult.getResponse().getContentAsString(), RecipientEntity.class);
        id = minerEntity.getUserId();
    }

    @Test
    public void testCreateRecipient() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String minerRequestJson = ow.writeValueAsString(Recipient.builder()
                .address("test").code("test").currency("test").institution("test")
                .institutionType("test").name("test").number("test").userId("test").build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/recipient/create")
                .content(minerRequestJson).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testGetMiner() throws Exception {
        setup();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/recipient/" + id)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}