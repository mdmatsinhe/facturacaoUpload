package mz.gov.at.fiscalfileupload.dto;

import lombok.Data;
import mz.gov.at.fiscalfileupload.entity.Perfil;
import mz.gov.at.fiscalfileupload.entity.Utilizador;

@Data
public class LoginResponse {
    private String token;
    private String nuit;
    private String nome;
    private String email;
    private Perfil perfil;

    public LoginResponse(String token, Utilizador u) {
        this.token = token;
        this.nuit = u.getNuit();
        this.nome = u.getNome();
        this.email = u.getEmail();
        this.perfil = u.getPerfil();
    }
    // getters
}
