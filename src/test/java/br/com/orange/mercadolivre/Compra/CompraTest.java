package br.com.orange.mercadolivre.Compra;

import br.com.orange.mercadolivre.Produtos.Produto;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
public class CompraTest {

    /**
     * 1 - Boundary teste para metodo de abatimento
     * 2 - teste de integração para o controller de compra
     */



    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager manager;

    @ParameterizedTest
    @CsvSource(value = {"1:true", "2:true", "10:false", "-1:false"}, delimiter = ':')
    @DisplayName("Testa o abatimento de um produto em estoque")
    public void abatimentoTeste(int input,  Boolean status){
        Produto produto = manager.find(Produto.class,1L);
        Assert.isTrue(produto.abateEstoque(input) == status);
    }

    @Test
    @DisplayName("Testa o controller de compras para o Paypal")
    @WithMockUser("email@email.br")
    public void controllerCompraTeste() throws Exception {

        String request = "{\n" +
                "\"quantidade\" : 1,\n" +
                "\"pagamento\" : \"PAYPAL\",\n" +
                "\"produtoId\": 2\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/produtos/api/compra")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("Testa o controller de compras para o PagSeguro")
    @WithMockUser("email@email.br")
    public void controllerCompraTeste2() throws Exception {

        String request = "{\n" +
                "\"quantidade\" : 1,\n" +
                "\"pagamento\" : \"PAGSEGURO\",\n" +
                "\"produtoId\": 2\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/produtos/api/compra")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(MockMvcResultMatchers.status().is(200))
                .andDo(MockMvcResultHandlers.print());
    }


    @ParameterizedTest
    @WithMockUser("email@email.br")
    @MethodSource("requests")
    @DisplayName(" Deve lidar com casos de BadRequest no controller de compra")

    public void compraBadRequestTest(String request) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/produtos/api/compra")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }
    public static Stream<Arguments> requests(){
        return Stream.of(
                Arguments.of("{\n" +
                        "\"quantidade\" : 80,\n" +
                        "\"pagamento\" : \"PAYPAL\",\n" +
                        "\"produtoId\": 1\n" +
                        "}"),
                Arguments.of("{\n" +
                        "\"quantidade\" : 0,\n" +
                        "\"pagamento\" : \"PAYPAL\",\n" +
                        "\"produtoId\": 1\n" +
                        "}"),
                Arguments.of("{\n" +
                        "\"quantidade\" : ,\n" +
                        "\"pagamento\" : \"PAYPAL\",\n" +
                        "\"produtoId\": 1\n" +
                        "}"),
                Arguments.of("{\n" +
                        "\"quantidade\" : -1,\n" +
                        "\"pagamento\" : \"PAYPAL\",\n" +
                        "\"produtoId\": 1\n" +
                        "}"),
                Arguments.of("{\n" +
                        "\"quantidade\" : 2,\n" +
                        "\"pagamento\" : \"PAYPA\",\n" +
                        "\"produtoId\": 1\n" +
                        "}"),
                Arguments.of("{\n" +
                        "\"quantidade\" : 2,\n" +
                        "\"pagamento\" : \"\",\n" +
                        "\"produtoId\": 1\n" +
                        "}"),
                Arguments.of("{\n" +
                        "\"quantidade\" : 2,\n" +
                        "\"pagamento\" : \"PAYPA\",\n" +
                        "\"produtoId\": 90\n" +
                        "}"),
                Arguments.of("{\n" +
                        "\"quantidade\" : 2,\n" +
                        "\"pagamento\" : \"PAYPA\",\n" +
                        "\"produtoId\": \n" +
                        "}")
                ); }

}
