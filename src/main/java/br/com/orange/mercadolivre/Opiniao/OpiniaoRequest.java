package br.com.orange.mercadolivre.Opiniao;

import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class OpiniaoRequest {
    @NotNull
    @Min(1)
    @Max(5)
    private int nota;
    @NotBlank
    private String titulo;
    @NotBlank
    @Size(max = 500)
    private String descricao;

    public OpiniaoRequest(int nota, String titulo, String descricao) {
        this.nota = nota;
        this.titulo = titulo;
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "OpiniaoRequest{" +
                "nota=" + nota +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    public Opiniao toModel(@Valid @NotNull Produto produto,@Valid @NotNull Usuario usuario) {

        /*
         * faço apenas a verificação de produto, porque o usuário
         * já tem uma verificação específica na classe AuthUtils
         */
        Assert.notNull(produto, "Produto não encontrado");
        Assert.isTrue(produto.getUsuario().getEmail() == usuario.getEmail(), "Compre esse produto para opinar sobre ele!");

        return new Opiniao(nota,titulo,descricao,produto,usuario);
    }
}
