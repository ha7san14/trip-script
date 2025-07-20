package com.tripscript.app.city.controller;

import com.tripscript.app.city.model.City;
import com.tripscript.app.city.service.CityService;
import com.tripscript.app.constants.Endpoints;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CityController {

    private final CityService cityService;

    @GetMapping(Endpoints.GET_ALL_CITIES)
    public ResponseEntity<List<City>> getAllCities() {
        List<City> cities = cityService.getAllCities();
        if (cities.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cities);
    }

    @GetMapping(Endpoints.GET_CITY)
    public ResponseEntity<City> getCity(@PathVariable Long cityId) {
        City city = cityService.getCity(cityId);
        if (city != null) {
            return ResponseEntity.ok(city);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
