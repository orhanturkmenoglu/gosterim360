package com.gosterim360.repository;

import com.gosterim360.model.Salon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SalonRepository extends JpaRepository<Salon, UUID> {

    boolean existsByName(String name);
}