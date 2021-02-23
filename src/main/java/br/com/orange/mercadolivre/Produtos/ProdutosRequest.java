package br.com.orange.mercadolivre.Produtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

public class ProdutosRequest {

    private String nome;
    private BigDecimal valor;
    private String descricao;
    private Long categoria;
    private LocalDateTime registro = LocalDateTime.now(ZoneId.systemDefault());
    private List<Caracteristicas> caracteristicas ;

    public ProdutosRequest(String nome, BigDecimal valor, String descricao, Long categoria, List<Caracteristicas> caracteristicas) {
        this.nome = nome;
        this.valor = valor;
        this.descricao = descricao;
        this.categoria = categoria;
        this.caracteristicas = caracteristicas;
    }

    @Override
    public String toString() {
        return "ProdutosRequest{" +
                "nome='" + nome + '\'' +
                ", valor=" + valor +
                ", descricao='" + descricao + '\'' +
                ", categoria=" + categoria +
                ", caracteristicas=" + caracteristicas +
                '}';
    }

    public Long getCategoria() {
        return categoria;
    }

    public List<Caracteristicas> getCaracteristicas() {
        return caracteristicas;
    }
}
