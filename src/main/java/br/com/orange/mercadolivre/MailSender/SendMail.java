package br.com.orange.mercadolivre.MailSender;

import br.com.orange.mercadolivre.handlers.exceptions.MailSenderException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public  class SendMail {

    @Autowired
    private JavaMailSender mailSender;

    public String send(String mens, String to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(mens);
        message.setTo(to);
        message.setFrom("orangetalents3@gmail.com");

        try {
            mailSender.send(message);
            return "Email enviado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace();
            throw  new MailSenderException("Não foi possível enviar o email");
        }
    }

}