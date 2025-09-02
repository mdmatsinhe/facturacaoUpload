package mz.gov.at.fiscalfileupload.service;

import mz.gov.at.fiscalfileupload.entity.AreaFiscal;
import mz.gov.at.fiscalfileupload.repository.AreaFiscalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaFiscalService {

    private final AreaFiscalRepository repository;

    public AreaFiscalService(AreaFiscalRepository repository) {
        this.repository = repository;
    }

    public AreaFiscal salvar(AreaFiscal areaFiscal) {
        return repository.save(areaFiscal);
    }

    public List<AreaFiscal> listarTodos() {
        return repository.findAll();
    }

    public AreaFiscal buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }
}

