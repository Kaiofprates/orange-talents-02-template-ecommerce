package br.com.orange.mercadolivre.handlers.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FormErrorDTO> handle(MethodArgumentNotValidException exception) {
        List<FormErrorDTO> dto = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            int status = HttpStatus.BAD_REQUEST.value();
            LocalDateTime time = LocalDateTime.now();
            FormErrorDTO erro = new FormErrorDTO(e.getField(), mensagem,status,time);
            dto.add(erro);
        });
        return dto;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public FormErrorDTO handlerAsserts(IllegalArgumentException exception){
        LocalDateTime time = LocalDateTime.now();
        FormErrorDTO dto = new FormErrorDTO(null,exception.getMessage(),400,time);
        return dto;
    }


    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public FormErrorDTO handlerAssertsEnum( HttpMessageNotReadableException exception){
        LocalDateTime time = LocalDateTime.now();
        FormErrorDTO dto = new FormErrorDTO(null,exception.getMessage(),400,time);
        return dto;
    }


}
