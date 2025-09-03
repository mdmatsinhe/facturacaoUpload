package mz.gov.at.fiscalfileupload.controller;

import mz.gov.at.fiscalfileupload.dto.CadastroRequest;
import mz.gov.at.fiscalfileupload.dto.LoginRequest;
import mz.gov.at.fiscalfileupload.dto.LoginResponse;
import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.security.AuthService;
import mz.gov.at.fiscalfileupload.security.JwtUtil;
import mz.gov.at.fiscalfileupload.service.UtilizadorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

    @RestController
    @RequestMapping("/api/auth")
    @CrossOrigin(origins = "http://localhost:4200")
    public class AuthController {

        private final UtilizadorService utilizadorService;
        private final JwtUtil jwtUtil;
        private final AuthService authService;

        public AuthController(UtilizadorService utilizadorService, JwtUtil jwtUtil, AuthService authService) {
            this.utilizadorService = utilizadorService;
            this.jwtUtil = jwtUtil;
            this.authService = authService;
        }

        @PostMapping("/register")
        public ResponseEntity<?> register(@RequestBody CadastroRequest req) {
            try {
                Map<String, String> resp = utilizadorService.criarUtilizador(req);
                return ResponseEntity.ok(resp);
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }


        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody LoginRequest req) {
            try {
                Utilizador u = authService.login(req.getNuit(), req.getSenha());
                String token = jwtUtil.generateToken(u);
                return ResponseEntity.ok(new LoginResponse(token, u));
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            }
        }
    }


