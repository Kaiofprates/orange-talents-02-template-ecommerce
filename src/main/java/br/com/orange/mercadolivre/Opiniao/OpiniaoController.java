package br.com.orange.mercadolivre.Opiniao;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/mercadolivre")
public class OpiniaoController {


    @PostMapping("/produtos/{id}/opiniao")
    public String cadastroOpiniao(@PathVariable("id") Long id, @Valid @RequestBody OpiniaoRequest request){
        return request.toString();
    }

}
