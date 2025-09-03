package mz.gov.at.fiscalfileupload.service;

import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.repository.UtilizadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilizadorService {

    private final UtilizadorRepository repository;

    public UtilizadorService(UtilizadorRepository repository) {
        this.repository = repository;
    }

    public Utilizador salvar(Utilizador utilizador) {
        return repository.save(utilizador);
    }

    public List<Utilizador> listarTodos() {
        return repository.findAll();
    }

    public Utilizador buscarPorNuit(String nuit) {
        return repository.findByNuit(nuit);
    }
}
