package com.ms.users.infra.producer;


import com.ms.users.domain.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    final RabbitTemplate rabbitTemplate;

    public UserProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value(value = "${broker.queue.email.name}")
    private String routingKey;

    public void publishMessage(User user){
        record EmailDTO(Long userId, String emailTo, String subject, String text){}
        String emailText = """
                Olá, %s
                
                Agradecemos a sua preferência!
                Estamos passando para informar que o cadastro da sua conta foi realizado com sucesso.
                """.formatted(user.getName());
        var emailDTO = new EmailDTO(user.getId(), user.getEmail(), "Cadastro realizado com sucesso!", emailText);

        rabbitTemplate.convertAndSend("", routingKey, emailDTO);

    }
}
