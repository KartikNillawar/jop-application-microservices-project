package com.kncodes.companyms.Company.messaging;

import com.kncodes.companyms.Company.CompanyService;
import com.kncodes.companyms.Company.dto.ReviewMessage;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReviewMessageConsumer {
    private final CompanyService companyService;

    public ReviewMessageConsumer(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RabbitListener(queues = "companyRatingQueue", ackMode = "MANUAL")
    public void consumeMessage(ReviewMessage reviewMessage, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException
    {
        companyService.updateCompanyRating(reviewMessage);
        channel.basicAck(tag, false);
    }
}
