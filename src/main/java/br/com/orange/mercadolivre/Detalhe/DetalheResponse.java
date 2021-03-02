package br.com.orange.mercadolivre.Detalhe;

import br.com.orange.mercadolivre.Produtos.Produto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.SortedSet;


public class DetalheResponse {


    private String nome;
    private BigDecimal preco;
    private String descricao;
    private Set<DetalheCaracterisca> caracteristicas;
    private Set<String> imagens;
    private SortedSet<String> perguntas;
    private Set<Map<String,String>> opinioes;
    private Double mediaNotas;

    public DetalheResponse(Produto produto) {
        this.nome = produto.getNome();
        this.preco = produto.getValor();
        this.descricao = produto.getDescricao();
        this.caracteristicas  = produto.buscaCarcteristicas(DetalheCaracterisca::new);
        this.imagens = produto.buscaImagens(img -> img.getLink());
        this.perguntas = produto.buscaPerguntas(pergunta -> pergunta.getTitulo());

        // refatora lógica de opinião

        DetalheOpiniao detalheOpiniao = new DetalheOpiniao(produto.getOpinioes());
        this.opinioes = detalheOpiniao.getOpinioes();
        this.mediaNotas = detalheOpiniao.media();

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

    public Set<Map<String, String>> getOpinioes() {
        return opinioes;
    }

    public Double getMediaNotas() {
        return mediaNotas;
    }
}
