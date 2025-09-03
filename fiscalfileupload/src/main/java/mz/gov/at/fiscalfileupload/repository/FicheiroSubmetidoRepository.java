package mz.gov.at.fiscalfileupload.repository;

import mz.gov.at.fiscalfileupload.entity.FicheiroSubmetido;
import mz.gov.at.fiscalfileupload.entity.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FicheiroSubmetidoRepository extends JpaRepository<FicheiroSubmetido, Long> {
    List<FicheiroSubmetido> findByUtilizador(Utilizador utilizador);
    List<FicheiroSubmetido> findByAnoAndMes(int ano, int mes);
}
