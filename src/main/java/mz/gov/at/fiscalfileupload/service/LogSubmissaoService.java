package mz.gov.at.fiscalfileupload.service;

import mz.gov.at.fiscalfileupload.entity.LogSubmissao;
import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.repository.LogSubmissaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogSubmissaoService {

    private final LogSubmissaoRepository repository;

    public LogSubmissaoService(LogSubmissaoRepository repository) {
        this.repository = repository;
    }

    public LogSubmissao salvar(LogSubmissao log) {
        return repository.save(log);
    }

    public List<LogSubmissao> listarTodos() {
        return repository.findAll();
    }

    public List<LogSubmissao> buscarPorUtilizador(Utilizador utilizador) {
        return repository.findByUtilizador(utilizador);
    }
}
