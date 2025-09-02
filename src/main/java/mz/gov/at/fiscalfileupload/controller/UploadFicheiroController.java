package mz.gov.at.fiscalfileupload.controller;

import jakarta.transaction.Transactional;
import mz.gov.at.fiscalfileupload.entity.FicheiroSubmetido;
import mz.gov.at.fiscalfileupload.entity.LogSubmissao;
import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.repository.FicheiroSubmetidoRepository;
import mz.gov.at.fiscalfileupload.repository.LogSubmissaoRepository;
import mz.gov.at.fiscalfileupload.repository.UtilizadorRepository;
import mz.gov.at.fiscalfileupload.service.FicheiroUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
@CrossOrigin(origins = "http://localhost:4200")
public class UploadFicheiroController {

    @Autowired
    private FicheiroUploadService fileStorageService;

    @PostMapping
    @Transactional
    public ResponseEntity<?> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("nuit") String nuit,
            @RequestParam("ano") int ano,
            @RequestParam("mes") int mes,
            @RequestParam("tipo") String tipo
    ) {
        try {
            String ficheiro = fileStorageService.fazerUpload(file, nuit, ano, mes, tipo);
            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Ficheiro submetido com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
