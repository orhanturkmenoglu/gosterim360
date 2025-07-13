package com.gosterim360.service.impl;

import com.gosterim360.dto.request.SeatRequestDTO;
import com.gosterim360.dto.response.SeatResponseDTO;
import com.gosterim360.exception.*;
import com.gosterim360.model.Salon;
import com.gosterim360.model.Seat;
import com.gosterim360.repository.SalonRepository;
import com.gosterim360.repository.SeatRepository;
import com.gosterim360.mapper.SeatMapper;
import com.gosterim360.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SalonRepository salonRepository;
    private final SeatMapper seatMapper;

    @Override
    public SeatResponseDTO createSeat(SeatRequestDTO request) {
        log.info("Request to create seat: salonId={}, rowNumber={}, seatNumber={}",
                request.getSalonId(), request.getRowNumber(), request.getSeatNumber());

        UUID salonId = parseUUID(request.getSalonId(), "Invalid salonId: " + request.getSalonId());
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> {
                    log.error("Salon not found for seat creation. salonId={}", salonId);
                    return new SalonNotFoundException("Salon not found: " + salonId);
                });

        validateSeatNumbers(request.getRowNumber(), request.getSeatNumber());

        if (seatRepository.existsBySalonIdAndRowNumberAndSeatNumber(salonId, request.getRowNumber(), request.getSeatNumber())) {
            log.warn("Duplicate seat creation attempt: salonId={}, rowNumber={}, seatNumber={}",
                    salonId, request.getRowNumber(), request.getSeatNumber());
            throw new SeatAlreadyExistsException("Seat already exists in this salon at the specified row and seat number");
        }

        Seat seat = seatMapper.toEntity(request);
        seat.setSalon(salon);
        Seat saved = seatRepository.save(seat);

        log.info("Seat created successfully. seatId={}, salonId={}, rowNumber={}, seatNumber={}",
                saved.getId(), salonId, saved.getRowNumber(), saved.getSeatNumber());
        return seatMapper.toDTO(saved);
    }

    @Override
    public SeatResponseDTO getSeatById(UUID id) {
        log.info("Fetching seat by id: {}", id);
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Seat not found. seatId={}", id);
                    return new SeatNotFoundException("Seat not found: " + id);
                });
        log.debug("Seat found: seatId={}, salonId={}, rowNumber={}, seatNumber={}",
                seat.getId(), seat.getSalon().getId(), seat.getRowNumber(), seat.getSeatNumber());
        return seatMapper.toDTO(seat);
    }

    @Override
    public List<SeatResponseDTO> getAllSeats() {
        log.info("Retrieving all seats from database");
        List<Seat> seats = seatRepository.findAll();
        log.info("Total seats found: {}", seats.size());
        return seatMapper.toDTOList(seats);
    }

    @Override
    public SeatResponseDTO updateSeat(UUID id, SeatRequestDTO request) {
        log.info("Request to update seat: seatId={}, newSalonId={}, newRowNumber={}, newSeatNumber={}",
                id, request.getSalonId(), request.getRowNumber(), request.getSeatNumber());

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Seat not found for update. seatId={}", id);
                    return new SeatNotFoundException("Seat not found: " + id);
                });

        UUID salonId = parseUUID(request.getSalonId(), "Invalid salonId: " + request.getSalonId());
        Salon salon = salonRepository.findById(salonId)
                .orElseThrow(() -> {
                    log.error("Salon not found for seat update. salonId={}", salonId);
                    return new SalonNotFoundException("Salon not found: " + salonId);
                });

        validateSeatNumbers(request.getRowNumber(), request.getSeatNumber());

        boolean isDuplicate = seatRepository.existsBySalonIdAndRowNumberAndSeatNumber(salonId, request.getRowNumber(), request.getSeatNumber())
                && !(seat.getSalon().getId().equals(salonId)
                && seat.getRowNumber() == request.getRowNumber()
                && seat.getSeatNumber() == request.getSeatNumber());

        if (isDuplicate) {
            log.warn("Duplicate seat update attempt: seatId={}, salonId={}, rowNumber={}, seatNumber={}",
                    id, salonId, request.getRowNumber(), request.getSeatNumber());
            throw new SeatAlreadyExistsException("Seat already exists in this salon at the specified row and seat number");
        }

        seat.setSalon(salon);
        seat.setRowNumber(request.getRowNumber());
        seat.setSeatNumber(request.getSeatNumber());
        Seat updated = seatRepository.save(seat);

        log.info("Seat updated successfully. seatId={}, salonId={}, rowNumber={}, seatNumber={}",
                updated.getId(), salonId, updated.getRowNumber(), updated.getSeatNumber());
        return seatMapper.toDTO(updated);
    }

    @Override
    public void deleteSeat(UUID id) {
        log.info("Request to delete seat: seatId={}", id);
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Seat not found for deletion. seatId={}", id);
                    return new SeatNotFoundException("Seat not found: " + id);
                });
        seatRepository.delete(seat);
        log.info("Seat deleted successfully. seatId={}", id);
    }

    private void validateSeatNumbers(Integer rowNumber, Integer seatNumber) {
        if (rowNumber == null || rowNumber < 1) {
            log.error("Invalid row number: {}", rowNumber);
            throw new SeatRowOutOfBoundsException("Row number must be at least 1");
        }
        if (seatNumber == null || seatNumber < 1) {
            log.error("Invalid seat number: {}", seatNumber);
            throw new InvalidSeatNumberException("Seat number must be at least 1");
        }
    }

    private UUID parseUUID(String value, String errorMessage) {
        try {
            return UUID.fromString(value);
        } catch (Exception e) {
            log.error("Failed to parse UUID from value: {}", value);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}