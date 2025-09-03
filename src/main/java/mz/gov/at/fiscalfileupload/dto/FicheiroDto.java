package mz.gov.at.fiscalfileupload.dto;

import lombok.Data;

@Data
public class FicheiroDto {

    private Long id;
    private String nomeOriginal;
    private String tipo;
    private int ano;
    private int mes;
    private String nuitDoUtilizador;

    public FicheiroDto(Long id, String nomeOriginal, String tipo, int ano, int mes, String nuitDoUtilizador) {
        this.id = id;
        this.nomeOriginal = nomeOriginal;
        this.tipo = tipo;
        this.ano = ano;
        this.mes = mes;
        this.nuitDoUtilizador = nuitDoUtilizador;
    }
}
