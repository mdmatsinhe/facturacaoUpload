package mz.gov.at.fiscalfileupload.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Utilizador {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nuit;
    private String nome;
    private String password;
    private String email;
    private String contacto;
    private String endereco;
    @ManyToOne
    private AreaFiscal areaFiscal;
    private LocalDateTime dataRegisto = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Perfil perfil;
// getters e setters
}
