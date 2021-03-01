package br.com.orange.mercadolivre.Pergunta;

import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Pergunta {

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
}
