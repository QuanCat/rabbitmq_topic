package rpc.topic.client.randqueue;

import java.io.IOException;

import rpc.topic.client.MsgConsumerB;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class MsgConsumerE {
	private static final String EXCHANGE_NAME = "exchange_rand";
	private static final String EXCHANGE_TYPE_TOPIC = "topic";

	private Connection connection;
	private Channel channel;
	private QueueingConsumer consumer_E;
	private ConnectionFactory factory;
	private String queueName;

	public MsgConsumerE init() throws IOException {
		// Create a connection
		factory = new ConnectionFactory();
		factory.setUsername("quan");
		factory.setPassword("quan");
		factory.setVirtualHost("rpctest");
		factory.setHost("localhost");
		try {
			connection = factory.newConnection();
			// Create a channel
			channel = connection.createChannel();

			channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE_TOPIC);
			queueName = channel.queueDeclare().getQueue();
			channel.basicQos(1);
			// Start a Consumer
			consumer_E = new QueueingConsumer(channel);
			// basicConsume(java.lang.String queue, boolean autoAck,
			// java.lang.String consumerTag, Consumer callback)
			// Start two non-nolocal, non-exclusive consumers.
			channel.basicConsume(queueName, false, "tag_e", consumer_E);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this;
	}

	public void receiveMsg_E() {

		while (true) {
			QueueingConsumer.Delivery delivery;
			try {

				delivery = consumer_E.nextDelivery();
				String message = new String(delivery.getBody());
				String routingKey = delivery.getEnvelope().getRoutingKey();

				System.out.println(" [x] Received_E '" + routingKey + "':'"
						+ message + "'");

				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);

			} catch (ShutdownSignalException | ConsumerCancelledException
					| InterruptedException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	public void closeConnection() {
		try {
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.basicConsume(queueName, false, "tag_e", consumer_E);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (channel != null) {
				try {
					channel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
