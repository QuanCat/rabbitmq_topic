package rpc.topic;

import java.io.IOException;

import com.rabbitmq.client.Channel;

public class MsgProducer {
	private static final String EXCHANGE_NAME = "topic_srv";
	private String routingKey_AD = "q.middle.AD";
	private String routingKey_D = "quan.q";
	private String routingKey_BC = "front.quan";
	private String message;
	
	public void sendMsg(Channel channel) throws IOException {
		for (int i = 0; i < 100; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			message = "Message for BCs_" + i;
			channel.basicPublish(EXCHANGE_NAME, routingKey_BC, null, message.getBytes());
		    //System.out.println(" [x] Sent '" + routingKey_BC + "':'" + message + "'");
		}
		for (int i = 0; i < 3; i++) {
			message = "Message for D only_" + i;
			channel.basicPublish(EXCHANGE_NAME, routingKey_D, null, message.getBytes());
		    System.out.println(" [x] Sent '" + routingKey_D + "':'" + message + "'");
		}
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
		
		
		
	}

}
