package mz.gov.at.fiscalfileupload.controller;

import mz.gov.at.fiscalfileupload.entity.AreaFiscal;
import mz.gov.at.fiscalfileupload.service.AreaFiscalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas-fiscais")
public class AreaFiscalController {

    private final AreaFiscalService service;

    public AreaFiscalController(AreaFiscalService service) {
        this.service = service;
    }

    @PostMapping
    public AreaFiscal criar(@RequestBody AreaFiscal area) {
        return service.salvar(area);
    }

    @GetMapping
    public List<AreaFiscal> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public AreaFiscal buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
}
