package br.com.orange.mercadolivre.Opiniao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
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

import java.util.stream.Stream;

@SpringBootTest
@AutoConfigureMockMvc
public class OpiniaoTest {


    /**
     *  1 - sucesso ao cadastras uma opinião
     *  2 - checkagem de usuário autorizado à postar opiniao para um produto
     *  3 - verificar campos vazios ( fazer isso em um unico teste)
     *  4 - verificar se o produto existe
     *  5  - verificar o numero mínimo e máximo aplicado a nota ( pode ser um teste apenas unitário) [ resolvido no item 3 ]
     */



    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deveria lidar com uma opinião postada com sucesso")
    @WithMockUser(username = "email@email.br", password = "123456")
    public void cadastraOpiniaoComSucesso() throws Exception {
        String request = "{\n" +
                "\t\"nota\" : 1,\n" +
                "\t\"titulo\" : \"venda\",\n" +
                "\t\"descricao\" : \"Uma produto muito bom!\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/produtos/1/opiniao")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @DisplayName(" Deveria lidar com um usuário não autorizado tentando postar opinião")
    @WithMockUser(username = "johndoe@email.br", password = "123456")
    public void validaOpiniaoPorUsuario() throws  Exception{
        String request = "{\n" +
                "\t\"nota\" : 1,\n" +
                "\t\"titulo\" : \"venda\",\n" +
                "\t\"descricao\" : \"Uma produto muito bom!\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/produtos/1/opiniao")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName(" Deveria lidar com um produto não existente")
    @WithMockUser(username = "johndoe@email.br", password = "123456")
    public void validaProdutoInexistente() throws  Exception{
        String request = "{\n" +
                "\t\"nota\" : 1,\n" +
                "\t\"titulo\" : \"venda\",\n" +
                "\t\"descricao\" : \"Uma produto muito bom!\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/produtos/100/opiniao")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(MockMvcResultMatchers.status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @DisplayName("Deveria lidar com campos vazios")
    @ParameterizedTest
    @MethodSource("requests")
    @WithMockUser(username = "email@email.br", password = "123456")

    public  void testaCamposVazios(String request, ResultMatcher status) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/produtos/1/opiniao")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status)
                .andDo(MockMvcResultHandlers.print());
    }

    public static Stream<Arguments> requests(){
        return Stream.of(
                Arguments.of("{\n" +
                                "\t\"nota\" : 0,\n" +
                                "\t\"titulo\" : \"venda\",\n" +
                                "\t\"descricao\" : \"Uma produto muito bom!\"\n" +
                                "}", MockMvcResultMatchers.status().isBadRequest()),
                Arguments.of("{\n" +
                        "\t\"nota\" : 1,\n" +
                        "\t\"titulo\" : \"\",\n" +
                        "\t\"descricao\" : \"Uma produto muito bom!\"\n" +
                        "}", MockMvcResultMatchers.status().isBadRequest()),
                Arguments.of("{\n" +
                        "\t\"nota\" : 1,\n" +
                        "\t\"titulo\" : \"venda\",\n" +
                        "\t\"descricao\" : \"\"\n" +
                        "}", MockMvcResultMatchers.status().isBadRequest()),
                Arguments.of("{\n" +
                        "\t\"nota\" : 6,\n" +
                        "\t\"titulo\" : \"venda\",\n" +
                        "\t\"descricao\" : \"Uma produto muito bom!\"\n" +
                        "}", MockMvcResultMatchers.status().isBadRequest()),
                Arguments.of("{\n" +
                        "\t\"nota\" : ,\n" +
                        "\t\"titulo\" : \"venda\",\n" +
                        "\t\"descricao\" : \"Uma produto muito bom!\"\n" +
                        "}", MockMvcResultMatchers.status().isBadRequest())
        );
    }
}
