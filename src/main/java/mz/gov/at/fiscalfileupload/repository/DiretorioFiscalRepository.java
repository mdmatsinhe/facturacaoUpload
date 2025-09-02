package mz.gov.at.fiscalfileupload.repository;

import mz.gov.at.fiscalfileupload.entity.DiretorioFiscal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiretorioFiscalRepository extends JpaRepository<DiretorioFiscal, Long> {
    List<DiretorioFiscal> findByNuit(String nuit);
    DiretorioFiscal findByNuitAndAnoAndMes(String nuit, int ano, int mes);
}

