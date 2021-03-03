package br.com.orange.mercadolivre.Compra;

import br.com.orange.mercadolivre.handlers.exceptions.validation.UniqueValues.UniqueValue;

import javax.validation.constraints.*;

public class PageseguroRequest {
    @NotBlank
    @UniqueValue(domainClass = Transacao.class,fieldName = "idTransacao")
    private String idTransacao;

    @NotNull
    private Status status;

    private enum Status{
        SUCESSO,
        ERRO
    }

    public PageseguroRequest(@NotBlank String idTransacao, @NotNull Status status) {
        this.idTransacao = idTransacao;
        this.status = status;
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    public Boolean getStatus() {
        if(this.status == Status.ERRO){
            return  false;
        }else{
            return true;
        }
    }

}
