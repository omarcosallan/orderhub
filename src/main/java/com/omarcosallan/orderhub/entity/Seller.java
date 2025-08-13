package com.omarcosallan.orderhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

@Entity
@Table(name = "tb_sellers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String name;

    @CPF(message = "CPF deve ter formato válido")
    @Column(nullable = false, unique = true)
    private String cpf;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate hiringDate;

    private LocalDate birthDate;

    private Double commissionRate;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(nullable = false)
    private String phone;

    @Embedded
    private Address address;

    @OneToOne
    @JoinColumn(name = "owner_id", nullable = false, unique = true)
    private User owner;
}
