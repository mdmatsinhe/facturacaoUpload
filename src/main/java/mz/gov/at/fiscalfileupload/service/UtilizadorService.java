package mz.gov.at.fiscalfileupload.service;

import mz.gov.at.fiscalfileupload.dto.CadastroRequest;
import mz.gov.at.fiscalfileupload.dto.UtilizadorComSenhaDTO;
import mz.gov.at.fiscalfileupload.entity.Perfil;
import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.repository.AreaFiscalRepository;
import mz.gov.at.fiscalfileupload.repository.UtilizadorRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UtilizadorService {

    private final UtilizadorRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final AreaFiscalRepository areaFiscalRepository;

    public UtilizadorService(UtilizadorRepository repository, PasswordEncoder passwordEncoder, AreaFiscalRepository areaFiscalRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.areaFiscalRepository = areaFiscalRepository;
    }

    public Map<String, String> criarUtilizador(CadastroRequest req) {
        String senhaHash = passwordEncoder.encode(gerarSenha(req.getNuit()));

        Utilizador u = new Utilizador();
        u.setNuit(req.getNuit());
        u.setNome(req.getNome());
        u.setEmail(req.getEmail());
        u.setContacto(req.getContacto());
        u.setEndereco(req.getEndereco());
        u.setPerfil(req.getPerfil());
        u.setPassword(senhaHash);

        if (req.getAreaFiscalId() != null) {
            u.setAreaFiscal(areaFiscalRepository.findById(req.getAreaFiscalId())
                    .orElseThrow(() -> new RuntimeException("Área fiscal não encontrada")));
        } else {
            u.setAreaFiscal(null); // explicitamente null
        }

        repository.save(u);

        Map<String, String> resp = new HashMap<>();
        resp.put("nuit", u.getNuit());
        resp.put("perfil", u.getPerfil().name());
        resp.put("senhaGerada", gerarSenha(req.getNuit()));

        return resp;
    }

    public List<Utilizador> listarTodos() {
        return repository.findAll();
    }

    public Utilizador buscarPorNuit(String nuit) {
        return repository.findByNuit(nuit);
    }

    public String gerarSenha(String nuit) {
        String parteNuit = nuit.length() >= 3 ? nuit.substring(0, 3) : nuit;

        int anoAtual = Year.now().getValue();

        String senha = parteNuit + "@" + anoAtual;

        return senha;
    }

}
