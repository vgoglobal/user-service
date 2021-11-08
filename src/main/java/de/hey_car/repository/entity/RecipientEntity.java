package de.hey_car.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "recipient")
public class RecipientEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "number", nullable = false)
    private String number;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "currency", nullable = false)
    private String currency;
    @Column(name = "institution", nullable = false)
    private String institution;
    @Column(name = "institution_type", nullable = false)
    private String institutionType;
    @Column(name = "userId", nullable = false)
    private String userId;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "create_date")
    private Instant createdDate;
    @Column(name = "update_date")
    private Instant updateDate;

    @PrePersist
    public void prePersist() {
        Instant now = Instant.now();
        this.createdDate = now;
        this.updateDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updateDate = Instant.now();
    }
}
