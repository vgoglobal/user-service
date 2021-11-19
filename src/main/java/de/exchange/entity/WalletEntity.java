package de.exchange.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "wallet")
public class WalletEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2") // TODO remove this and generate logic need to be added
    private String id;
    @Column(name = "mobile", nullable = false)
    private Long mobile;
    @Column(name = "otp", nullable = false)
    private String otp;
    @Column(name = "otp_confirmed", nullable = false)
    private Boolean otpConfirmed;
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;
    @Column(name = "update_date")
    private Instant updateDate;
    @Column(name = "user_id")
    private String userId;
    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "wallet_id")
    private List<WalletDetailsEntity> walletDetailsEntity;
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