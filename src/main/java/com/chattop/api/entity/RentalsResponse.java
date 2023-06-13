package com.chattop.api.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Log4j2
@Data
public class RentalsResponse {
    private ArrayList<Rental> rentals;

    public RentalsResponse(List<Rental> rentalList) {
        ArrayList<Rental> rentalsTrans= new ArrayList<>();
        rentalList.forEach(e->rentalsTrans.add(e));
        log.info(rentalsTrans.toString());
        this.rentals = rentalsTrans;
    }
}
