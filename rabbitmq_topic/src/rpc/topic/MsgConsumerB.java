package rpc.topic;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class MsgConsumerB {
	private static final String EXCHANGE_NAME = "topic_srv";
	private static final String EXCHANGE_TYPE_TOPIC = "topic";
	private static final String QUEUE_NAME_2 = "topic_srv_q2";
	
	private Connection connection;
	private Channel channel;
	private QueueingConsumer consumer_B;
	
	public MsgConsumerB init() throws IOException {
		// Create a connection
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("quan");
		factory.setPassword("quan");
		factory.setVirtualHost("rpctest");
		factory.setHost("localhost");
		connection = factory.newConnection();
		// Create a channel
		channel = connection.createChannel();
		
		channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE_TOPIC);
		//String queueName = channel.queueDeclare().getQueue();
		
		// Start a Consumer
		consumer_B = new QueueingConsumer(channel);
		// basicConsume(java.lang.String queue, boolean autoAck,
		// java.lang.String consumerTag, Consumer callback)
		// Start two non-nolocal, non-exclusive consumers.
		channel.basicConsume(QUEUE_NAME_2, false, "comsumer_tag2", consumer_B);
		
		return this;
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
	
public void receiveMsg_B() {
		
		while (true) {
			QueueingConsumer.Delivery delivery;
			try {
				delivery = consumer_B.nextDelivery();
				String message = new String(delivery.getBody());
		        String routingKey = delivery.getEnvelope().getRoutingKey();
	
		        System.out.println(" [x] Received_B '" + routingKey + "':'" + message + "'"); 
		        
			} catch (ShutdownSignalException | ConsumerCancelledException
					| InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
			
	         
		}

	}
}
