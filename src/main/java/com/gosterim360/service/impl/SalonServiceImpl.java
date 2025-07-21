package com.gosterim360.service.impl;

import com.gosterim360.dto.request.SalonRequestDTO;
import com.gosterim360.dto.response.SalonResponseDTO;
import com.gosterim360.exception.*;
import com.gosterim360.mapper.SalonMapper;
import com.gosterim360.model.Salon;
import com.gosterim360.repository.SalonRepository;
import com.gosterim360.service.SalonService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SalonServiceImpl implements SalonService {

    //salon anotasyon olacak slf4j
    private static final Logger log = LoggerFactory.getLogger(SalonServiceImpl.class);

    private final SalonRepository salonRepository;

    @Override
    @Transactional
    public SalonResponseDTO create(SalonRequestDTO request) {
        log.info("Attempting to create a new cinema hall with name: {}", request.getName());
        if (salonRepository.existsByName(request.getName())) {
            log.warn("Cinema hall creation failed: name '{}' already exists", request.getName());
            throw new SalonAlreadyExistsException("A cinema hall with this name already exists.");
        }
        if (request.getSeatCapacity() < 1) {
            log.warn("Cinema hall creation failed: invalid seat capacity ({})", request.getSeatCapacity());
            throw new SalonSeatCapacityInvalidException("Seat capacity must be at least 1.");
        }
        Salon salon = SalonMapper.toEntity(request);
        Salon saved = salonRepository.saveAndFlush(salon);
        log.info("Cinema hall created successfully with id: {}", saved.getId());
        return SalonMapper.toDTO(saved);
    }

    @Override
    @Transactional
    public SalonResponseDTO update(UUID id, SalonRequestDTO request) {
        log.info("Attempting to update cinema hall with id: {}", id);
        Salon salon = salonRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cinema hall update failed: id '{}' not found", id);
                    return new SalonNotFoundException("Cinema hall not found.");
                });
        if (!salon.getName().equals(request.getName()) && salonRepository.existsByName(request.getName())) {
            log.warn("Cinema hall update failed: name '{}' already exists", request.getName());
            throw new SalonUpdateConflictException("Another cinema hall with this name already exists.");
        }
        if (request.getSeatCapacity() < 1) {
            log.warn("Cinema hall update failed: invalid seat capacity ({})", request.getSeatCapacity());
            throw new SalonSeatCapacityInvalidException("Seat capacity must be at least 1.");
        }
        salon.setName(request.getName());
        salon.setLocation(request.getLocation());
        salon.setSeatCapacity(request.getSeatCapacity());
        Salon updated = salonRepository.saveAndFlush(salon);
        log.info("Cinema hall updated successfully with id: {}", updated.getId());
        return SalonMapper.toDTO(updated);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        log.info("Attempting to delete cinema hall with id: {}", id);
        Salon salon = salonRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cinema hall deletion failed: id '{}' not found", id);
                    return new SalonNotFoundException("Cinema hall not found.");
                });
        salonRepository.delete(salon);
        log.info("Cinema hall deleted successfully with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public SalonResponseDTO getById(UUID id) {
        log.info("Fetching cinema hall with id: {}", id);
        Salon salon = salonRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Cinema hall fetch failed: id '{}' not found", id);
                    return new SalonNotFoundException("Cinema hall not found.");
                });
        log.info("Cinema hall fetched successfully with id: {}", id);
        return SalonMapper.toDTO(salon);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SalonResponseDTO> getAll() {
        log.info("Fetching all cinema halls");
        List<SalonResponseDTO> salons = salonRepository.findAll()
                .stream()
                .map(SalonMapper::toDTO)
                .collect(Collectors.toList());
        log.info("Fetched {} cinema halls", salons.size());
        return salons;
    }
}