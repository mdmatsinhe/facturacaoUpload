package mz.gov.at.fiscalfileupload.service;

import mz.gov.at.fiscalfileupload.entity.FicheiroSubmetido;
import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.repository.FicheiroSubmetidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FicheiroSubmetidoService {

    private final FicheiroSubmetidoRepository repository;

    public FicheiroSubmetidoService(FicheiroSubmetidoRepository repository) {
        this.repository = repository;
    }

    public FicheiroSubmetido salvar(FicheiroSubmetido ficheiro) {
        return repository.save(ficheiro);
    }

    public List<FicheiroSubmetido> listarTodos() {
        return repository.findAll();
    }

    public List<FicheiroSubmetido> buscarPorUtilizador(Utilizador utilizador) {
        return repository.findByUtilizador(utilizador);
    }
}
