package mz.gov.at.fiscalfileupload.controller;

import jakarta.transaction.Transactional;
import mz.gov.at.fiscalfileupload.entity.FicheiroSubmetido;
import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.repository.FicheiroSubmetidoRepository;
import mz.gov.at.fiscalfileupload.repository.UtilizadorRepository;
import mz.gov.at.fiscalfileupload.service.FicheiroUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/upload")
public class UploadFicheiroController {

    @Autowired
    private FicheiroUploadService fileStorageService;

    @Autowired
    private UtilizadorRepository utilizadorRepository;

    @Autowired
    private FicheiroSubmetidoRepository ficheiroRepository;

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
            Utilizador utilizador = utilizadorRepository.findByNuit(nuit);
            if (utilizador == null) return ResponseEntity.badRequest().body("Utilizador não encontrado para o NUIT: " + nuit);

            String caminho = fileStorageService.fazerUpload(file, nuit, ano, mes);

            FicheiroSubmetido registo = new FicheiroSubmetido();
            registo.setNomeOriginal(file.getOriginalFilename());
            registo.setCaminhoFicheiro(caminho);
            registo.setTipo(tipo);
            registo.setAno(ano);
            registo.setMes(mes);
            registo.setUtilizador(utilizador);

            ficheiroRepository.save(registo);

            return ResponseEntity.ok("Ficheiro submetido com sucesso.");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Erro ao processar o ficheiro: " + e.getMessage());
        }
    }
}
