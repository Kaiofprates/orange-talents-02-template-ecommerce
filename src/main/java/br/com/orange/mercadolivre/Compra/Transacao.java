package br.com.orange.mercadolivre.Compra;

import br.com.orange.mercadolivre.handlers.exceptions.validation.UniqueValues.UniqueValue;
import br.com.orange.mercadolivre.handlers.exceptions.validation.ValidID.ExistId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity

public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Boolean status;

    @NotBlank
    private String idTransacao;

    @NotNull
    private LocalDateTime registro = LocalDateTime.now(ZoneId.systemDefault());

    @ManyToOne
    private Compra compra;

    @Deprecated
    public Transacao(){}


    public Transacao(@NotNull Boolean status, @NotBlank String idTransacao, Compra compra) {
        this.status = status;
        this.idTransacao = idTransacao;
        this.compra = compra;
    }

}
