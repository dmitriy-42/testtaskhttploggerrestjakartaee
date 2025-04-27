package org.example.testtaskhttploggerrest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Entity
@Table(name = "additional_log_data")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalLogData {
    @Id
    private Long id;

    @NotNull
    private String ip;

    private String cookies;

    private String headers;

    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private LogMessage logMessage;

    @Override
    public String toString() {
        return String.format("%s(id=%s, ip=%s, headers=%s, cookies=%s)", this.getClass().getSimpleName(), id, ip, headers, cookies);
    }
}
