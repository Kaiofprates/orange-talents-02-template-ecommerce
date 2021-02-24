package br.com.orange.mercadolivre.Produtos;

import br.com.orange.mercadolivre.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/mercadolivre/produtos")
public class ProdutosController {

    @PersistenceContext
    private EntityManager manager;
    @Autowired
    private ValidaCategoria validaCategoria;
    @Autowired
    private ValidaListaDeCaracteristicas validaListaDeCaracteristicas;

    @InitBinder
    public void init(WebDataBinder binder){
        binder.addValidators(validaCategoria);
        binder.addValidators(validaListaDeCaracteristicas);
    }

    @PostMapping
    @Transactional
    public String cadastraProduto(@RequestBody   ProdutosRequest request){
        Produto produto = request.paraProduto(manager);
        if(produto != null){
            manager.persist(produto);
        }
       return  produto.toString();

    }
}
