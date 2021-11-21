package de.exchange.entity;

import de.exchange.dto.TransferStatusType;
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
@Table(name = "order_details")
public class OrderDetailsEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    @Column(name = "order_id", nullable = false)
    private String orderId;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "source_miner_id")
    private MinerEntity sourceMinerId;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "target_miner_id")
    private MinerEntity targetMinerId;
    @Column(name = "create_date")
    private Instant createdDate;
    @Column(name = "update_date")
    private Instant updateDate;
    @Column(name = "pick_date")
    private Instant pickDate;
    @Column(name = "delivery_date")
    private Instant deliveryDate;
    @Column(name = "cancelled_date")
    private Instant cancelledDate;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransferStatusType status;
    @Column(name = "ref_file")
    private String refFile;

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