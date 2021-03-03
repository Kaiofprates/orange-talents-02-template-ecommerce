package br.com.orange.mercadolivre.Pergunta;

import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;
import org.springframework.util.Assert;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
;import java.time.LocalDateTime;
import java.time.ZoneId;

public class PerguntaRequest {

    @NotBlank
    private String titulo;
    @NotNull
    private LocalDateTime criacao = LocalDateTime.now(ZoneId.systemDefault());

    public PerguntaRequest(@NotBlank String titulo) {
        this.titulo = titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Deprecated
    public  PerguntaRequest(){}

    public Pergunta toModel(Usuario perguntador, Produto produto) {
        Assert.notNull(perguntador, "O usuário inválido");
        Assert.notNull(produto, "Não foi possível recuperar o produto informado");
        return new Pergunta(titulo,criacao,perguntador,produto);
    }
}
