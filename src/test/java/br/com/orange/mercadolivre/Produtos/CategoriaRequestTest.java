package br.com.orange.mercadolivre.Produtos;

import br.com.orange.mercadolivre.Categoria.Categoria;
import br.com.orange.mercadolivre.Usuario.Usuario;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.opentest4j.AssertionFailedError;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@SpringBootTest
public class CategoriaRequestTest {

    @Test
    @DisplayName("Testa a inserção de menos que duas caracteristicas")
    @ParameterizedTest
    @MethodSource("caracteristica2")
    public void caracteristicaMenorQueDoisTest(Collection<CaracteristicaRequest> caracteristicaRequests){

        Usuario usuario = new Usuario("manuel@email.com","123456");
        Categoria categoria = new Categoria("Informática");

        try{
            Produto produto = new Produto("Tenis",2,"um tenis legal!", BigDecimal.TEN,categoria,usuario,caracteristicaRequests);
            Assertions.fail();
        }catch (IllegalArgumentException e){

        }

    }
    @Test
    @ParameterizedTest
    @DisplayName("Testa a inserção de tres caracteristicas")
    @MethodSource("caracteristica3")
    public void caracteristicaTresElementosTest(Collection<CaracteristicaRequest> caracteristicaRequests){

        Usuario usuario = new Usuario("manuel@email.com","123456");
        Categoria categoria = new Categoria("Informática");
        Produto produto = new Produto("Tenis",2,"um tenis legal!",BigDecimal.TEN,categoria,usuario,caracteristicaRequests);
        Assertions.assertNotNull(produto);

    }

    @Test
    @DisplayName("Testa a inserção de mais de tres caracteristicas")
    @ParameterizedTest
    @MethodSource("caracteristica4")
    public void caracteristicaQuatroElementosTest(Collection<CaracteristicaRequest> caracteristicaRequests){

        Usuario usuario = new Usuario("manuel@email.com","123456");
        Categoria categoria = new Categoria("Informática");
        Produto produto = new Produto("Tenis",2,"um tenis legal!",BigDecimal.TEN,categoria,usuario,caracteristicaRequests);
        Assertions.assertNotNull(produto);

    }

    @Test
    @DisplayName("Testa a inserção de carcteristicas duplicadas")
    @ParameterizedTest
    @MethodSource("caracteristicaDuplicada")
    public void caracteristicaDuplicadaTest(Collection<CaracteristicaRequest> caracteristicaRequests){

        Usuario usuario = new Usuario("manuel@email.com","123456");
        Categoria categoria = new Categoria("Informática");

        try{
            Produto produto = new Produto("Tenis",2,"um tenis legal!",BigDecimal.TEN,categoria,usuario,caracteristicaRequests);
            Assertions.fail();
        }catch (AssertionFailedError e){

        }

    }

    public static Stream<Arguments> caracteristica2(){
        return Stream.of(
                Arguments.of(
                        List.of(
                                new CaracteristicaRequest("Marca","adidas"),
                                new CaracteristicaRequest("Cor", "Branco")
                        )
                )
        );
    }

    public static Stream<Arguments> caracteristica3(){
        return Stream.of(
                Arguments.of(
                        List.of(
                                new CaracteristicaRequest("Marca","adidas"),
                                new CaracteristicaRequest("Cor", "Branco"),
                                new CaracteristicaRequest("tamanho", "Medio")

                        )
                )
        );
    }

    public static Stream<Arguments> caracteristica4(){
        return Stream.of(
                Arguments.of(
                        List.of(
                                new CaracteristicaRequest("Marca","adidas"),
                                new CaracteristicaRequest("Tamanho", "Medio"),
                                new CaracteristicaRequest("Cor", "Branco"),
                                new CaracteristicaRequest("Descont", "10% a vista")

                        )
                )
        );
    }
    public static Stream<Arguments> caracteristicaDuplicada(){
        return Stream.of(
                Arguments.of(
                        List.of(
                                new CaracteristicaRequest("Marca","adidas"),
                                new CaracteristicaRequest("Marca","adidas"),
                                new CaracteristicaRequest("Cor", "Branco"),
                                new CaracteristicaRequest("tamanho", "Medio")

                        )
                )
        );
    }


}
