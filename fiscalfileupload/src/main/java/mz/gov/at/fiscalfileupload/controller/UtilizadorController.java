package mz.gov.at.fiscalfileupload.controller;

import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.service.UtilizadorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilizadores")
public class UtilizadorController {

    private final UtilizadorService service;

    public UtilizadorController(UtilizadorService service) {
        this.service = service;
    }

    @PostMapping
    public Utilizador criar(@RequestBody Utilizador utilizador) {
        return service.salvar(utilizador);
    }

    @GetMapping
    public List<Utilizador> listar() {
        return service.listarTodos();
    }

    @GetMapping("/nuit/{nuit}")
    public Utilizador buscarPorNuit(@PathVariable String nuit) {
        return service.buscarPorNuit(nuit);
    }
}
