package com.gosterim360.controller;

import com.gosterim360.common.BaseResponse;
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
    public ResponseEntity<BaseResponse<List<SessionResponseDTO>>> getAllSessions() {
        List<SessionResponseDTO> sessions = sessionService.getAllSessions();
        return ResponseEntity.ok(
                BaseResponse.success(sessions, "Sessions retrieved successfully", HttpStatus.OK.value())
        );
    }

    @Operation(
            summary = "Get session by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Session found",
                            content = @Content(schema = @Schema(implementation = SessionResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Session not found")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<SessionResponseDTO>> getSessionById(
            @Parameter(description = "Session UUID", required = true)
            @PathVariable UUID id) {
        SessionResponseDTO session = sessionService.getSessionById(id);
        return ResponseEntity.ok(
                BaseResponse.success(session, "Session retrieved successfully", HttpStatus.OK.value())
        );
    }

    @Operation(
            summary = "Update session by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Session updated",
                            content = @Content(schema = @Schema(implementation = SessionResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid request data"),
                    @ApiResponse(responseCode = "404", description = "Session not found")
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<SessionResponseDTO>> updateSession(
            @Parameter(description = "Session UUID", required = true)
            @PathVariable UUID id,
            @Valid @RequestBody SessionRequestDTO dto) {
        SessionResponseDTO updated = sessionService.updateSession(id, dto);
        return ResponseEntity.ok(
                BaseResponse.success(updated, "Session updated successfully", HttpStatus.OK.value())
        );
    }

    @Operation(
            summary = "Delete session by id",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Session deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Session not found")
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteSession(
            @Parameter(description = "Session UUID", required = true)
            @PathVariable UUID id) {
        sessionService.deleteSessionById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(BaseResponse.success(null, "Session deleted successfully", HttpStatus.NO_CONTENT.value()));
    }

    @GetMapping("/films/{filmId}/sessions")
    public ResponseEntity<BaseResponse<List<SessionResponseDTO>>> getSessionsByFilmId(
            @PathVariable UUID filmId) {
        List<SessionResponseDTO> sessions = sessionService.getSessionsByFilmId(filmId);
        return ResponseEntity.ok(BaseResponse.success(sessions, "Sessions for film retrieved", HttpStatus.OK.value()));
    }
}
