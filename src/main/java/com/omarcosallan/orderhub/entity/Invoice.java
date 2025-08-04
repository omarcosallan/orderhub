package com.omarcosallan.orderhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_invoices")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Sale sale;

    @NotBlank
    private String nfeRef;

    @NotBlank
    private String xmlUrl;

    @NotBlank
    private String danfeUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusNfe status;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime issuedAt;

    private LocalDateTime paidAt;

    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = StatusNfe.PENDING;
        }
    }
}
