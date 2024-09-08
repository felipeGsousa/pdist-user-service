package br.edu.ifpb.user_service.producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class LikeProducer {

    final RabbitTemplate rabbitTemplate;

    public LikeProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setReplyTimeout(60000);
    }

    @Value(value = "${broker.queue.post.like}")
    private String postLikeRoutingKey;

    @Value(value = "${broker.queue.comment.like}")
    private String commentLikeRoutingKey;

    public void likePost(HashMap<String, Object> likeAction){
        rabbitTemplate.convertAndSend("", postLikeRoutingKey, likeAction);
    }

    public void likeComment(HashMap<String, Object> likeAction){
        rabbitTemplate.convertAndSend("", commentLikeRoutingKey, likeAction);
    }

}
