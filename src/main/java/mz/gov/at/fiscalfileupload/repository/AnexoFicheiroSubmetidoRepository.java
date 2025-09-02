package mz.gov.at.fiscalfileupload.repository;

import mz.gov.at.fiscalfileupload.entity.AnexoFicheiroSubmetido;
import mz.gov.at.fiscalfileupload.entity.FicheiroSubmetido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnexoFicheiroSubmetidoRepository extends JpaRepository<AnexoFicheiroSubmetido, Long> {
    List<AnexoFicheiroSubmetido> findByFicheiro(FicheiroSubmetido ficheiro);
}
