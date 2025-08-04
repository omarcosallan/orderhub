package com.omarcosallan.orderhub.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CNPJ;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_costumers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Costumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String companyName;

    @CNPJ(message = "CNPJ deve ter formato válido")
    @Column(nullable = false, unique = true)
    private String cnpj;

    @Email(message = "Email deve ter formato válido")
    @NotBlank(message = "Email é obrigatório")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(nullable = false)
    private String phone;

    @Embedded
    private Address address;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
