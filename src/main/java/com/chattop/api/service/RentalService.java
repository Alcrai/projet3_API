package com.chattop.api.service;


import com.chattop.api.entity.Rental;
import com.chattop.api.repository.RentalRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
@Log4j2
public class RentalService {
    private RentalRepository rentalRepository;
    @Autowired
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public Rental create(int id, String name, float surface, float price, String picture, String description, int ownerId){
        Rental rental = new Rental();
        if(rentalRepository.existsById(id)){ //cas update
            rental.setId(id);
            rental.setName(name);
            rental.setSurface(surface);
            rental.setPrice(price);
            rental.setPicture(rentalRepository.findById(id).get().getPicture());
            rental.setDescription(description);
            rental.setOwner_id(rentalRepository.findById(id).get().getOwner_id());
            rental.setCreated_at(rentalRepository.findById(id).get().getCreated_at());
            rental.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        } else { //cas save
            rental.setName(name);
            rental.setSurface(surface);
            rental.setPrice(price);
            rental.setPicture("http://localhost:3001/api/uploads/"+picture);
            log.info(picture);
            rental.setDescription(description);
            rental.setOwner_id(ownerId);
            rental.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
            rental.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        }
        return rental;
    }
    public List<Rental> findAll(){
        log.info("List of RENTALS");
        return rentalRepository.findAll();
    }

    public Rental findById(int id){
        Optional<Rental> result = rentalRepository.findById(id);
        log.info("View rental by Id:" + id);
        return result.get();
    }

    public Rental add(Rental rental){
        log.info("Add rental:" + rental.toString());
        rental.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        rental.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
        return rentalRepository.save(rental);
    }

    public Rental update(Rental rental){
        if(rentalRepository.existsById(rental.getId())) {
            rental.setUpdated_at(Timestamp.valueOf(LocalDateTime.now()));
            rentalRepository.save(rental);
            log.info("Update rental:" + rental.toString());
            return rental;
        }else{ return null;}
    }

    public void delete(int id){
        if (rentalRepository.existsById(id)){
            rentalRepository.deleteById(id);
            log.info("deleted rental id :" + id);
        } else {
            log.error("rental does not exist");
        }
    }


}
