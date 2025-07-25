package com.tripscript.app.city.service;

import com.tripscript.app.city.model.City;
import com.tripscript.app.city.repository.CityRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return cityRepository.findById(cityId).orElseThrow(() ->
                new EntityNotFoundException("City with ID " + cityId + " not found."));
    }

    public void deleteCity(Long cityId) {
        if (!cityRepository.existsById(cityId)) {
            throw new EntityNotFoundException("City with ID " + cityId + " not found.");
        }
        cityRepository.deleteById(cityId);

    }

    public City createCity(City city) {
        return cityRepository.save(city);
    }

    public City updateCity(Long cityId, City city) {
        City existingCity = cityRepository.findById(cityId).orElseThrow(() ->
                new EntityNotFoundException("City with ID " + cityId + " not found."));
        existingCity.setCityName(city.getCityName());
        existingCity.setCountry(city.getCountry());
        existingCity.setDetails(city.getDetails());

        return cityRepository.save(existingCity);
    }
}
