package com.tripscript.app.City.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CityId")
    private long cityId;
    @Column(name = "CityName")
    private String cityName;
    @Column(name = "Country")
    private String country;
    @Column(name = "Details", length = 1000)
    private String details;
}
