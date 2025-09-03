package mz.gov.at.fiscalfileupload.controller;

import mz.gov.at.fiscalfileupload.entity.FicheiroSubmetido;
import mz.gov.at.fiscalfileupload.entity.LogSubmissao;
import mz.gov.at.fiscalfileupload.entity.Perfil;
import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.repository.FicheiroSubmetidoRepository;
import mz.gov.at.fiscalfileupload.repository.LogSubmissaoRepository;
import mz.gov.at.fiscalfileupload.repository.UtilizadorRepository;
import mz.gov.at.fiscalfileupload.security.JwtUtil;
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
    private final JwtUtil jwtUtil;

    public DownloadFicheiroController(FicheiroSubmetidoRepository ficheiroRepository,
                                      UtilizadorRepository utilizadorRepository,
                                      LogSubmissaoRepository logRepository, JwtUtil jwtUtil) {
        this.ficheiroRepository = ficheiroRepository;
        this.utilizadorRepository = utilizadorRepository;
        this.logRepository = logRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFicheiro(@PathVariable Long id,
                                                     @RequestHeader("Authorization") String authHeader) throws IOException {

        System.out.println("=== DOWNLOAD DEBUG ===");

        // 1. Verifica se o header está presente
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Header Authorization ausente ou inválido: " + authHeader);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.replace("Bearer ", "");
        System.out.println("Token recebido: " + token);

        // 2. Extrai NUIT do token
        String nuitDoToken;
        try {
            nuitDoToken = jwtUtil.extractNuit(token);
            System.out.println("NUIT extraído do token: " + nuitDoToken);
        } catch (Exception e) {
            System.out.println("Erro ao extrair NUIT do token: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 3. Busca utilizador
        Utilizador utilizador = utilizadorRepository.findByNuit(nuitDoToken);
        if (utilizador == null) {
            System.out.println("Utilizador não encontrado para NUIT: " + nuitDoToken);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        System.out.println("Perfil do utilizador: " + utilizador.getPerfil());

        // 4. Busca ficheiro
        FicheiroSubmetido ficheiro = ficheiroRepository.findById(id)
                .orElse(null);

        if (ficheiro == null) {
            System.out.println("Ficheiro não encontrado para id: " + id);
            return ResponseEntity.notFound().build();
        }
        System.out.println("Ficheiro solicitado: " + ficheiro.getNomeOriginal() + ", pertence ao NUIT: " + ficheiro.getUtilizador().getNuit());

        // 5. Valida acesso
        if (utilizador.getPerfil() == Perfil.CONTRIBUINTE &&
                !ficheiro.getUtilizador().getId().equals(utilizador.getId())) {
            System.out.println("Acesso negado: contribuinte tentando baixar ficheiro de outro utilizador");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Path path = Paths.get(ficheiro.getCaminhoFicheiro());
        if (!Files.exists(path)) return ResponseEntity.notFound().build();
        Resource resource = new UrlResource(path.toUri());
        System.out.println("Download autorizado para " + ficheiro.getNomeOriginal());

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

