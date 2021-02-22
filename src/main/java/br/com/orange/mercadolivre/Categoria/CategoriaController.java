package br.com.orange.mercadolivre.Categoria;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/mercadolivre")
public class CategoriaController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/categoria")
    @Transactional
    public ResponseEntity<?> novaCategoria(@Valid @RequestBody CategoriaRequest request){
        Categoria categoria = request.paraCategoria(manager);
        if(categoria != null){
            manager.persist(categoria);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }


}
