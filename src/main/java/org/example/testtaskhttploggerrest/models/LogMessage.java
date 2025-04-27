package org.example.testtaskhttploggerrest.models;

import jakarta.json.bind.annotation.JsonbTypeDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlAnyElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.sql.rowset.serial.SerialClob;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Map;

@Data
@Entity
@ToString
@XmlType
@Table(name = "logger_messages", indexes = {@Index(columnList = "logger_name"), @Index(columnList = "create_time"), @Index(columnList = "level")})
@NoArgsConstructor
public class LogMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "logger_name")
    private String loggerName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LevelMessage level;

    @NotNull
    private String message;

    private String url;

    @Column(name = "stack_trace")
    private String stackTrace;

    @Lob
    private String params;

    @Lob
    @Column(name = "add_info")
    private String addInfo;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    @OneToOne(mappedBy = "logMessage", cascade = CascadeType.ALL)
    @JoinColumn
    private AdditionalLogData logData;
}
