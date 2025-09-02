package mz.gov.at.fiscalfileupload.service;

import mz.gov.at.fiscalfileupload.entity.FicheiroSubmetido;
import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.repository.FicheiroSubmetidoRepository;
import mz.gov.at.fiscalfileupload.repository.UtilizadorRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;

@Service
public class FicheiroUploadService {

    @Value("${app.repositorio.base-path:/opt/Repo_eFactura}")
    private String basePath;

    private final FicheiroSubmetidoRepository ficheiroRepo;
    private final UtilizadorRepository utilizadorRepo;

    public FicheiroUploadService(FicheiroSubmetidoRepository ficheiroRepo, UtilizadorRepository utilizadorRepo) {
        this.ficheiroRepo = ficheiroRepo;
        this.utilizadorRepo = utilizadorRepo;
    }

    public String fazerUpload(MultipartFile file, String nuit, int ano, int mes, String tipo) throws IOException {
        // Validações básicas
        if (file.isEmpty()) throw new RuntimeException("Ficheiro vazio.");
        if (file.getSize() > 10 * 1024 * 1024) throw new RuntimeException("Tamanho superior a 10MB.");
        if (!file.getOriginalFilename().matches(".*\\.(xml|csv|xls|xlsx)$"))
            throw new RuntimeException("Formato não suportado.");

        // Caminho de destino: /Repo_eFactura/NUIT/ANO/MES
        Path pastaDestino = Paths.get(basePath, nuit, String.valueOf(ano), String.valueOf(mes));
        Files.createDirectories(pastaDestino);

        // Guardar ficheiro
        Path destinoFinal = pastaDestino.resolve(file.getOriginalFilename());
        Files.copy(file.getInputStream(), destinoFinal, StandardCopyOption.REPLACE_EXISTING);

        // Obter utilizador pelo NUIT
        Utilizador utilizadorOpt = utilizadorRepo.findByNuit(nuit);
        if (utilizadorOpt==null) throw new RuntimeException("Utilizador com NUIT " + nuit + " não encontrado.");

        // Registrar na BD
        FicheiroSubmetido registo = new FicheiroSubmetido();
        registo.setNomeOriginal(file.getOriginalFilename());
        registo.setCaminhoFicheiro(destinoFinal.toString());
        registo.setTipo(file.getContentType());
        registo.setAno(ano);
        registo.setMes(mes);
        registo.setTipo(tipo);
        registo.setDataSubmissao(LocalDateTime.now());
        registo.setUtilizador(utilizadorOpt);

        ficheiroRepo.save(registo);

        return "Ficheiro submetido com sucesso!";
    }
}
