package mz.gov.at.fiscalfileupload.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogSubmissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensagem;
    private LocalDateTime dataLog = LocalDateTime.now();

    @ManyToOne
    private Utilizador utilizador;

    @ManyToOne
    private FicheiroSubmetido ficheiro;
// getters e setters
}
