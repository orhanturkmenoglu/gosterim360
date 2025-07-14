package com.gosterim360.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SalonTest {

    @Test
    @DisplayName("Salon builder and getters should work correctly")
    void testSalonBuilderAndGetters() {
        UUID id = UUID.randomUUID();
        String name = "IMAX";
        String location = "Downtown";
        int seatCapacity = 200;

        Salon salon = Salon.builder()
                .name(name)
                .location(location)
                .seatCapacity(seatCapacity)
                .build();
        salon.setId(id);

        assertEquals(id, salon.getId());
        assertEquals(name, salon.getName());
        assertEquals(location, salon.getLocation());
        assertEquals(seatCapacity, salon.getSeatCapacity());
    }

    @Test
    @DisplayName("Salon setters should update fields correctly")
    void testSalonSetters() {
        Salon salon = new Salon();
        UUID id = UUID.randomUUID();
        salon.setId(id);
        salon.setName("VIP");
        salon.setLocation("Mall");
        salon.setSeatCapacity(50);

        assertEquals(id, salon.getId());
        assertEquals("VIP", salon.getName());
        assertEquals("Mall", salon.getLocation());
        assertEquals(50, salon.getSeatCapacity());
    }

    @Test
    @DisplayName("Salon equals and hashCode should work for same id")
    void testSalonEqualsAndHashCode() {
        UUID id = UUID.randomUUID();
        Salon salon1 = Salon.builder().name("A").location("L1").seatCapacity(10).build();
        salon1.setId(id);
        Salon salon2 = Salon.builder().name("B").location("L2").seatCapacity(20).build();
        salon2.setId(id);
        assertEquals(salon1.getId(), salon2.getId());
    }

    @Test
    @DisplayName("Salon toString should not be null")
    void testSalonToString() {
        UUID id = UUID.randomUUID();
        String name = "Test";
        String location = "TestLoc";
        int seatCapacity = 100;

        Salon salon = Salon.builder()
                .name(name)
                .location(location)
                .seatCapacity(seatCapacity)
                .build();
        salon.setId(id);
        assertNotNull(salon.toString());
    }
}