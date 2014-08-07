package rpc.topic;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Srv {
	private static final String EXCHANGE_NAME = "topic_srv";
	private static final String QUEUE_NAME_1 = "topic_srv_q1";
	private static final String QUEUE_NAME_2 = "topic_srv_q2";
	private static final String EXCHANGE_TYPE_TOPIC = "topic";
	private static final String ROUTING_KEY_QUEUE1 = "*.middle.*";
	private static final String ROUTING_KEY_QUEUE2 = "front.#";

	private Connection connection;
	private Channel channel;
//	private QueueingConsumer consumer_A;

	public Channel init() throws IOException {
		// Create a connection
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("quan");
		factory.setPassword("quan");
		factory.setVirtualHost("rpctest");
		factory.setHost("localhost");
		connection = factory.newConnection();
		// Create a channel
		channel = connection.createChannel();
		// Setup AMQP fabric
		// Topic Exchange: non-auto_delete, non-durable
		channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE_TOPIC, false,
				false, null);

		// Queue1: non-durable, non-exclusive, auto-delete
		channel.queueDeclare(QUEUE_NAME_1, false, false, true, null);
		// Queue2: non-durable, non-exclusive, auto-delete
		channel.queueDeclare(QUEUE_NAME_2, false, false, true, null);

		// Bind with Queue1:
		channel.queueBind(QUEUE_NAME_1, EXCHANGE_NAME, ROUTING_KEY_QUEUE1);
		// Bind with Queue2:
		channel.queueBind(QUEUE_NAME_2, EXCHANGE_NAME, ROUTING_KEY_QUEUE2);
		
		// Start a Consumer
//		consumer_A = new QueueingConsumer(channel);
		// basicConsume(java.lang.String queue, boolean autoAck,
		// java.lang.String consumerTag, Consumer callback)
		// Start two non-nolocal, non-exclusive consumers.
//		channel.basicConsume(QUEUE_NAME_1, false, "comsumer_tag1", consumer_A);
//		channel.basicConsume(QUEUE_NAME_2, false, "comsumer_tag2", consumer); // !!!!!!!!!!!!!!true -> false

		//System.out.println("Waiting for RPC calls...");

		return channel;
	}

	public void closeConnection() {
		if (connection != null) {
			try {
				channel.close();
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
