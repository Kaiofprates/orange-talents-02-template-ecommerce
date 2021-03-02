package br.com.orange.mercadolivre.Detalhe;

import br.com.orange.mercadolivre.Produtos.Produto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
@RequestMapping("/mercadolivre")
public class DetalheController {

    @PersistenceContext
    private EntityManager manager;

    @GetMapping("/produtos/{id}")
    public ResponseEntity<?> detalheProduto(@PathVariable("id") Long id){

        Produto produto = manager.find(Produto.class,id);
        DetalheResponse response = new DetalheResponse(produto);

        return ResponseEntity.ok(response);
    }

}
