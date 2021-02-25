package br.com.orange.mercadolivre.Produtos;

import br.com.orange.mercadolivre.Categoria.Categoria;
import br.com.orange.mercadolivre.Usuario.Usuario;
import br.com.orange.mercadolivre.handlers.exceptions.validation.ValidID.ExistId;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProdutoRequest {

    @NotBlank
    private String nome;
    @NotNull
    @PositiveOrZero
    private int quantidade;
    @Positive
    @NotNull
    private BigDecimal valor;
    @NotBlank
    @Length(max = 1000)
    private String descricao;
    @NotNull
    @ExistId(domainClass = Categoria.class,fieldName = "id")
    private Long idCategoria;
    @Size(min = 3)
    @Valid
    private List<CaracteristicaRequest> caracteristicas = new ArrayList<>();


    public ProdutoRequest(@NotBlank String nome, @NotNull @Positive int quantidade,
                          @Positive @NotNull BigDecimal valor,
                          @NotBlank @Length(max = 1000) String descricao,
                          @NotNull Long idCategoria,List<CaracteristicaRequest> caracteristicas) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
        this.descricao = descricao;
        this.idCategoria = idCategoria;
        this.caracteristicas.addAll(caracteristicas);
    }

    public List<CaracteristicaRequest> getCaracteristicas() {
        return caracteristicas;
    }

    @Override
    public String toString() {
        return "ProdutoRequest{" +
                "nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", valor=" + valor +
                ", descricao='" + descricao + '\'' +
                ", idCategoria=" + idCategoria +
                ", caracteristicas=" + caracteristicas.toString() +
                '}';
    }

    public Produto toModel(EntityManager manager, Usuario usuario) {
        Categoria categoria = manager.find(Categoria.class,idCategoria);
        return new Produto(nome, quantidade, descricao, valor, categoria, usuario,caracteristicas);
    }

    public Set<String> caracteristicasIguals() {
        HashSet<String> nomesIguais  = new HashSet<>();
        HashSet<String> resultados  = new HashSet<>();

        for( CaracteristicaRequest caracteristica : caracteristicas){
            String nome = caracteristica.getNome();
            if(!nomesIguais.add(nome)){
                  resultados.add(nome);
              }
        }
        return resultados;
    }
}
