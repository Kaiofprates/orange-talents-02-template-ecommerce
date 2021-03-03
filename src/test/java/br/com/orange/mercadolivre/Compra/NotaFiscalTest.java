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
public class NotaFiscalTest {

   @Autowired
   private MockMvc mockMvc;

   @Test    @WithMockUser("email@email.br")
   @DisplayName("Deve lidar com a rota fake de notas fiscais")
   public  void testaNota() throws Exception {

       String request = "{\n" +
               "\t\"idCompra\" : 1,\n" +
               "\t\"idComprador\" : 2\n" +
               "}";

       mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/notas-fiscais")
               .accept(MediaType.APPLICATION_JSON)
               .contentType(MediaType.APPLICATION_JSON)
               .content(request)
       ).andExpect(MockMvcResultMatchers.status().isOk())
               .andDo(MockMvcResultHandlers.print());

   }


}
