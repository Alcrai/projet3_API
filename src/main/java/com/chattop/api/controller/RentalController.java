package com.chattop.api.controller;

import com.chattop.api.entity.MessageResponse;
import com.chattop.api.entity.Rental;
import com.chattop.api.entity.RentalResponse;
import com.chattop.api.entity.RentalsResponse;
import com.chattop.api.service.FilesStorageService;
import com.chattop.api.service.RentalService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin("http://localhost:3001")
@Log4j2
public class RentalController {
    @Autowired
    private RentalService rentalService;
    @Autowired
    private FilesStorageService storageService;

    @GetMapping("/api/rentals")
    public RentalsResponse getRentalList(){
        return new RentalsResponse(rentalService.findAll());
    }

    @GetMapping("/api/rentals/{id}")
    public Rental getRentalById(@PathVariable("id")int id){
        return rentalService.findById(id);
    }

    @PostMapping(value = "/api/rentals/{ownerId}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RentalResponse> postRental(@PathVariable("ownerId")int ownerId, @RequestParam("name")String name,
                                                      @RequestParam("surface")String surface,
                                                      @RequestParam("price")String price,
                                                      @RequestParam("picture")MultipartFile picture,
                                                      @RequestParam("description")String description){
        List<String> fileNames = new ArrayList<>();
        Arrays.asList(picture).stream().forEach(file -> {
            storageService.save(file);
            fileNames.add(file.getOriginalFilename());
        });
        Rental rental = rentalService.create(0,name, Float.parseFloat(surface), Float.parseFloat(price), fileNames.get(0), description, ownerId);
        log.info(rental.toString());
        Rental rentalAddedd = rentalService.add(rental);
        if (rentalAddedd.getId()==0){
            log.error("failed to save Rental");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new RentalResponse("failed to save Rental"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new RentalResponse("Rental created !"));
    }

    @PutMapping(value = "/api/rentals/{id}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<RentalResponse> postRental(@PathVariable("id")int id,
                                                     @RequestParam("name")String name,
                                                     @RequestParam("surface")String surface,
                                                     @RequestParam("price")String price,
                                                     @RequestParam("description")String description){
        Rental rental = rentalService.create(id, name, Float.parseFloat(surface), Float.parseFloat(price), "picture", description, 0);
        log.info(rental.toString());
        Rental rentalUpdated = rentalService.update(rental);
        if (rentalUpdated.getId()==0){
            log.error("failed to update Rental");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new RentalResponse("failed to update Rental"));
        }
        log.info(new MessageResponse("Rental updated !"));
        return ResponseEntity.status(HttpStatus.OK).body(new RentalResponse("Rental updated !"));
    }

}
