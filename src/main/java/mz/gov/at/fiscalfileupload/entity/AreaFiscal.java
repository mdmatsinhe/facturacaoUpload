package mz.gov.at.fiscalfileupload.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AreaFiscal {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String descricao;
    private String descricaoCurta;
    private String classificacao;
    private Integer provincia;
    private String unidadeOrcamental;
    private String dafMae;
// getters e setters
}
