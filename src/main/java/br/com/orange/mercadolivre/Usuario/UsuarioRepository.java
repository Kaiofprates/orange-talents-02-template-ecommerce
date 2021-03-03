package br.com.orange.mercadolivre.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *  Uso o repository para parte de minha lógica de altenticação
 *  vide a classe AuthUtils no pacote security
 */


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Usuario findByEmail(String email);
}
