package de.hey_car.entity;

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
@Table(name = "wallet_transactions")
public class WalletTransactionsEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    @Column(name = "amount", nullable = false)
    private String amount;
    @Column(name = "currency", nullable = false)
    private Double currency;
    @Column(name = "trx_type", nullable = false)
    private Double trxType;
    @Column(name = "closing_balance", nullable = false)
    private Double closingBalance;
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