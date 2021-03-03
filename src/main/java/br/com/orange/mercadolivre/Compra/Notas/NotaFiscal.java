package br.com.orange.mercadolivre.Compra.Notas;

import br.com.orange.mercadolivre.Compra.Compra;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class NotaFiscal {

    public void processa(Compra compra){
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> request = Map.of("idCompra",compra.getId(),"idComprador", compra.getComprador().getId());

        try{
            restTemplate.postForEntity("http://localhost:8080/mercadolivre/notas-fiscais",
                    request, String.class
            );
        }catch (RestClientException e){
            throw  e;
        }
    }



}
