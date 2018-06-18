package com.crossover.techtrial.service;

import com.crossover.techtrial.dto.DailyElectricity;
import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.repository.DailyElectricityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.LongStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * DailyElectricityServiceImplTest class will test all APIs in DailyElectricityService.java.
 *
 * @author Crossover
 */
@RunWith(MockitoJUnitRunner.class)
public class DailyElectricityServiceImplTest {

    @Mock
    private DailyElectricityRepository dailyElectricityRepository;

    private DailyElectricityService dailyElectricityService;

    List<HourlyElectricity> hourlyElectricitiesPanel1 = new ArrayList<>();

    @Before
    public void setup() {

        Random random = new Random();

        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        LongStream.range(0, 5).boxed().forEach(e -> {
            HourlyElectricity hourlyElectricity = new HourlyElectricity();
            hourlyElectricity.setPanel(getPanel1());
            hourlyElectricity.setReadingAt(yesterday.minusHours(e));
            hourlyElectricity.setGeneratedElectricity(e + 1);
            hourlyElectricity.setId(random.nextLong());
            hourlyElectricitiesPanel1.add(hourlyElectricity);
        });


        dailyElectricityService = new DailyElectricityServiceImpl();
        ((DailyElectricityServiceImpl) dailyElectricityService).setDailyElectricityRepository(dailyElectricityRepository);
    }


    @Test
    public void shouldGetDailyElectricityForPanelId1() {
        Pageable pageable = PageRequest.of(0, 5);

        when(dailyElectricityRepository.findAllByPanelIdOrderByReadingAtDesc(1L, pageable)).thenReturn(
                new PageImpl<>(hourlyElectricitiesPanel1));

        List<DailyElectricity> dailyElectricityList = dailyElectricityService.getAllDailyElectricityByPanelId(1l, pageable);

        assertNotNull(dailyElectricityList);
        assertEquals(2, dailyElectricityList.size());
        for (DailyElectricity dailyElectricity : dailyElectricityList) {
            assertNotNull(dailyElectricity.getDate());
            assertNotNull(dailyElectricity.getSum());
            assertNotNull(dailyElectricity.getAverage());
            assertNotNull(dailyElectricity.getMin());
            assertNotNull(dailyElectricity.getMax());
        }


    }

    private Panel getPanel1() {
        Panel p1 = new Panel();

        p1.setLatitude(51.376495);
        p1.setLongitude(-0.100594);
        p1.setSerial("123");
        p1.setBrand("EDF");
        p1.setId(1l);

        return p1;
    }



}