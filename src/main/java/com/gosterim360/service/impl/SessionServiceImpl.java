package com.gosterim360.service.impl;

import com.gosterim360.common.MessageUtil;
import com.gosterim360.dto.request.SessionRequestDTO;
import com.gosterim360.dto.response.SessionResponseDTO;
import com.gosterim360.mapper.SessionMapper;
import com.gosterim360.model.Session;
import com.gosterim360.repository.SessionRepository;
import com.gosterim360.service.SessionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final MessageUtil messageUtil;

    @Override
    public List<SessionResponseDTO> getAllSessions() {
        log.info("SessionServiceImpl:: getAllSessions started");

        List<SessionResponseDTO> sessions = sessionRepository.findAll().stream()
                .map(sessionMapper::toDTO)
                .toList();

        log.info("SessionServiceImpl:: getAllSessions finished - Found {} sessions", sessions.size());
        return sessions;
    }

    @Override
    public SessionResponseDTO getSessionById(UUID id) {
        log.info("SessionServiceImpl:: getSessionById started - ID: {}", id);

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("SessionServiceImpl:: getSessionById - Session not found for ID: {}", id);
                    return new EntityNotFoundException(messageUtil.getMessage("session.notfound", id));
                });

        log.info("SessionServiceImpl:: getSessionById finished - Session found");
        return sessionMapper.toDTO(session);
    }

    @Override
    public SessionResponseDTO updateSession(UUID id, SessionRequestDTO dto) {
        log.info("SessionServiceImpl:: updateSession started - ID: {}, Request: {}", id, dto);

        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("SessionServiceImpl:: updateSession - Session not found for ID: {}", id);
                    return new EntityNotFoundException(messageUtil.getMessage("session.notfound", id));
                });

        session.setDate(dto.getDate());
        session.setPrice(dto.getPrice());

        session.getTimes().clear();
        if (dto.getTimes() != null) {
            session.getTimes().addAll(
                    dto.getTimes().stream()
                            .map(timeDTO -> {
                                var time = new com.gosterim360.model.SessionTime();
                                time.setTime(timeDTO.getTime());
                                time.setSession(session);
                                return time;
                            }).toList()
            );
        }

        Session updated = sessionRepository.save(session);

        log.info("SessionServiceImpl:: updateSession finished - Updated ID: {}", updated.getId());
        return sessionMapper.toDTO(updated);
    }

    @Override
    public void deleteSessionById(UUID id) {
        log.info("SessionServiceImpl:: deleteSessionById started - ID: {}", id);

        if (!sessionRepository.existsById(id)) {
            log.warn("SessionServiceImpl:: deleteSessionById - Session not found for ID: {}", id);
            throw new EntityNotFoundException(messageUtil.getMessage("session.notfound", id));
        }

        sessionRepository.deleteById(id);

        log.info("SessionServiceImpl:: deleteSessionById finished - Deleted ID: {}", id);
    }

    @Override
    public List<SessionResponseDTO> getSessionsByFilmId(UUID filmId) {
        log.info("SessionServiceImpl:: getSessionsByFilmId started - filmId: {}", filmId);
        List<SessionResponseDTO> sessions = sessionRepository.findByMovie_Id(filmId)
                .stream()
                .map(sessionMapper::toDTO)
                .toList();
        log.info("SessionServiceImpl:: getSessionsByFilmId finished - Found {} sessions", sessions.size());
        return sessions;
    }
}
