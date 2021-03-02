package br.com.orange.mercadolivre.Compra;

import br.com.orange.mercadolivre.handlers.exceptions.validation.UniqueValues.UniqueValue;

import javax.validation.constraints.*;

public class PaypalRequest {

    @NotBlank
    @UniqueValue(domainClass = Transacao.class,fieldName = "idTransacao")
    private String idTransacao;

    @PositiveOrZero
    @Min(0)
    @Max(1)
    private int status;

    public PaypalRequest(@NotBlank String idTransacao, @PositiveOrZero @Min(0) @Max(1) int status) {
        this.idTransacao = idTransacao;
        this.status = status;
    }

    @Override
    public String toString() {
        return "PaypalRequest{" +
                "idTransacao='" + idTransacao + '\'' +
                ", status=" + status +
                '}';
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    public Boolean getStatus() {
        if(this.status == 0){
            return  false;
        }else{
            return true;
        }
    }

}
