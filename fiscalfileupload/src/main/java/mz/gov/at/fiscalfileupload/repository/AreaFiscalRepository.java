package mz.gov.at.fiscalfileupload.repository;

import mz.gov.at.fiscalfileupload.entity.AreaFiscal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaFiscalRepository extends JpaRepository<AreaFiscal, Long> {
    AreaFiscal findByCodigo(String codigo);
}
