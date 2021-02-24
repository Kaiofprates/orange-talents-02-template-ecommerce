package br.com.orange.mercadolivre.Produtos;

import br.com.orange.mercadolivre.Categoria.Categoria;
import org.hibernate.validator.constraints.Length;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class ProdutosRequest {

    @NotBlank
    private String nome;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @PositiveOrZero
    private Long quantidade;

    @NotBlank
    @Length(max = 1000)
    private String descricao;

    @NotNull
    private Long categoria;

    private LocalDateTime registro = LocalDateTime.now(ZoneId.systemDefault());

    @Size(min = 3)
    private List<CaracteristicasRequest> caracteristicas;

    public Long getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return "ProdutosRequest{" +
                "nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", categoria=" + categoria +
                ", registro=" + registro +
                ", caracteristicas=" + caracteristicas.toString() +
                '}';
    }

    @Transactional
    public Produto paraProduto(EntityManager manager) {
        Categoria c = manager.find(Categoria.class,categoria);
        Produto produto = new Produto(nome,valor,quantidade,descricao,c,registro);

        for(CaracteristicasRequest caracteristicasRequest: caracteristicas){
            Caracteristicas caracteristicas = caracteristicasRequest.toModel(produto);
            manager.persist(caracteristicas);
        }

        return produto;
    }

    public List<CaracteristicasRequest> getCaracteristicas() {
        return caracteristicas;
    }
}
