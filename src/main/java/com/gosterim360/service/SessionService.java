package com.gosterim360.service;

import com.gosterim360.dto.request.SessionRequestDTO;
import com.gosterim360.dto.response.SessionResponseDTO;

import java.util.List;
import java.util.UUID;

public interface SessionService {

    List<SessionResponseDTO> getAllSessions();

    SessionResponseDTO getSessionById(UUID id);

    SessionResponseDTO updateSession(UUID id, SessionRequestDTO sessionRequestDTO);

    void deleteSessionById(UUID id);
}
