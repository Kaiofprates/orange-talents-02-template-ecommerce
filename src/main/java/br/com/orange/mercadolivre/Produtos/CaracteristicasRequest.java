package br.com.orange.mercadolivre.Produtos;

public class CaracteristicasRequest {

    private String nome;
    private String descricao;

    public CaracteristicasRequest(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNome() {
        return nome;
    }

    public Caracteristicas toModel(Produto produto){
        return new Caracteristicas(nome,descricao,produto);
    }

}
