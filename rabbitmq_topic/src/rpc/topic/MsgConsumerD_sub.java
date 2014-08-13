package rpc.topic;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
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
	private ConnectionFactory factory;

	public MsgConsumerD_sub init() throws IOException {
		// Create a connection
		factory = new ConnectionFactory();
		factory.setUsername("quan");
		factory.setPassword("quan");
		factory.setVirtualHost("rpctest");
		factory.setHost("localhost");
		connection = factory.newConnection();
		// Create a channel
		channel = connection.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE_TOPIC);
		// String queueName = channel.queueDeclare().getQueue();
		channel.basicQos(1);
		// Start a Consumer
		consumer_D = new QueueingConsumer(channel);
		// basicConsume(java.lang.String queue, boolean autoAck,
		// java.lang.String consumerTag, Consumer callback)
		// Start two non-nolocal, non-exclusive consumers.
		channel.basicConsume(QUEUE_NAME_1, false, "comsumer_tag4", consumer_D);
		channel.basicConsume(QUEUE_NAME_3, false, "customer_tag_4only", consumer_D);
		return this;
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
		
		
	}
	
	public void closeConnection() {
		try {
		    connection = factory.newConnection();
		    channel = connection.createChannel();
		    channel.basicConsume(QUEUE_NAME_3, false, "customer_tag_4D", consumer_D);
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
		/*if (connection != null) {
			try {
				//channel.close();
				connection.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
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
