package com.crossover.techtrial.service;

import com.crossover.techtrial.model.Panel;
import com.crossover.techtrial.repository.PanelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PanelServiceImplTest {

    @Mock
    private PanelRepository panelRepository;

    private PanelService panelService;

    @Before
    public void setup() {
        PanelService panelService = new PanelServiceImpl();
        ((PanelServiceImpl) panelService).setPanelRepository(panelRepository);
    }

    @Test
    public void shouldRegisterPanelSuccessfully() {

        Panel panel = getPanel1();

        when(panelRepository.save(panel)).thenReturn(panel);
        panelService.register(panel);
        verify(panelRepository, atLeastOnce()).save(panel);

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