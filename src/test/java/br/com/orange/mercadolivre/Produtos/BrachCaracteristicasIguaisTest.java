package br.com.orange.mercadolivre.Produtos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest

public class BrachCaracteristicasIguaisTest {


    @Test
    @DisplayName("Teste unitário para metodo de validação de categorias")
    @ParameterizedTest
    @MethodSource("requestLista")
    public void testaMetodoDeValidacaoDeCategorias(int check,List<CaracteristicaRequest> caracteristicaRequests){
        ProdutoRequest request = new ProdutoRequest("Tenis",1,BigDecimal.TEN,"Um tenis legal", 1l, caracteristicaRequests);
        Assertions.assertEquals(check,request.caracteristicasIguals().size());

    }

    public static Stream<Arguments> requestLista(){
        return Stream.of(
                Arguments.of(1, List.of(
                                new CaracteristicaRequest("Marca","adidas"),
                                new CaracteristicaRequest("Cor", "Branco"),
                                new CaracteristicaRequest("Marca","adidas"))),
                Arguments.of(2, List.of(
                        new CaracteristicaRequest("Cor", "Branco"),
                        new CaracteristicaRequest("Cor", "Branco"),
                        new CaracteristicaRequest("Marca","adidas"),
                        new CaracteristicaRequest("Marca","adidas"))),
                Arguments.of(0, List.of(
                        new CaracteristicaRequest("Marca","adidas"),
                        new CaracteristicaRequest("Cor", "Branco"),
                        new CaracteristicaRequest("tamano","medio")))

        );
    }



}
