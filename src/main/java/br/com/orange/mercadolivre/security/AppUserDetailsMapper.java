package br.com.orange.mercadolivre.security;

import br.com.orange.mercadolivre.Usuario.Usuario;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;


@Configuration
public class AppUserDetailsMapper implements UserDetailsMapper{

    @Override
    public UserDetails map(Object shouldBeASystemUser) {
        return new UsuarioLogado((Usuario)shouldBeASystemUser);
    }

}