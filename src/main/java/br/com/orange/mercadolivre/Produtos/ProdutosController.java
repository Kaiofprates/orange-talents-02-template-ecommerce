package br.com.orange.mercadolivre.Produtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/mercadolivre/produtos")
public class ProdutosController {

    @Autowired
    private ValidaCategoria validaCategoria;
    @Autowired
    private ValidaListaDeCaracteristicas validaListaDeCaracteristicas;

    @InitBinder
    public void init(WebDataBinder binder){
        binder.addValidators(validaCategoria);
        binder.addValidators(validaListaDeCaracteristicas);
    }

    @PostMapping
    public String cadastraProduto(@RequestBody @Valid  ProdutosRequest request){
        return  request.toString();
    }
}
