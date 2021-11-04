package de.hey_car.client;

import de.hey_car.dto.Countries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component

public class HubSpotClient {
    private static final Logger LOGGER = LogManager.getLogger(HubSpotClient.class);
    @Autowired
    private RestTemplate restTemplate;

    String hubSpotHost;

    String getPath;

    String postPath;

    String apiKey;

    public Countries postHubSpotCountries(Countries countries) {
        LOGGER.info("URL: {}", hubSpotHost+postPath+apiKey);

        Countries ct = restTemplate.postForEntity(hubSpotHost+postPath+apiKey, countries, Countries.class).getBody();
        return ct;
    }

    public List getHubspotCountries() {
        return restTemplate.getForEntity(hubSpotHost+getPath+apiKey, List.class).getBody();
    }
}
