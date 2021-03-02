package br.com.orange.mercadolivre.Detalhe;

import br.com.orange.mercadolivre.Opiniao.Opiniao;

import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DetalheOpiniao {

    private Set<Opiniao> opinioes;

    public DetalheOpiniao(Set<Opiniao> opinioes) {
        this.opinioes = opinioes;
    }

    public <T> Set <T> buscaOpinioes(Function<Opiniao, T> funcaoMap){
        return this.opinioes.stream().map(funcaoMap).collect(Collectors.toSet());
    }

    public Set<Map<String,String>> getOpinioes() {
        return  this.buscaOpinioes(opiniao -> {
            return Map.of(
                    "titulo", opiniao.getTitulo(),
                    "descricao", opiniao.getDescricao()
            );
        });
    }

    public double media(){
        Set<Integer> notas = this.buscaOpinioes(opiniao -> opiniao.getNota());
        OptionalDouble notaInt = notas.stream().mapToInt(nota -> nota).average();
        return notaInt.orElse(0.0);
    }


}
