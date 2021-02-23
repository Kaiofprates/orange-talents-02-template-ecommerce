package br.com.orange.mercadolivre.Categoria;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.NestedServletException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaTest {

    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager manager;

    @Test
    public  void  categoriaCriadaComSucesso() throws Exception {

        String categoriaSucesso = "{\n" +
                "    \"nome\" : \"Tecnologia\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/categoria")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoriaSucesso)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @Transactional
    public  void  categoriaComIdDeCategoriaMaeInvalido() throws Exception {
        populaBanco();
        String categoriaComIdValido = "{\n" +
                "    \"nome\" : \"Android\",\n" +
                "    \"categoria\" : 4\n" +
                "}";
        try{
             mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/categoria")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(categoriaComIdValido));
            Assert.fail();
        }catch (NestedServletException e){
        }

    }

    @Test
    @WithMockUser
    @Transactional
    public  void  categoriaComNomeDuplicado() throws Exception {
        populaBanco();
        String categoriaNomeDuplicado = "{\n" +
                "    \"nome\" : \"Tecnologia\",\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/categoria")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoriaNomeDuplicado)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());
    }
    @Test
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
                .andExpect(jsonPath("$[0].error").value("O nome não pode estar vazio"))
                .andDo(MockMvcResultHandlers.print());
    }


    @Transactional
    private void populaBanco(){
        Categoria mae = new Categoria("Tecnologia");
        Categoria filha = new Categoria("Celulares");
        filha.setCategoria(mae);
        manager.persist(mae);
        manager.persist(filha);
    }


}
