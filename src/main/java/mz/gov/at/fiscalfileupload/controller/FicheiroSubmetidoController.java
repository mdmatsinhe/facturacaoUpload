package mz.gov.at.fiscalfileupload.controller;

import mz.gov.at.fiscalfileupload.entity.FicheiroSubmetido;
import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.repository.UtilizadorRepository;
import mz.gov.at.fiscalfileupload.service.FicheiroSubmetidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ficheiros")
@CrossOrigin(origins = "http://localhost:4200")
public class FicheiroSubmetidoController {

    private final FicheiroSubmetidoService service;
    @Autowired
    private UtilizadorRepository utilizadorRepository;

    public FicheiroSubmetidoController(FicheiroSubmetidoService service) {
        this.service = service;
    }

    @PostMapping
    public FicheiroSubmetido submeter(@RequestBody FicheiroSubmetido ficheiro) {
        return service.salvar(ficheiro);
    }

    @GetMapping
    public List<FicheiroSubmetido> listar() {
        return service.listarTodos();
    }

    @GetMapping("/utilizador/{id}")
    public List<FicheiroSubmetido> porUtilizador(@PathVariable Long id) {
       Utilizador user=utilizadorRepository.getById(id);
        return service.buscarPorUtilizador(user);
    }
}

