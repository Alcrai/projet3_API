package com.chattop.api.service;

import com.chattop.api.entity.Rental;
import com.chattop.api.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RentalServiceTest {
    @Mock
    private RentalRepository rentalRepository;

    private RentalService rentalService;
    private Rental rental;

    @BeforeEach
    void init(){
        rentalService = new RentalService(rentalRepository);
        rental = new Rental(1,"Villa du cap", 150.5f,500,"url picture","test",1, Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
    }

    @Test
    void findAll() {
        List<Rental> rentals =  new ArrayList<>();
        rentals.add(rental);
        when(rentalRepository.findAll()).thenReturn(rentals);
        assertThat(rentalService.findAll()).hasSize(1);
        verify(rentalRepository).findAll();
    }

    @Test
    void findById() {
        when(rentalRepository.findById(any(Integer.class))).thenReturn(Optional.of(rental));
        assertThat(rentalService.findById(1)).isEqualTo(rental);
        verify(rentalRepository).findById(any(Integer.class));
    }

    @Test
    void add() {
        Rental rental2 = new Rental(2,"Villa du rocher", 150.5f,500,"url picture","test",1,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        when(rentalRepository.save(any(Rental.class))).thenReturn(rental2);
        assertThat(rentalService.add(rental2)).isEqualTo(rental2);
        verify(rentalRepository).save(any(Rental.class));
    }

    @Test
    void update() {
        Rental rental2 = new Rental(1,"Villa du cap", 150.5f,800,"url picture","test",1,Timestamp.valueOf(LocalDateTime.now()),Timestamp.valueOf(LocalDateTime.now()));
        when(rentalRepository.existsById(any(Integer.class))).thenReturn(true);
        when(rentalRepository.save(any(Rental.class))).thenReturn(rental2);
        assertThat(rentalService.update(rental2).getPrice()).isEqualTo(rental2.getPrice());
        verify(rentalRepository).save(any(Rental.class));
    }

    @Test
    void deleteIdExist() {
        when(rentalRepository.existsById(any(Integer.class))).thenReturn(true);
        rentalService.delete(1);
        verify(rentalRepository).existsById(any(Integer.class));
        verify(rentalRepository).deleteById(any(Integer.class));
    }

    @Test
    void deleteIdNotExist() {
        when(rentalRepository.existsById(any(Integer.class))).thenReturn(false);
        rentalService.delete(1);
        verify(rentalRepository).existsById(any(Integer.class));
        verify(rentalRepository,times(0)).deleteById(any(Integer.class));
    }
}