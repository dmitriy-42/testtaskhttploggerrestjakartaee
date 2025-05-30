package org.example.testtaskhttploggerrest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlType;
import lombok.*;

@Data
@Entity
@ToString
@XmlType
@Table(name = "additional_settings")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalSetting {
    @Id
    @Column(name = "logger_name")
    private String loggerName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LevelMessage level;
}
