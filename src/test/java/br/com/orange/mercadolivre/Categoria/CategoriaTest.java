package br.com.orange.mercadolivre.Categoria;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaTest {

    @Autowired
    private MockMvc mockMvc;


    String categoriaRequest = "{\n" +
            "    \"nome\" : \"Tecnologia\",\n" +
            "    \"categoria\" : 1\n" +
            "}";

    @Test(expected = AssertionError.class)
    public  void  categoriaCriadaComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/categoria")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.categoriaRequest)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("").isEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test(expected = AssertionError.class)
    public  void  categoriaComIdDeCategoriaMaeInvalido() throws Exception {

        String categoriaComIdValido = "{\n" +
                "    \"nome\" : \"Tecnologia\",\n" +
                "    \"categoria\" : 4\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/categoria")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoriaComIdValido)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("").isEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test(expected = AssertionError.class)
    public  void  categoriaComNomeDuplicado() throws Exception {

        // vamos popular o banco primeiro, mas em outro ponto

        String categoriaNomeDuplicado = "{\n" +
                "    \"nome\" : \"Tecnologia\",\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/categoria")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.categoriaRequest)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$[0].field").isString())
                .andExpect(jsonPath("$[0].status").isNumber())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test(expected = AssertionError.class)
    public  void  categoriaComNomeEmBranco() throws Exception {

        String categoriaNomeEmBranco = "{\n" +
                "    \"nome\" : \"\",\n" +
                "    \"categoria\" : 1\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/categoria")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoriaNomeEmBranco)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$[0].field").isString())
                .andExpect(jsonPath("$[0].status").isNumber())
                .andDo(MockMvcResultHandlers.print());
    }




}
