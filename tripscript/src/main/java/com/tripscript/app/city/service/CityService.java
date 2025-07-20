package com.tripscript.app.city.service;

import com.tripscript.app.city.model.City;
import com.tripscript.app.city.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City getCity(Long cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }
}
