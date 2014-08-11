package rpc.topic;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.GetResponse;
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
		try{
			connection = factory.newConnection();
			// Create a channel
			channel = connection.createChannel();
			
			channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE_TOPIC);
			//String queueName = channel.queueDeclare().getQueue();
			channel.basicQos(1);
			// Start a Consumer
			consumer_B = new QueueingConsumer(channel);
			// basicConsume(java.lang.String queue, boolean autoAck, java.lang.String consumerTag, Consumer callback)
			// Start two non-nolocal, non-exclusive consumers.
			channel.basicConsume(QUEUE_NAME_2, false, "comsumer_tag2", consumer_B);
		} catch(Exception e) {
			e.printStackTrace();
		}
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
		boolean autoAck = false;

		while (true) {
			QueueingConsumer.Delivery delivery;
			try {
				//GetResponse response = channel.basicGet(QUEUE_NAME_2, autoAck);
				
				//if (response == null) {
					delivery = consumer_B.nextDelivery();
					String message = new String(delivery.getBody());
					String routingKey = delivery.getEnvelope().getRoutingKey();

					System.out.println(" [x] Received_B '" + routingKey + "':'"
							+ message + "'");
					//System.out.println("%%%%%%="+response);
					// to ack the message, the false flag is to do with multiple message acknowledgement
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
					
				//} else {
				//	System.out.println("previous message has not been acknowledged  BBB");
				//}
				    /*AMQP.BasicProperties props = response.getProps();
				    
				    byte[] body = response.getBody();
				    long deliveryTag = response.getEnvelope().getDeliveryTag();*/
			

			} catch (ShutdownSignalException | ConsumerCancelledException
					| InterruptedException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
      
		} 

	}
}
