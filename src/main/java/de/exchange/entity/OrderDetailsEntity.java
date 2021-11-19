package de.exchange.entity;

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
    @JoinColumn(name = "onshore_miner_id")
    //@Column(name = "onshore_miner_id", nullable = false)
    private MinerEntity onshoreMinerId;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "offshore_miner_id")
  //  @Column(name = "offshore_miner_id", nullable = false)
    private MinerEntity offshoreMinerId;
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