package de.exchange;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.exchange.dto.WalletDetails;
import de.exchange.dto.Wallet;
import de.exchange.entity.WalletEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class WalletIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    private String id;

    private void setup() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        WalletDetails walletDetails = WalletDetails.builder().amount(2000.0).currency("INR").build();
        List<WalletDetails> walletDetailsList = new ArrayList<>();
        walletDetailsList.add(walletDetails);
        String requestJson = ow.writeValueAsString(Wallet.builder().mobile(12342333L).build());
        MvcResult value  = mockMvc.perform(MockMvcRequestBuilders.post("/api/wallet/create")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON)).andReturn();
        mapper.registerModule(new JavaTimeModule());

        WalletEntity userEntity = mapper.readValue(value.getResponse().getContentAsString(), WalletEntity.class);
        id = userEntity.getId();
    }

    @Test
    public void testSaveWallet() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        WalletDetails walletDetails = WalletDetails.builder().amount(2000.0).currency("INR").build();
        List<WalletDetails> walletDetailsList = new ArrayList<>();
        walletDetailsList.add(walletDetails);
        String requestJson = ow.writeValueAsString(Wallet.builder().mobile(12342333L).build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/wallet/create")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void TestWalletTopup() throws Exception {
        setup();

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        WalletDetails walletDetails = WalletDetails.builder().amount(2000.0).walletId(id).currency("INR").build();

        String requestJson = ow.writeValueAsString(walletDetails);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/wallet/top-up")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
