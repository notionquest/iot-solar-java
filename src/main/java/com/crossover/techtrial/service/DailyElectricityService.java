package com.crossover.techtrial.service;

import com.crossover.techtrial.dto.DailyElectricity;

import java.util.List;

public interface DailyElectricityService {
    List<DailyElectricity> getAllDailyElectricityByPanelId(Long panelId);
}
