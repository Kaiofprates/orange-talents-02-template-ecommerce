package br.com.orange.mercadolivre.Produtos;

import br.com.orange.mercadolivre.Categoria.Categoria;
import br.com.orange.mercadolivre.Opiniao.Opiniao;
import br.com.orange.mercadolivre.Pergunta.Pergunta;
import br.com.orange.mercadolivre.Usuario.Usuario;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
public class Produto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private int quantidade;
    private String descricao;
    private BigDecimal valor;
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    private Usuario usuario;
    @OneToMany(mappedBy = "produto",cascade = CascadeType.PERSIST)
    private Set<Caracteristica> caracteristicas = new HashSet<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<ImagemProduto> imagens = new HashSet<>();

    @OneToMany(mappedBy = "produto", cascade = CascadeType.MERGE)
    private Set<Opiniao> opinioes = new HashSet<>();

    @OneToMany(mappedBy = "produto")
    @OrderBy("titulo asc")
    private SortedSet<Pergunta> perguntas = new TreeSet<>();


    @Deprecated
    public Produto(){
    }

    public Produto(String nome, int quantidade, String descricao,
                   BigDecimal valor, Categoria categoria,
                   Usuario usuario, Collection<CaracteristicaRequest> caracteristicas) {
        this.nome = nome;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.valor = valor;
        this.categoria = categoria;
        this.usuario = usuario;
        Set<Caracteristica> novasCaracteristicas =  caracteristicas.stream().map(caracteristica -> caracteristica.toModel(this)).collect(Collectors.toSet());
        this.caracteristicas.addAll(novasCaracteristicas);
        Assert.isTrue(this.caracteristicas.size() >= 3, "Cadastre no minimo 3 caracteristicas");

    }

    public Long getId() { return id; }
    public String getNome() {return nome; }
    public int getQuantidade() { return quantidade; }
    public String getDescricao() { return descricao; }
    public BigDecimal getValor() { return valor; }
    public Categoria getCategoria() { return categoria; }
    public Set<ImagemProduto> getImagens() { return imagens; }
    public Usuario getUsuario() {
        return usuario;
    }
    public Set<Caracteristica> getCaracteristicas() {
        return caracteristicas;
    }
    public Set<Opiniao> getOpinioes() { return opinioes; }


    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", valor=" + valor +
                ", categoria=" + categoria +
                ", usuario=" + usuario +
                ", caracteristicas=" + caracteristicas +
                ", imagens=" + imagens +
                '}';
    }

    public void associaImagens(Set<String> links) {
      Set<ImagemProduto> imagens  =   links.stream()
              .map( link -> new ImagemProduto(this,link))
              .collect(Collectors.toSet());
    this.imagens.addAll(imagens);
    }

    /*
    *  Mapeamentos para a página de detalhe do produto
    */

    public <T> Set <T> buscaCarcteristicas(Function<Caracteristica,T> funcaoMap){
        return this.caracteristicas.stream().map(funcaoMap).collect(Collectors.toSet());
    }

    public <T> Set <T> buscaImagens(Function<ImagemProduto, T> funcaoMap){
        return this.imagens.stream().map(funcaoMap).collect(Collectors.toSet());
    }

    public <T extends Comparable<T>> SortedSet<T> buscaPerguntas(Function<Pergunta, T> funcaoMapeadora) {
        return this.perguntas.stream().map(funcaoMapeadora)
                .collect(Collectors.toCollection(TreeSet :: new));
    }

    public boolean abateEstoque(@Positive int quantidade) {
        if(quantidade <= 0 ){
            return false;
        }
        if(this.quantidade >= quantidade){
            this.quantidade-=quantidade;
            return true;
        }else{
            return false;
        }
    }
}
