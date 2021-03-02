package br.com.orange.mercadolivre.Detalhe;

import br.com.orange.mercadolivre.Produtos.Caracteristica;
import br.com.orange.mercadolivre.Produtos.Produto;
import org.springframework.boot.SpringApplication;

import java.math.BigDecimal;
import java.util.Set;
import java.util.SortedSet;


public class DetalheResponse {


    private String nome;
    private BigDecimal preco;
    private String descricao;
    private Set<DetalheCaracterisca> caracteristicas;
    private Set<String> imagens;
    private SortedSet<String> perguntas;


    public DetalheResponse(Produto produto) {
        this.nome = produto.getNome();
        this.preco = produto.getValor();
        this.descricao = produto.getDescricao();
        this.caracteristicas  = produto.buscaCarcteristicas(DetalheCaracterisca::new);
        this.imagens = produto.buscaImagens(img -> img.getLink());
        this.perguntas = produto.buscaPerguntas(pergunta -> pergunta.getTitulo());
    }


    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public Set<DetalheCaracterisca> getCaracteristicas() {
        return caracteristicas;
    }

    public Set<String> getImagens() {
        return imagens;
    }

    public SortedSet<String> getPerguntas() {
        return perguntas;
    }
}
