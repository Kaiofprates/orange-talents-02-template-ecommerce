package br.com.orange.mercadolivre.Produtos;

import br.com.orange.mercadolivre.Usuario.Usuario;
import br.com.orange.mercadolivre.Usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/mercadolivre")
public class ProdutosController {

    @PersistenceContext
    private EntityManager manager;


    @InitBinder
    public void init(WebDataBinder binder){
    binder.addValidators(new ValidadorCaracteristica());
    }


    @Autowired
    private UsuarioRepository repository;

    @PostMapping("/produtos")
    @Transactional
    public ResponseEntity<?> cadastroProdutos(@RequestBody @Valid ProdutoRequest request){

        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = repository.findByEmail(a.getName());
        Assert.isTrue(usuario != null,"Falha ao recuperar usuário");
        Produto produto = request.toModel(manager,usuario);
        manager.persist(produto);

        return ResponseEntity.ok(produto);
    }
}
