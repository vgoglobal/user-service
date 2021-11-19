package de.exchange.entity;

import de.exchange.dto.ResourceType;
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
@Table(name = "wallet_details")
public class WalletDetailsEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    @Column(name = "currency", nullable = false)
    private String currency;
    @Column(name = "amount", nullable = false)
    private Double amount;
    @Column(name = "hold_amount", nullable = false)
    private Double holdAmount;
    @Column(name = "resource_type", nullable = false)
    private ResourceType resourceType;
    @Column(name = "create_date")
    private Instant createdDate;
    @Column(name = "update_date")
    private Instant updateDate;
    @Column(name = "wallet_id")
    private String walletId;

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