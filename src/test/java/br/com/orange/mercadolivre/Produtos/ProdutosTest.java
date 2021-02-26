package br.com.orange.mercadolivre.Produtos;

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

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ProdutosTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    @WithMockUser(username = "email@email.br",password = "123456")
    public void cadastroDeProdutoPorUsuarioTeste() throws Exception {


        String request = "{\n" +
                "    \"nome\" : \"Tenis\",\n" +
                "    \"valor\" : 1089.0,\n" +
                "    \"descricao\" : \"Adidas usado \",\n" +
                "    \"quantidade\" : 1,\n" +
                "    \"idCategoria\" : 1,\n" +
                "    \"caracteristicas\" : [\n" +
                "        {\"nome\" : \"Marca\",\"descricao\" : \"Adidas\"} ,\n" +
                "        { \"nome\" : \"Cor \" , \"descricao\" : \"Branco\"},\n" +
                "        {\"nome\" : \"Desconto\", \"descricao\": \"10% a prazo\"}    \n" +
                "    ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/produtos")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @Transactional
    @WithMockUser(username = "email@email.br",password = "123456")
    public void idCategoriaInvalidoTeste() throws Exception {

        String request = "{\n" +
                "    \"nome\" : \"Tenis\",\n" +
                "    \"valor\" : 1089.0,\n" +
                "    \"descricao\" : \"Adidas usado \",\n" +
                "    \"quantidade\" : 1,\n" +
                "    \"idCategoria\" : 10,\n" +
                "    \"caracteristicas\" : [\n" +
                "        {\"nome\" : \"Marca\",\"descricao\" : \"Adidas\"} ,\n" +
                "        { \"nome\" : \"Cor \" , \"descricao\" : \"Branco\"},\n" +
                "        {\"nome\" : \"Desconto\", \"descricao\": \"10% a prazo\"}    \n" +
                "    ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/produtos")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$[0].field").isString())
                .andExpect(jsonPath("$[0].status").isNumber())
                .andExpect(jsonPath("$[0].error").value("Id not found"))
                .andDo(MockMvcResultHandlers.print());

    }

}
