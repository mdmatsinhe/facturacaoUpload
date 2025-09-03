package mz.gov.at.fiscalfileupload.security;

import mz.gov.at.fiscalfileupload.entity.Utilizador;
import mz.gov.at.fiscalfileupload.repository.UtilizadorRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UtilizadorRepository repo;

    public CustomUserDetailsService(UtilizadorRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String nuit) throws UsernameNotFoundException {
        Utilizador user = repo.findByNuit(nuit);
        if (user == null) {
            throw new UsernameNotFoundException("Utilizador não encontrado com NUIT: " + nuit);
        }

        // garante que o prefixo ROLE_ está presente
        String role = "ROLE_" + user.getPerfil();

        return User.builder()
                .username(user.getNuit())
                .password(user.getPassword())
                .authorities(role)
                .build();
    }


}

