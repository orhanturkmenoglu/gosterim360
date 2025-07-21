package com.gosterim360.model;

import com.gosterim360.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
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

    // seans bazlı fiyat farklılaşabilir.
    private BigDecimal price;

    private LocalDateTime time;

}
