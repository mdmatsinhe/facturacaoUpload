package mz.gov.at.fiscalfileupload.repository;

import mz.gov.at.fiscalfileupload.entity.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilizadorRepository extends JpaRepository<Utilizador, Long> {
    Utilizador findByNuit(String nuit);
    Optional<Utilizador> findByEmail(String email);
}

