package com.gosterim360.repository;

import com.gosterim360.model.Salon;
import com.gosterim360.model.Seat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SeatRepositoryTest {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SalonRepository salonRepository;

    private Salon createSalon(String name, String location, int seatCapacity) {
        Salon salon = Salon.builder()
                .name(name)
                .location(location)
                .seatCapacity(seatCapacity)
                .build();
        return salonRepository.save(salon);
    }

    @Test
    @DisplayName("existsBySalonIdAndRowNumberAndSeatNumber returns correct result")
    void testExistsBySalonIdAndRowNumberAndSeatNumber() {
        Salon salon = createSalon("Test Salon", "Test Location", 100);

        Seat seat = Seat.builder()
                .salon(salon)
                .rowNumber(1)
                .seatNumber(1)
                .build();
        seatRepository.save(seat);

        assertTrue(seatRepository.existsBySalonIdAndRowNumberAndSeatNumber(salon.getId(), 1, 1));
        assertFalse(seatRepository.existsBySalonIdAndRowNumberAndSeatNumber(salon.getId(), 2, 1));
    }

    @Test
    @DisplayName("Different seats in the same salon")
    void testDifferentSeatsInSameSalon() {
        Salon salon = createSalon("Main Hall", "Baku", 200);

        Seat seat1 = Seat.builder().salon(salon).rowNumber(1).seatNumber(1).build();
        Seat seat2 = Seat.builder().salon(salon).rowNumber(1).seatNumber(2).build();
        seatRepository.save(seat1);
        seatRepository.save(seat2);

        assertTrue(seatRepository.existsBySalonIdAndRowNumberAndSeatNumber(salon.getId(), 1, 1));
        assertTrue(seatRepository.existsBySalonIdAndRowNumberAndSeatNumber(salon.getId(), 1, 2));
        assertFalse(seatRepository.existsBySalonIdAndRowNumberAndSeatNumber(salon.getId(), 2, 1));
    }

    @Test
    @DisplayName("Same seat numbers in different salons")
    void testSameSeatNumbersInDifferentSalons() {
        Salon salon1 = createSalon("Hall 1", "Sumqayit", 50);
        Salon salon2 = createSalon("Hall 2", "Ganja", 60);

        Seat seat1 = Seat.builder().salon(salon1).rowNumber(1).seatNumber(1).build();
        Seat seat2 = Seat.builder().salon(salon2).rowNumber(1).seatNumber(1).build();
        seatRepository.save(seat1);
        seatRepository.save(seat2);

        assertTrue(seatRepository.existsBySalonIdAndRowNumberAndSeatNumber(salon1.getId(), 1, 1));
        assertTrue(seatRepository.existsBySalonIdAndRowNumberAndSeatNumber(salon2.getId(), 1, 1));
    }

    @Test
    @DisplayName("Seat CRUD operations")
    void testSeatCrudOperations() {
        Salon salon = createSalon("VIP Hall", "Shaki", 30);

        Seat seat = Seat.builder().salon(salon).rowNumber(2).seatNumber(5).build();
        seat = seatRepository.save(seat);
        assertNotNull(seat.getId());

        Optional<Seat> found = seatRepository.findById(seat.getId());
        assertTrue(found.isPresent());
        assertEquals(2, found.get().getRowNumber());
        assertEquals(5, found.get().getSeatNumber());

        seat.setRowNumber(3);
        seatRepository.save(seat);
        Seat updated = seatRepository.findById(seat.getId()).orElseThrow();
        assertEquals(3, updated.getRowNumber());

        seatRepository.delete(seat);
        assertFalse(seatRepository.findById(seat.getId()).isPresent());
    }

    @Test
    @DisplayName("Add and delete multiple seats in one salon")
    void testMultipleSeatsAddAndDelete() {
        Salon salon = createSalon("Cinema", "İst", 120);

        Seat seat1 = Seat.builder().salon(salon).rowNumber(1).seatNumber(1).build();
        Seat seat2 = Seat.builder().salon(salon).rowNumber(1).seatNumber(2).build();
        seatRepository.saveAll(List.of(seat1, seat2));

        List<Seat> allSeats = seatRepository.findAll();
        assertEquals(2, allSeats.size());

        seatRepository.delete(seat1);
        allSeats = seatRepository.findAll();
        assertEquals(1, allSeats.size());
        assertEquals(2, allSeats.get(0).getSeatNumber());
    }
}