package mz.gov.at.fiscalfileupload.controller;

import mz.gov.at.fiscalfileupload.entity.FicheiroSubmetido;
import mz.gov.at.fiscalfileupload.entity.LogSubmissao;
import mz.gov.at.fiscalfileupload.entity.Perfil;
import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.repository.FicheiroSubmetidoRepository;
import mz.gov.at.fiscalfileupload.repository.LogSubmissaoRepository;
import mz.gov.at.fiscalfileupload.repository.UtilizadorRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/download")
@CrossOrigin(origins = "http://localhost:4200")
public class DownloadFicheiroController {

    private final FicheiroSubmetidoRepository ficheiroRepository;
    private final UtilizadorRepository utilizadorRepository;
    private final LogSubmissaoRepository logRepository;

    public DownloadFicheiroController(FicheiroSubmetidoRepository ficheiroRepository,
                                      UtilizadorRepository utilizadorRepository,
                                      LogSubmissaoRepository logRepository) {
        this.ficheiroRepository = ficheiroRepository;
        this.utilizadorRepository = utilizadorRepository;
        this.logRepository = logRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFicheiro(@PathVariable Long id,
                                                     @RequestParam("nuit") String nuit) throws IOException {

        // Buscar ficheiro
        FicheiroSubmetido ficheiro = ficheiroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ficheiro não encontrado"));

        // Buscar utilizador
        Utilizador utilizador = utilizadorRepository.findByNuit(nuit);
        if (utilizador == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Regras de acesso
        if (utilizador.getPerfil() == Perfil.CONTRIBUINTE &&
                !ficheiro.getUtilizador().getId().equals(utilizador.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Verificar se o ficheiro existe
        Path path = Paths.get(ficheiro.getCaminhoFicheiro());
        if (!Files.exists(path)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(path.toUri());

        // Registar log de download
        LogSubmissao log = new LogSubmissao();
        log.setMensagem("Download do ficheiro: " + ficheiro.getNomeOriginal());
        log.setUtilizador(utilizador);
        log.setFicheiro(ficheiro);
        logRepository.save(log);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + ficheiro.getNomeOriginal() + "\"")
                .body(resource);
    }
}

