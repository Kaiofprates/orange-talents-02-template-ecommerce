package br.com.orange.mercadolivre.Opiniao;

import br.com.orange.mercadolivre.UtilTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class OpiniaoTest {

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



}
