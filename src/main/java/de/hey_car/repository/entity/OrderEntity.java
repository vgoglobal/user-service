package de.hey_car.repository.entity;

import de.hey_car.dto.TransferStatusType;
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
@Table(name = "exchange_order")
public class OrderEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    @Column(name = "amount", nullable = false)
    private Double amount;
    @Column(name = "recipient_amount", nullable = false)
    private Double recipientAmount;
    @Column(name = "transfer_fee", nullable = false)
    private Double transferFee;
    @Column(name = "recipient_id", nullable = false)
    private String recipientId;
    @Column(name = "user_id", nullable = false)
    private String userId;
    @Column(name = "from_currency", nullable = false)
    private String fromCurrency;
    @Column(name = "to_currency", nullable = false)
    private String toCurrency;
    @Column(name = "ref_id", nullable = false)
    private String refId;
    @Column(name = "status", nullable = false)
    private TransferStatusType status;
    @Column(name = "transfer_ref", nullable = true)
    private String transferRef;
    @Column(name = "picked_by", nullable = true)
    private String pickedBy;
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