package com.gosterim360.controller;

import com.gosterim360.dto.request.SessionRequestDTO;
import com.gosterim360.dto.response.SessionResponseDTO;
import com.gosterim360.service.SessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/sessions")
@RequiredArgsConstructor
@Tag(name = "Sessions", description = "Session management APIs")
public class SessionController {

    private final SessionService sessionService;

    @Operation(
            summary = "Get all sessions",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of sessions",
                            content = @Content(schema = @Schema(implementation = SessionResponseDTO.class)))
            }
    )
    @GetMapping
    public ResponseEntity<List<SessionResponseDTO>> getAllSessions() {
        List<SessionResponseDTO> sessions = sessionService.getAllSessions();
        return ResponseEntity.ok(sessions);
    }

    @Operation(
            summary = "Get session by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Session found",
                            content = @Content(schema = @Schema(implementation = SessionResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Session not found",
                            content = @Content)
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> getSessionById(
            @Parameter(description = "Session UUID", required = true)
            @PathVariable UUID id) {
        SessionResponseDTO session = sessionService.getSessionById(id);
        return ResponseEntity.ok(session);
    }

    @Operation(
            summary = "Update session by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Session updated",
                            content = @Content(schema = @Schema(implementation = SessionResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data",
                            content = @Content),
                    @ApiResponse(responseCode = "404", description = "Session not found",
                            content = @Content)
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<SessionResponseDTO> updateSession(
            @Parameter(description = "Session UUID", required = true)
            @PathVariable UUID id,
            @Parameter(description = "Updated session data", required = true)
            @Valid @RequestBody SessionRequestDTO dto) {
        SessionResponseDTO updated = sessionService.updateSession(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Delete session by id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Session deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Session not found",
                            content = @Content)
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSession(
            @Parameter(description = "Session UUID", required = true)
            @PathVariable UUID id) {
        sessionService.deleteSessionById(id);
        return ResponseEntity.noContent().build();
    }
}
