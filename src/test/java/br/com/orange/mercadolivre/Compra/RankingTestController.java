package br.com.orange.mercadolivre.Compra;

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

public class RankingTestController {


    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deve lidar com a rota fake de raking")
    @WithMockUser("email@email.br")
    public  void testaRanking() throws Exception {

        String request = "{\n" +
                "\t\"idCompra\" : 1,\n" +
                "\t\"idUsuarioProduto\" : 2\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/ranking")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }


}
