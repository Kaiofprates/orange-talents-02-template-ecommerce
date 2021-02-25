package br.com.orange.mercadolivre.security;

import br.com.orange.mercadolivre.Usuario.Usuario;
import br.com.orange.mercadolivre.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 *  Retona o usuário logado no sistema conforme o Bearer token.
 */
@Component
public class AuthUtils {

    @Autowired
    private UsuarioRepository repository;

    public Usuario checkUser() {
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = repository.findByEmail(a.getName());
        if(usuario == null){
            throw  new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        return usuario;
    }
}
