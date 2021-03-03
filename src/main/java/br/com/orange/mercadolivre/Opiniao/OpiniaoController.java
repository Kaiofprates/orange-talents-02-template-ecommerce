package br.com.orange.mercadolivre.Opiniao;

import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;
import br.com.orange.mercadolivre.security.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    //1
    @Autowired
    private AuthUtils authUtils;

    @PostMapping("/produtos/{id}/opiniao")
    @Transactional
    //1
    public String cadastroOpiniao(@PathVariable("id") Long id, @Valid @RequestBody OpiniaoRequest request){
        //1
        Produto produto = manager.find(Produto.class,id);
        //1
        Usuario usuario = authUtils.checkUser();
        //1
        Opiniao opiniao = request.toModel(produto,usuario);
        manager.persist(opiniao);

        return opiniao.toString();
    }

}
