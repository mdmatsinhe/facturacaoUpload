package mz.gov.at.fiscalfileupload.repository;

import mz.gov.at.fiscalfileupload.entity.LogSubmissao;
import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.entity.FicheiroSubmetido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogSubmissaoRepository extends JpaRepository<LogSubmissao, Long> {
    List<LogSubmissao> findByUtilizador(Utilizador utilizador);
    List<LogSubmissao> findByFicheiro(FicheiroSubmetido ficheiro);
}

