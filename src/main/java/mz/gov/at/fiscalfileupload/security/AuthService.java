package mz.gov.at.fiscalfileupload.security;

import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.repository.UtilizadorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UtilizadorRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public AuthService(UtilizadorRepository repo, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public Utilizador login(String nuit, String senha) {
        Utilizador u = repo.findByNuit(nuit);
        if (u == null) {
            throw new RuntimeException("Utilizador não encontrado.");
        }
        if (!encoder.matches(senha, u.getPassword())) {
            throw new RuntimeException("Senha incorreta.");
        }
        return u;
    }
}

