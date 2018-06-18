package com.crossover.techtrial.service;

import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.repository.PanelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PanelServiceImplTest {

    @Mock
    private PanelRepository panelRepository;

    private PanelService panelService;

    @Before
    public void setup() {
        panelService = new PanelServiceImpl();
        ((PanelServiceImpl) panelService).setPanelRepository(panelRepository);
    }

    @Test
    public void shouldRegisterPanelSuccessfully() {

        Panel panel = getPanel1();

        when(panelRepository.save(panel)).thenReturn(panel);
        panelService.register(panel);
        verify(panelRepository, atLeastOnce()).save(panel);

    }

    @Test
    public void shouldFindThePanelBySerialNumber() {

        Panel panel = getPanel1();

        when(panelRepository.findBySerial("123")).thenReturn(panel);
        Panel panelResult = panelService.findBySerial("123");
        verify(panelRepository, atLeastOnce()).findBySerial("123");

        assertNotNull(panelResult);
        assertEquals(panel, panelResult);
        assertEquals(panel.getBrand(), panelResult.getBrand());
        assertEquals(panel.getSerial(), panelResult.getSerial());
        assertEquals(panel.getLatitude(), panelResult.getLatitude());
        assertEquals(panel.getLongitude(), panelResult.getLongitude());
        assertEquals(panel.getId(), panelResult.getId());

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