package br.com.orange.mercadolivre.Produtos;

import br.com.orange.mercadolivre.Usuario.Usuario;
import br.com.orange.mercadolivre.security.AuthUtils;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/mercadolivre")
public class ProdutosController {

    /*
    * Um controller coeso é uma classe que usa todos os seus atributos em todos os seus métodos
    */

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private UploaderFake uploaderFake;
    @Autowired
    private AuthUtils authUtils;

    @InitBinder(value = "ProdutoRequest")
    public void init(WebDataBinder binder){
    binder.addValidators(new ValidadorCaracteristica());
    }

    @PostMapping("/produtos")
    @Transactional
    public ResponseEntity<?> cadastroProdutos(@RequestBody @Valid ProdutoRequest request){

        Produto produto = request.toModel(manager,authUtils.checkUser());
        manager.persist(produto);
        return ResponseEntity.ok(produto);
    }

    @PostMapping("/produtos/{id}/imagens")
    @Transactional
    public ResponseEntity<?> recebeImagens(@PathVariable("id") Long id, @Valid ImagensRequest request){

        Usuario usuario = authUtils.checkUser();

        /**
         * 1 - enviar imagens para um lugar
         * 2 - buscas os links
         * 3 - associar link ao produto
         * 4 - carregar o produto
         * 5 - salvar a nova versão do produto
         */
       Set<String> links  =  uploaderFake.envia(request.getImages());

        /** Quanto mais cedo vc rodar a aplicação, mais cedo vc encontra os erros
         *  Menor é o escopo do código
         *  e mais facil é de resolver
         */Produto produto = manager.find(Produto.class,id);
        Assert.isTrue(produto != null, "Produto não encontrado");

        if(produto.getUsuario().getEmail() != usuario.getEmail()){
            throw  new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        produto.associaImagens(links);

        manager.merge(produto);
        return ResponseEntity.ok().build();

    }


}
