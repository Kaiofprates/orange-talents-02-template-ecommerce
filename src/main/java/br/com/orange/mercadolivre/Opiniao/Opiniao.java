package br.com.orange.mercadolivre.Opiniao;

import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;

@Entity

public class Opiniao {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Min(1) @Max(5)
    private int nota;
    @NotBlank
    private String titulo;
    @NotBlank
    @Size(max = 500)
    private String descricao;
    @ManyToOne
    private Produto produto;
    @ManyToOne
    private Usuario usuario;

    @Deprecated
    public Opiniao(){}

    public Opiniao(@Min(1) @Max(5) int nota, @NotBlank String titulo,
                   @NotBlank @Size(max = 500) String descricao,
                   @NotNull Produto produto,@NotNull Usuario usuario) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
        this.produto = produto;
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Opiniao)) return false;
        Opiniao opiniao = (Opiniao) o;
        return Objects.equals(descricao, opiniao.descricao) && Objects.equals(produto, opiniao.produto) && Objects.equals(usuario, opiniao.usuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descricao, produto, usuario);
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getNota() {
        return nota;
    }
}
