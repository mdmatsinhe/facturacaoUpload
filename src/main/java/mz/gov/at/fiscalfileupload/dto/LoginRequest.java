package mz.gov.at.fiscalfileupload.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String nuit;
    private String senha;
}
