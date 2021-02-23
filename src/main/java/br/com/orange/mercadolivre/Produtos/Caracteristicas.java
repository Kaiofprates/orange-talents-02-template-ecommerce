package br.com.orange.mercadolivre.Produtos;

public class Caracteristicas {
    private String nome;
    private String valor;

    public Caracteristicas(String nome, String valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public String getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "Caracteristicas{" +
                "nome='" + nome + '\'' +
                ", valor='" + valor + '\'' +
                '}';
    }
}
