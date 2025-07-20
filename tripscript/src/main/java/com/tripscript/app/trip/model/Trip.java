package com.tripscript.app.trip.model;

import com.tripscript.app.city.model.City;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TripId")
    private long tripId;
    @ManyToOne(optional = false)
    @JoinColumn(name = "CityId")
    private City city;
    @Column(name = "StartDate")
    private LocalDate startDate;
    @Column(name = "EndDate")
    private LocalDate endDate;
    @Column(name = "Rating")
    private int rating;
    @Column(name = "Notes", length = 1000)
    private String notes;
}
