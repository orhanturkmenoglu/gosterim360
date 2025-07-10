package com.gosterim360.repository;

import com.gosterim360.model.Salon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SalonRepository extends JpaRepository<Salon, UUID> {

    boolean existsByName(String name);
}