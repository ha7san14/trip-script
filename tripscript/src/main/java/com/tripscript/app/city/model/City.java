package com.tripscript.app.city.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CityId")
    private long cityId;
    @Column(name = "CityName")
    @NotBlank(message = "City Name is required")
    private String cityName;
    @Column(name = "Country")
    @NotBlank(message = "Country is required")
    private String country;
    @Column(name = "Details", length = 1000)
    @NotBlank(message = "Details are required")
    private String details;
}
