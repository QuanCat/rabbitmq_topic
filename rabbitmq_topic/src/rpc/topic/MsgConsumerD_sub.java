package rpc.topic;

import java.io.IOException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class MsgConsumerD_sub {
	private static final String EXCHANGE_NAME = "topic_srv";
	private static final String EXCHANGE_TYPE_TOPIC = "topic";
	private static final String QUEUE_NAME_1 = "topic_srv_q1";
	private static final String QUEUE_NAME_3 = "topic_srv_q3";

	private Connection connection;
	private Channel channel;
	private QueueingConsumer consumer_D;

	public MsgConsumerD_sub init() throws IOException {
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
		// String queueName = channel.queueDeclare().getQueue();

		// Start a Consumer
		consumer_D = new QueueingConsumer(channel);
		// basicConsume(java.lang.String queue, boolean autoAck,
		// java.lang.String consumerTag, Consumer callback)
		// Start two non-nolocal, non-exclusive consumers.
		channel.basicConsume(QUEUE_NAME_1, false, "comsumer_tag4", consumer_D);
		//channel.basicConsume(QUEUE_NAME_3, true, "customer_tag_4D", consumer_D);
	/*	Thread q1 = new Thread(new Runnable(){
//
			@Override
			public void run() {
				try {
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
		});*/
		
	/*	Thread q2 = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
		});
		
		q1.start();
		q2.start();*/
		
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

	public void receiveMsg_D() {
		while (true) {
			QueueingConsumer.Delivery delivery;
			try {

				delivery = consumer_D.nextDelivery();
				String message = new String(delivery.getBody());
				String routingKey = delivery.getEnvelope().getRoutingKey();

				System.out.println(" [x] Received_D '" + routingKey + "':'"
						+ message + "'");

				try {
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (ShutdownSignalException | ConsumerCancelledException
					| InterruptedException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

}
