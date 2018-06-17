package com.crossover.techtrial.repository;

import com.crossover.techtrial.model.HourlyElectricity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * HourlyElectricity Repository is for all operations for HourlyElectricity.
 * @author Crossover
 */
@RestResource(exported = false)
public interface DailyElectricityRepository
        extends PagingAndSortingRepository<HourlyElectricity,Long> {

    List<HourlyElectricity> getAllDailyElectricityByPanelIdOrderByDate(Long panelId);

}
