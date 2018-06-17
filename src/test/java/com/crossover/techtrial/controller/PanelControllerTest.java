package com.crossover.techtrial.controller;

import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * PanelControllerTest class will test all APIs in PanelController.java.
 *
 * @author Crossover
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PanelControllerTest {

    MockMvc mockMvc;

    @Mock
    private PanelController panelController;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(panelController).build();
    }

    @Test
    public void testPanelShouldBeRegistered() throws Exception {
        HttpEntity<Object> panel = getHttpEntity(
                "{\"serial\": \"232323\", \"longitude\": \"54.123232\","
                        + " \"latitude\": \"54.123232\",\"brand\":\"tesla\" }");
        ResponseEntity<Panel> response = template.postForEntity(
                "/api/register", panel, Panel.class);
        Assert.assertEquals(202, response.getStatusCode().value());
    }

    private HttpEntity<Object> getHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<Object>(body, headers);
    }

    @Test
    public void registerPanel() {

    }

    @Test
    public void shouldSaveHourlyElectricity() throws IOException {
        HttpEntity<Object> hourlyElectricity = getHttpEntity(
                StreamUtils.copyToString(getClass().getResourceAsStream("/hourlyElectricity.json"),
                        Charset.forName("UTF-8")));

        ResponseEntity<HourlyElectricity> response = template.postForEntity(
                "/api/panels/232323/hourly", hourlyElectricity, HourlyElectricity.class);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals(new Long(1), response.getBody().getGeneratedElectricity());
        assertNotNull(response.getBody().getReadingAt());

    }

    @Test
    public void hourlyElectricity() throws IOException {

        HttpEntity<Object> hourlyElectricity = getHttpEntity(
                StreamUtils.copyToString(getClass().getResourceAsStream("/hourlyElectricityArray.json"),
                        Charset.forName("UTF-8")));

        ParameterizedTypeReference<List<HourlyElectricity>> listOfHourlyElec =
                new ParameterizedTypeReference<List<HourlyElectricity>>() {};

        ResponseEntity<List<HourlyElectricity>> response = template.exchange("/api/panels/232323/hourly",
                HttpMethod.GET,
                null,listOfHourlyElec);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1,response.getBody().size());

    }

    @Test
    public void allDailyElectricityFromYesterday() {
    }
}
