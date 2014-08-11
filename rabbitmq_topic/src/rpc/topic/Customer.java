package rpc.topic;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public interface Customer {

    void handleConsumeOk(String consumerTag);

    void handleCancelOk(String consumerTag);

    void handleCancel(String consumerTag) throws IOException;

    void handleShutdownSignal(String consumerTag, ShutdownSignalException sig);

    void handleRecoverOk(String consumerTag);


    void handleDelivery(String consumerTag,
                        Envelope envelope,
                        AMQP.BasicProperties properties,
                        byte[] body)
        throws IOException;
}
