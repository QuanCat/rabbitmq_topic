package rpc.topic;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
//import com.rabbitmq.client.QueueingConsumer;

public class Srv {
	private static final String EXCHANGE_NAME = "topic_srv";
	private static final String QUEUE_NAME_1 = "topic_srv_q1";
	private static final String QUEUE_NAME_2 = "topic_srv_q2";
	
	private static final String QUEUE_NAME_3 = "topic_srv_q3";
	
	private static final String EXCHANGE_TYPE_TOPIC = "topic";
	private static final String ROUTING_KEY_QUEUE1 = "*.middle.*";
	private static final String ROUTING_KEY_QUEUE2 = "front.#";
	private static final String ROUTING_KEY_QUEUE3 = "quan.#";

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
		
		//Queue3: 
		channel.queueDeclare(QUEUE_NAME_3, false, false, true, null);
		
		channel.basicQos(1);

		// Bind with Queue1:
		channel.queueBind(QUEUE_NAME_1, EXCHANGE_NAME, ROUTING_KEY_QUEUE1);
		// Bind with Queue2:
		channel.queueBind(QUEUE_NAME_2, EXCHANGE_NAME, ROUTING_KEY_QUEUE2);
		// Bind with Queue3:
		channel.queueBind(QUEUE_NAME_3, EXCHANGE_NAME, ROUTING_KEY_QUEUE3);


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
