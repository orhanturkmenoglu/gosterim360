package com.gosterim360.repository;

import com.gosterim360.model.Salon;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class SalonRepositoryTest {

    @Autowired
    private SalonRepository salonRepository;

    @Test
    @DisplayName("Should save and find salon by id")
    void testSaveAndFindById() {
        Salon salon = Salon.builder()
                .name("Test Salon")
                .location("Test Location")
                .seatCapacity(100)
                .build();

        Salon saved = salonRepository.save(salon);

        assertNotNull(saved.getId());
        assertTrue(salonRepository.findById(saved.getId()).isPresent());
        assertEquals("Test Salon", saved.getName());
    }

    @Test
    @DisplayName("Should check existence by name")
    void testExistsByName() {
        Salon salon = Salon.builder()
                .name("UniqueName")
                .location("Loc")
                .seatCapacity(50)
                .build();

        salonRepository.save(salon);

        assertTrue(salonRepository.existsByName("UniqueName"));
        assertFalse(salonRepository.existsByName("NonExistingName"));
    }

    @Test
    @DisplayName("Should delete salon")
    void testDeleteSalon() {
        Salon salon = Salon.builder()
                .name("DeleteMe")
                .location("Loc")
                .seatCapacity(10)
                .build();

        Salon saved = salonRepository.save(salon);
        UUID id = saved.getId();

        salonRepository.deleteById(id);

        assertFalse(salonRepository.findById(id).isPresent());
    }
    @Test
    @DisplayName("Should update salon details")
    void testUpdateSalon() {
        Salon salon = Salon.builder()
                .name("Old Name")
                .location("Old Location")
                .seatCapacity(100)
                .build();

        Salon saved = salonRepository.save(salon);
        saved.setName("New Name");
        saved.setLocation("New Location");
        saved.setSeatCapacity(200);

        Salon updated = salonRepository.save(saved);

        assertEquals("New Name", updated.getName());
        assertEquals("New Location", updated.getLocation());
        assertEquals(200, updated.getSeatCapacity());
    }


    @Test
    @DisplayName("Should not allow duplicate salon names if unique constraint is set")
    void testUniqueNameConstraint() {
        Salon salon1 = Salon.builder()
                .name("UniqueSalon")
                .location("Loc1")
                .seatCapacity(10)
                .build();
        salonRepository.save(salon1);

        Salon salon2 = Salon.builder()
                .name("UniqueSalon")
                .location("Loc2")
                .seatCapacity(20)
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> {
            salonRepository.saveAndFlush(salon2);
        });
    }
}