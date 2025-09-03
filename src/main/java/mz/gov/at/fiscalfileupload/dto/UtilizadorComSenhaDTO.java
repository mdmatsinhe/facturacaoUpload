package mz.gov.at.fiscalfileupload.dto;

import mz.gov.at.fiscalfileupload.entity.Utilizador;

public class UtilizadorComSenhaDTO {
    private Utilizador utilizador;
    private String senhaGerada;

    public UtilizadorComSenhaDTO(Utilizador utilizador, String senhaGerada) {
        this.utilizador = utilizador;
        this.senhaGerada = senhaGerada;
    }

    public Utilizador getUtilizador() { return utilizador; }
    public String getSenhaGerada() { return senhaGerada; }
}

