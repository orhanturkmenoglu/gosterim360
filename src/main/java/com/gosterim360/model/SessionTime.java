package com.gosterim360.model;

import com.gosterim360.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "session_times")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionTime extends BaseEntity<UUID> {

    @ManyToOne(fetch = FetchType.LAZY)
    private Session session;

    private LocalDateTime time;

}
