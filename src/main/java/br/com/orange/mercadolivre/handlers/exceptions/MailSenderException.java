package br.com.orange.mercadolivre.handlers.exceptions;

public class MailSenderException extends  RuntimeException{
    public MailSenderException(String message){
        super(message);
    }
}