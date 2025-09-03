package mz.gov.at.fiscalfileupload.controller;

import mz.gov.at.fiscalfileupload.dto.FicheiroDto;
import mz.gov.at.fiscalfileupload.entity.FicheiroSubmetido;
import mz.gov.at.fiscalfileupload.entity.Perfil;
import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.repository.FicheiroSubmetidoRepository;
import mz.gov.at.fiscalfileupload.repository.UtilizadorRepository;
import mz.gov.at.fiscalfileupload.security.JwtUtil;
import mz.gov.at.fiscalfileupload.service.FicheiroSubmetidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ficheiros")
@CrossOrigin(origins = "http://localhost:4200")
public class FicheiroSubmetidoController {

    private final FicheiroSubmetidoService service;
    @Autowired
    private UtilizadorRepository utilizadorRepository;

    private final FicheiroSubmetidoRepository ficheiroRepo;
    private final JwtUtil jwtUtil;

    public FicheiroSubmetidoController(FicheiroSubmetidoService service, FicheiroSubmetidoRepository ficheiroRepo, JwtUtil jwtUtil) {
        this.service = service;
        this.ficheiroRepo = ficheiroRepo;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public FicheiroSubmetido submeter(@RequestBody FicheiroSubmetido ficheiro) {
        return service.salvar(ficheiro);
    }

    @GetMapping
    public List<FicheiroSubmetido> listar() {
        return service.listarTodos();
    }

    @GetMapping("/utilizador/{id}")
    public List<FicheiroSubmetido> porUtilizador(@PathVariable Long id) {
       Utilizador user=utilizadorRepository.getById(id);
        return service.buscarPorUtilizador(user);
    }

    @GetMapping("/utilizador")
    public List<FicheiroDto> listarPorUtilizador(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String nuit = jwtUtil.extractNuit(token);
        Utilizador utilizadorLogado = utilizadorRepository.findByNuit(nuit);
        if (utilizadorLogado == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilizador não encontrado");
        }

        List<FicheiroSubmetido> ficheiros;

        if (utilizadorLogado.getPerfil() == Perfil.FUNCIONARIO) {
            ficheiros = ficheiroRepo.findAll();
        } else {
            ficheiros = ficheiroRepo.findByUtilizadorId(utilizadorLogado.getId());
        }

        return ficheiros.stream()
                .map(f -> new FicheiroDto(
                        f.getId(),
                        f.getNomeOriginal(),
                        f.getTipo(),
                        f.getAno(),
                        f.getMes(),
                        f.getUtilizador().getNuit()
                ))
                .collect(Collectors.toList());
    }
}

