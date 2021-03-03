package br.com.orange.mercadolivre.Detalhe;

import br.com.orange.mercadolivre.Produtos.Caracteristica;

public class DetalheCaracterisca {

    private String nome;
    private String descricao;

    public DetalheCaracterisca(Caracteristica caracteristica) {
        this.nome = caracteristica.getNome();
        this.descricao = caracteristica.getDescricao();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
