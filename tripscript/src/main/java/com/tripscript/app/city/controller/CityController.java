package com.tripscript.app.city.controller;

import com.tripscript.app.city.model.City;
import com.tripscript.app.city.service.CityService;
import com.tripscript.app.constants.Endpoints;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping(Endpoints.DELETE_CITY)
    public ResponseEntity<Void> deleteCity(@PathVariable Long cityId) {
        City getCity = cityService.getCity(cityId);
        if (getCity != null) {
            cityService.deleteCity(cityId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(Endpoints.CREATE_CITY)
    public ResponseEntity<City> createCity(@Valid @RequestBody City city) {
        City newCity = cityService.createCity(city);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCity);
    }

    @PutMapping(Endpoints.MODIFY_CITY)
    public ResponseEntity<City> updateCity(@Valid @PathVariable Long cityId, @RequestBody City city) {
        City existingCity = cityService.updateCity(cityId, city);
        return ResponseEntity.ok(existingCity);

    }
}
