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
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "country", nullable = false)
    private String country;
    @Column(name = "address", nullable = false)
    private String address;
    @Column(name = "confirmation_code")
    private String confirmationCode;
    @Column(name = "confirmed")
    private Boolean confirmed;
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