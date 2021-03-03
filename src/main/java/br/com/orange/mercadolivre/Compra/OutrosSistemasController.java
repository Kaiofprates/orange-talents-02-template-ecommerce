package br.com.orange.mercadolivre.Compra;

import br.com.orange.mercadolivre.Compra.Notas.NotaFiscalRequest;
import br.com.orange.mercadolivre.Compra.Ranking.RankingRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/mercadolivre")
public class OutrosSistemasController {

    @PostMapping(value = "/notas-fiscais")
    public  void novaNota(@RequestBody @Valid NotaFiscalRequest request) throws  InterruptedException{
        System.out.printf("Criou nova nota fiscal "+request.toString());
        Thread.sleep(150);
    }

    @PostMapping(value = "/ranking")
    public  void ranking(@RequestBody @Valid RankingRequest request) throws  InterruptedException{
        System.out.printf("enviou para o ranking "+ request.toString());
        Thread.sleep(150);
    }


}
