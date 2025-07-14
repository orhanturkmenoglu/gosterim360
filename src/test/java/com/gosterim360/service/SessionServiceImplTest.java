package com.gosterim360.service;

import com.gosterim360.common.MessageUtil;
import com.gosterim360.dto.request.SessionRequestDTO;
import com.gosterim360.dto.request.SessionTimeRequestDTO;
import com.gosterim360.dto.response.SessionResponseDTO;
import com.gosterim360.mapper.SessionMapper;
import com.gosterim360.model.Session;
import com.gosterim360.model.SessionTime;
import com.gosterim360.repository.SessionRepository;
import com.gosterim360.service.impl.SessionServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionServiceImplTest {

    @InjectMocks
    private SessionServiceImpl sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private SessionMapper sessionMapper;

    @Mock
    private MessageUtil messageUtil;

    private Session session;
    private SessionRequestDTO sessionRequestDTO;
    private SessionResponseDTO sessionResponseDTO;
    private UUID sessionId;


    @BeforeEach
    public void setUp() {
        sessionId = UUID.randomUUID();

        SessionTime sessionTime = new SessionTime();
        sessionTime.setTime(LocalDateTime.of(2025, 7, 15, 18, 30));

        session = new Session();
        session.setId(sessionId);
        session.setDate(LocalDate.of(2025, 7, 15));
        session.setPrice(BigDecimal.valueOf(100));
        session.setTimes(new ArrayList<>(List.of(sessionTime)));

        SessionTimeRequestDTO timeDTO = SessionTimeRequestDTO.builder()
                .time(LocalDateTime.of(2025, 7, 15, 18, 30))
                .build();

        sessionRequestDTO = SessionRequestDTO.builder()
                .date(LocalDate.of(2025, 7, 15))
                .price(BigDecimal.valueOf(150))
                .times(List.of(timeDTO))
                .build();

        sessionResponseDTO = SessionResponseDTO.builder()
                .id(sessionId)
                .date(LocalDate.of(2025, 7, 15))
                .price(BigDecimal.valueOf(150))
                .build();
    }


    @Test
    public void getAllSessions_ShouldReturnListOfSessionResponseDTO() {
        when(sessionRepository.findAll()).thenReturn(List.of(session));
        when(sessionMapper.toDTO(session)).thenReturn(sessionResponseDTO);

        List<SessionResponseDTO> result = sessionService.getAllSessions();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(sessionRepository, times(1)).findAll();
    }

    @Test
    public void getSessionById_ShouldReturnSessionResponseDTO_WhenSessionExists() {
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(sessionMapper.toDTO(session)).thenReturn(sessionResponseDTO);

        SessionResponseDTO result = sessionService.getSessionById(sessionId);

        assertNotNull(result);
        assertEquals(sessionId, result.getId());
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void getSessionById_ShouldThrowException_WhenSessionNotFound() {
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());
        when(messageUtil.getMessage("session.notfound", sessionId)).thenReturn("Session not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> sessionService.getSessionById(sessionId));

        assertEquals("Session not found", exception.getMessage());
    }

    @Test
    public void updateSession_ShouldUpdateAndReturnSessionResponseDTO() {
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));
        when(sessionRepository.save(any(Session.class))).thenAnswer(i -> i.getArgument(0));
        when(sessionMapper.toDTO(any(Session.class))).thenReturn(sessionResponseDTO);

        SessionResponseDTO result = sessionService.updateSession(sessionId, sessionRequestDTO);

        assertNotNull(result);
        assertEquals(sessionResponseDTO.getId(), result.getId());
        verify(sessionRepository).save(any(Session.class));
    }

    @Test
    public void updateSession_ShouldThrowException_WhenSessionNotFound() {
        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());
        when(messageUtil.getMessage("session.notfound", sessionId)).thenReturn("Session not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> sessionService.updateSession(sessionId, sessionRequestDTO));

        assertEquals("Session not found", exception.getMessage());
    }

    @Test
    public void deleteSessionById_ShouldDeleteSession_WhenExists() {
        when(sessionRepository.existsById(sessionId)).thenReturn(true);

        sessionService.deleteSessionById(sessionId);

        verify(sessionRepository, times(1)).deleteById(sessionId);
    }

    @Test
    public void deleteSessionById_ShouldThrowException_WhenSessionNotFound() {
        when(sessionRepository.existsById(sessionId)).thenReturn(false);
        when(messageUtil.getMessage("session.notfound", sessionId)).thenReturn("Session not found");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> sessionService.deleteSessionById(sessionId));

        assertEquals("Session not found", exception.getMessage());
    }
}
