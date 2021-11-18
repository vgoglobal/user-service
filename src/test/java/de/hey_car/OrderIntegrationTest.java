package de.hey_car;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.hey_car.dto.*;
import de.hey_car.repository.MinerRepository;
import de.hey_car.entity.MinerEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class OrderIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private String id;
    private String confirmCode;
    @Mock
    MinerRepository minerRepository;

    private void setup() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(Order.builder().fromCurrency("USD").toCurrency("EUR")
                .amount(2500.0).recipientAmount(200010.00).recipientId("test")
                .transferFee(10.0).userId("test").refId("test").build());
        String minerRequestJson = ow.writeValueAsString(Miner.builder()
                .resourceAddress("test")
                .resourceCode("USD").resourceCurrency("USD")
                .resourceName("test").resourceNumber("test").userId("test").resourceType(MinerResourceType.BANK).build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/miner/resources/create")
                .content(minerRequestJson).contentType(MediaType.APPLICATION_JSON)).andReturn();

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/order/exchange/create")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON)).andReturn();

        new ArrayList<MinerEntity>().add(MinerEntity.builder().resourceNumber("13213213").resourceName("test").resourceCurrency("USD").resourceCode("test").resourceAddress("test").id("test").build());

        mapper.registerModule(new JavaTimeModule());
        Transfer transfer = mapper.readValue(mvcResult.getResponse().getContentAsString(), Transfer.class);
        id = transfer.getOrderId();
    }

    @Test
    public void testSaveOrder() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(Order.builder().fromCurrency("USD").toCurrency("EUR")
                .amount(2500.0).recipientAmount(200010.00).recipientId("test")
                .transferFee(10.0).userId("test").refId("test").build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/exchange/create")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
    }

    @Test
    public void testGetOrderByCurrency() throws Exception {
        setup();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/USD")
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testPickOrder() throws Exception {
        setup();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/pick/test/" + id)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testUpdateOrderStatus() throws Exception {
        setup();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/update/test/" + id)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    @Ignore
    public void testConfirmOrder() throws Exception {
        setup();
        String fileName = "test.txt";
        MockMultipartFile sampleFile = new MockMultipartFile(
                "uploaded-file",
                fileName,
                "text/plain",
                "This is the file content".getBytes()
        );

        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/api/order/confirm/test/" + id);

        mockMvc.perform(multipartRequest.file(sampleFile)).andExpect(status().isOk());
    }
}
