package br.com.orange.mercadolivre.security;

import br.com.orange.mercadolivre.Usuario.Usuario;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioLogado implements UserDetails {
    public UsuarioLogado(Usuario shouldBeASystemUser) {
    }
}
