package de.hey_car;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.hey_car.dto.CountryWallet;
import de.hey_car.dto.User;
import de.hey_car.dto.Wallet;
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
public class WalletrIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSaveWallet() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        CountryWallet countryWallet = CountryWallet.builder().amount(2000.0).currency("INR").build();
        List<CountryWallet> countryWalletList = new ArrayList<>();
        countryWalletList.add(countryWallet);
        String requestJson = ow.writeValueAsString(Wallet.builder().mobile(12342333L).countryWallet(countryWalletList).build());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/wallet/create")
                .content(requestJson).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

}
