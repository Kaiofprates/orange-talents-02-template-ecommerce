package br.com.orange.mercadolivre.Compra.Ranking;

import javax.validation.constraints.NotNull;

public class RankingRequest {

    @NotNull
    private Long idCompra;

    @NotNull
    private Long idUsuarioProduto;

    public RankingRequest(@NotNull Long idCompra, @NotNull Long idUsuarioProduto) {
        this.idCompra = idCompra;
        this.idUsuarioProduto = idUsuarioProduto;
    }

    @Override
    public String toString() {
        return "RankingRequest{" +
                "idCompra=" + idCompra +
                ", idUsuarioProduto=" + idUsuarioProduto +
                '}';
    }
}
