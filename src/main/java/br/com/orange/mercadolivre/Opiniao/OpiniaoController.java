package br.com.orange.mercadolivre.Opiniao;

import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;
import br.com.orange.mercadolivre.security.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/mercadolivre")
public class OpiniaoController {

    @PersistenceContext
    private EntityManager manager;
    @Autowired
    private AuthUtils authUtils;

    @PostMapping("/produtos/{id}/opiniao")
    @Transactional
    public ResponseEntity<?> cadastroOpiniao(@PathVariable("id") Long id, @Valid @RequestBody OpiniaoRequest request){
        Produto produto = manager.find(Produto.class,id);
        Usuario usuario = authUtils.checkUser();
        Opiniao opiniao = request.toModel(produto,usuario);
        manager.persist(opiniao);

        return ResponseEntity.ok().build();
    }

}
