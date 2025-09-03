package mz.gov.at.fiscalfileupload.dto;

import lombok.Data;
import mz.gov.at.fiscalfileupload.entity.Perfil;

@Data
public class CadastroRequest {
    private String nuit;
    private String nome;
    private String email;
    private String contacto;
    private String endereco;
    private Long areaFiscalId; // você pode usar o id da área fiscal
    private Perfil perfil;

    // getters e setters
}

