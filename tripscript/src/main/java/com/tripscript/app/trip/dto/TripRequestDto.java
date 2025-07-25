package com.tripscript.app.trip.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TripRequestDto {
    private Long cityId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int rating;
    private String notes;
}
