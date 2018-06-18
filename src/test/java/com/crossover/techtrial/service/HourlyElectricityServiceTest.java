package com.crossover.techtrial.service;

import com.crossover.techtrial.model.HourlyElectricity;
import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.repository.HourlyElectricityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HourlyElectricityServiceTest {

    List<HourlyElectricity> hourlyElectricitiesPanel1 = new ArrayList<>();
    List<HourlyElectricity> hourlyElectricitiesPanel2 = new ArrayList<>();

    @Mock
    private HourlyElectricityRepository hourlyElectricityRepository;

    private HourlyElectricityService hourlyElectricityService;

    @Test
    public void shouldGetHourlyElectricityForPanelId1() {
        Pageable pageable = PageRequest.of(0, 5);

        when(hourlyElectricityRepository.findAllByPanelIdOrderByReadingAtDesc(1L, pageable)).thenReturn(
                new PageImpl<>(hourlyElectricitiesPanel1));

        Page<HourlyElectricity> hourlyElectricityPage = hourlyElectricityService.getAllHourlyElectricityByPanelId(1l, pageable);

        assertNotNull(hourlyElectricityPage);
        assertEquals(5, hourlyElectricityPage.getNumberOfElements());

        for (HourlyElectricity hourlyElectricity : hourlyElectricityPage.getContent()) {
            assertNotNull(hourlyElectricity.getId());
            assertNotNull(hourlyElectricity.getReadingAt());
            assertNotNull(hourlyElectricity.getGeneratedElectricity());
            assertEquals(new Long(1), Long.valueOf(hourlyElectricity.getPanel().getId()));
            assertEquals("123", hourlyElectricity.getPanel().getSerial());
            assertEquals("EDF", hourlyElectricity.getPanel().getBrand());
            assertNotNull(hourlyElectricity.getPanel().getLatitude());
            assertNotNull(hourlyElectricity.getPanel().getLongitude());


        }

        verify(hourlyElectricityRepository, atLeastOnce()).findAllByPanelIdOrderByReadingAtDesc(1L, pageable);
    }

    @Test
    public void shouldGetHourlyElectricityForPanelId2() {
        Pageable pageable = PageRequest.of(0, 5);

        when(hourlyElectricityRepository.findAllByPanelIdOrderByReadingAtDesc(2L, pageable)).thenReturn(
                new PageImpl<>(hourlyElectricitiesPanel2));

        Page<HourlyElectricity> hourlyElectricityPage = hourlyElectricityService.getAllHourlyElectricityByPanelId(2l, pageable);

        assertNotNull(hourlyElectricityPage);
        assertEquals(3, hourlyElectricityPage.getNumberOfElements());
        for (HourlyElectricity hourlyElectricity : hourlyElectricityPage.getContent()) {
            assertNotNull(hourlyElectricity.getReadingAt());
            assertNotNull(hourlyElectricity.getGeneratedElectricity());
            assertEquals(new Long(2), Long.valueOf(hourlyElectricity.getPanel().getId()));
            assertEquals("234", hourlyElectricity.getPanel().getSerial());
            assertEquals("BRITISHGAS", hourlyElectricity.getPanel().getBrand());
            assertNotNull(hourlyElectricity.getPanel().getLatitude());
            assertNotNull(hourlyElectricity.getPanel().getLongitude());
        }

        verify(hourlyElectricityRepository, atLeastOnce()).findAllByPanelIdOrderByReadingAtDesc(2L, pageable);

    }

    @Test
    public void shouldSaveHourlyElectricity() {

        HourlyElectricity hourlyElectricity = new HourlyElectricity();
        hourlyElectricity.setPanel(getPanel1());
        hourlyElectricity.setReadingAt(LocalDateTime.now());
        hourlyElectricity.setGeneratedElectricity(1l);
        hourlyElectricity.setId(new Random().nextLong());

        when(hourlyElectricityRepository.save(hourlyElectricity)).thenReturn(
                hourlyElectricity);

        HourlyElectricity hourlyElectricityResult = hourlyElectricityService.save(hourlyElectricity);

        verify(hourlyElectricityRepository, atLeastOnce()).save(hourlyElectricity);

        assertEquals(hourlyElectricity, hourlyElectricityResult);

    }

    @Before
    public void setup() {

        Random random = new Random();

        LongStream.range(0, 5).boxed().forEach(e -> {
            HourlyElectricity hourlyElectricity = new HourlyElectricity();
            hourlyElectricity.setPanel(getPanel1());
            hourlyElectricity.setReadingAt(LocalDateTime.now().minusHours(e));
            hourlyElectricity.setGeneratedElectricity(e + 1);
            hourlyElectricity.setId(random.nextLong());
            hourlyElectricitiesPanel1.add(hourlyElectricity);
        });


        LongStream.range(0, 3).boxed().forEach(e -> {
            HourlyElectricity hourlyElectricity = new HourlyElectricity();
            hourlyElectricity.setPanel(getPanel2());
            hourlyElectricity.setReadingAt(LocalDateTime.now().minusHours(e));
            hourlyElectricity.setGeneratedElectricity(e + 1);
            hourlyElectricity.setId(random.nextLong());
            hourlyElectricitiesPanel2.add(hourlyElectricity);
        });


        hourlyElectricityService = new HourlyElectricityServiceImpl();
        ((HourlyElectricityServiceImpl) hourlyElectricityService).setHourlyElectricityRepository(hourlyElectricityRepository);
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

    private Panel getPanel2() {
        Panel p2 = new Panel();

        p2.setLatitude(52.771317);
        p2.setLongitude(-1.554997);
        p2.setSerial("234");
        p2.setBrand("BRITISHGAS");
        p2.setId(2l);

        return p2;
    }
}