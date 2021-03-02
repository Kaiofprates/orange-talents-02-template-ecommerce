package br.com.orange.mercadolivre.Pergunta;

import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Pergunta  implements Comparable<Pergunta>{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String titulo;

    @NotNull
    private LocalDateTime criacao;

    @ManyToOne
    private Usuario perguntador;

    @ManyToOne
    private Produto produto;

    @Deprecated
    private Pergunta(){}

    public Pergunta(@NotBlank String titulo, @NotNull LocalDateTime criacao, Usuario perguntador, Produto produto) {
        this.titulo = titulo;
        this.criacao = criacao;
        this.perguntador = perguntador;
        this.produto = produto;
    }

    @Override
    public String toString() {
        return "Pergunta{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", criacao=" + criacao +
                ", perguntador=" + perguntador +
                ", produto=" + produto +
                '}';
    }

    public String getTitulo() {
        return titulo;
    }

    // implementação necessária para gerar a comparação no DetalheResponse e assim trazer um lista ordenada pelo titulo
    @Override
    public int compareTo(Pergunta o) {
        return this.titulo.compareTo(o.titulo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pergunta)) return false;
        Pergunta pergunta = (Pergunta) o;
        return titulo.equals(pergunta.titulo) && Objects.equals(criacao, pergunta.criacao)
                && Objects.equals(perguntador, pergunta.perguntador)
                && Objects.equals(produto, pergunta.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(titulo, criacao, perguntador, produto);
    }
}
