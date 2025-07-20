package com.tripscript.app.city.repository;

import com.tripscript.app.city.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

}
