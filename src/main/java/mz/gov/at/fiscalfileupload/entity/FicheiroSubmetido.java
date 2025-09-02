package mz.gov.at.fiscalfileupload.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FicheiroSubmetido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeOriginal;
    private String caminhoFicheiro;
    private String tipo;
    private int ano;
    private int mes;
    private LocalDateTime dataSubmissao = LocalDateTime.now();

    @ManyToOne
    private Utilizador utilizador;
}
