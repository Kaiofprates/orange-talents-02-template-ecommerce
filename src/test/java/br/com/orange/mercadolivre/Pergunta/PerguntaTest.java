package br.com.orange.mercadolivre.Pergunta;

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
public class PerguntaTest {

    /**
     * 1 - pergunta enviada com sucesso X
     * 2 - pergunta direcionada a um produto não cadastrado X
     * 3 - pergunta null  X
     */

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser("email@email.br")
    @DisplayName(" Deve lidar com um id de um produto inexistente")
    public void produtoInexistenteTest() throws Exception {

        String request = "{\n" +
                "\t\"titulo\" : \"Qual o preço com desconto?\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/produtos/100/perguntas")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

    }

    @ParameterizedTest
    @WithMockUser("email@email.br")
    @MethodSource("requests")
    @DisplayName(" Deve lidar com o sucesso e campo vazio  no cadastro de pergunta")
    public void perguntaTest(String request, ResultMatcher status) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/produtos/2/perguntas")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(status)
                .andDo(MockMvcResultHandlers.print());

    }
    public static Stream<Arguments> requests(){
        return Stream.of(
                Arguments.of("{\n" +
                                "\t\"titulo\" : \"Qual o preço com desconto?\"\n" +
                                "}", MockMvcResultMatchers.status().isOk()),
                Arguments.of("{\n" +
                        "\t\"titulo\" : \"\"\n" +
                        "}", MockMvcResultMatchers.status().isBadRequest())); }

}
