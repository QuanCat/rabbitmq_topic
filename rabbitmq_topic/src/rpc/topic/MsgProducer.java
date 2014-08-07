package rpc.topic;

import java.io.IOException;

import com.rabbitmq.client.Channel;

public class MsgProducer {
	private static final String EXCHANGE_NAME = "topic_srv";
	private String routingKey = "f.middle.w";
	private String message = "I am from the MsgProducer";
	
	public void sendMsg(Channel channel) throws IOException {
		channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());
	    System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
		
	}

}
