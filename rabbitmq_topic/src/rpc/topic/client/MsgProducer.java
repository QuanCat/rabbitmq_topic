package rpc.topic.client;

import java.io.IOException;

import rpc.topic.message.JsonToJava;
import rpc.topic.message.Message;
import rpc.topic.message.MsgDirectory;

import com.rabbitmq.client.Channel;

public class MsgProducer {
	private static final String EXCHANGE_NAME = "topic_srv";
	private String routingKey_AD = "q.middle.AD";
	private String routingKey_D = "quan.q";
	private String routingKey_BC = "front.quan";
	private String message;
	
	private JsonToJava json;
	private MsgDirectory msgDir;
	
	public MsgProducer() {
		json = new JsonToJava();
		msgDir = json.jsonToJava();
	}
	
	public void sendMsg(Channel channel) throws IOException {
		int size = msgDir.getMsgDirectory().size();
		//message from JSON
		if (size > 0) {
			for (Message msg: msgDir.getMsgDirectory()) {
				//channel.basicPublish(EXCHANGE_NAME, msg.getRoutingKey(), null, msg.getContent().getBytes());
			}
		}

		//Send messages to D only
		for (int i = 0; i < 3; i++) {
			message = "Message for D only_" + i;
			channel.basicPublish(EXCHANGE_NAME, routingKey_D, null, message.getBytes());
		    System.out.println(" [x] Sent '" + routingKey_D + "':'" + message + "'");
		}
		//Send messages to A and D
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			message = "Message for AD_" + i;
			channel.basicPublish(EXCHANGE_NAME, routingKey_AD, null, message.getBytes());
		    //System.out.println(" [x] Sent '" + routingKey_AD + "':'" + message + "'");
			
		}
		//Send messages to B and C
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//message = "Message for BCs_" + i;
			//channel.basicPublish(EXCHANGE_NAME, routingKey_BC, null, message.getBytes());
		    //System.out.println(" [x] Sent '" + routingKey_BC + "':'" + message + "'");
		}
		
		
		
	}

}
