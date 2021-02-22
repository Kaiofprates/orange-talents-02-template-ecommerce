package br.com.orange.mercadolivre.Categoria;

import br.com.orange.mercadolivre.handlers.exceptions.validation.UniqueValues.UniqueValue;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;

public class CategoriaRequest {
    @NotBlank(message = "O nome não pode estar vazio")
    @UniqueValue(domainClass = Categoria.class,fieldName = "nome")
    private String nome;
    private Long categoria;

    public CategoriaRequest(String nome, Long categoria) {
        this.nome = nome;
        this.categoria = categoria;
    }

    @Transactional
    public Categoria paraCategoria(EntityManager manager) {
        Categoria novaCategoria = new Categoria(this.nome);
        if(this.categoria != null){
            Categoria check = manager.find(Categoria.class,this.categoria);
            Assert.isTrue(check != null, "Categoria mãe não encontrada");
            novaCategoria.setCategoria(check);
            return novaCategoria;
        }else{
            return novaCategoria;
        }
    }
}
