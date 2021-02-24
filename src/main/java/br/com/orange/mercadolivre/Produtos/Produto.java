package br.com.orange.mercadolivre.Produtos;

import br.com.orange.mercadolivre.Categoria.Categoria;
import br.com.orange.mercadolivre.Usuario.Usuario;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Produto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private BigDecimal valor;
    private Long quantidade;
    private String descricao;

    @ManyToOne
    private Categoria categoria;

   // @ManyToOne
   // private Usuario usuario;

    private LocalDateTime registro;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST)
    private List<Caracteristicas> caracteristicas ;

    @Deprecated
    public Produto(){
    }

    public Produto(String nome, BigDecimal valor, Long quantidade, String descricao, Categoria categoria, LocalDateTime registro) {
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;
        this.descricao = descricao;
        this.categoria = categoria;
        this.registro = registro;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", categoria=" + categoria +
                ", registro=" + registro +
                ", caracteristicas=" + caracteristicas.toString() +
                '}';
    }

    public void setCaracteristicas(List<Caracteristicas> caracteristicas) {
        this.caracteristicas = caracteristicas;
    }
}
