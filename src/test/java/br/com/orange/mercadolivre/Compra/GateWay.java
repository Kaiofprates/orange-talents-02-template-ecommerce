package br.com.orange.mercadolivre.Compra;

import org.junit.jupiter.api.DisplayName;
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
public class GateWay {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @WithMockUser("email@email.br")
    @MethodSource("requests")
    @DisplayName(" Deve lidar com os controllers de Gateways PayPal e Pagseguro")

    public void gatewaysRequestsTest(String request, ResultMatcher status) throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(request)
        ).andExpect(status)
                .andDo(MockMvcResultHandlers.print());

    }
    public static Stream<Arguments> requests(){
        return Stream.of(
                Arguments.of("http://localhost:8080/mercadolivre/paypal.com/1?idTransacao=sfasdfaSDFSDF9&status=1", MockMvcResultMatchers.status().isOk()),
                Arguments.of("http://localhost:8080/mercadolivre/paypal.com/1?idTransacao=sfasdfaSDFSDF9&status=1", MockMvcResultMatchers.status().isBadRequest()),
                Arguments.of("http://localhost:8080/mercadolivre/paypal.com/19?idTransacao=sfasdfaSDFSDF10&status=1", MockMvcResultMatchers.status().isBadRequest()),
                Arguments.of("http://localhost:8080/mercadolivre/paypal.com/1?idTransacao=sfasdfaSDFSDF11&status=1", MockMvcResultMatchers.status().isBadRequest()),
                Arguments.of("http://localhost:8080/mercadolivre/paypal.com/1?idTransacao=sfasdfaSDFSDF912&status=0", MockMvcResultMatchers.status().isBadRequest()),
                Arguments.of("http://localhost:8080/mercadolivre/pagseguro.com/2?idTransacao=sfasdfaSDFSDF913&status=ERRO", MockMvcResultMatchers.status().isOk()),
                Arguments.of("http://localhost:8080/mercadolivre/pagseguro.com/2?idTransacao=sfasdfaSDFSDF914&status=SUCESSO", MockMvcResultMatchers.status().isOk()),
                Arguments.of("http://localhost:8080/mercadolivre/pagseguro.com/2?idTransacao=sfasdfaSDFSDF915&status=SUCESSO", MockMvcResultMatchers.status().isBadRequest())


        ); }

}
