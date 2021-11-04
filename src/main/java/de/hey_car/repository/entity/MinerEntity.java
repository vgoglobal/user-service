package de.hey_car.repository.entity;

import de.hey_car.dto.MinerResourceType;
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
@Table(name = "miner")
public class MinerEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type", nullable = false)
    private MinerResourceType resourceType;
    @Column(name = "resource_name", nullable = false)
    private String resourceName;
    @Column(name = "resource_number", nullable = false)
    private String resourceNumber;
    @Column(name = "resource_code", nullable = false)
    private String resourceCode;
    @Column(name = "resource_address", nullable = false)
    private String resourceAddress;
    @Column(name = "resource_currency")
    private String resourceCurrency;
    @Column(name = "userId")
    private String userId;
    @Column(name = "created_date", nullable = false)
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