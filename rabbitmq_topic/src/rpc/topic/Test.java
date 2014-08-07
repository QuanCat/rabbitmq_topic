package rpc.topic;

import java.io.IOException;

import com.rabbitmq.client.Channel;

public class Test {
	
	public static void callSendMsg() {
		Srv srv = null;
		MsgProducer msg = null;
		try {
			srv = new Srv();
			msg = new MsgProducer();
			Channel channel = srv.init();
			msg.sendMsg(channel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void callReceiveMsg_A() {
		MsgConsumerA msgConsumer_A = null;		
		try {
			msgConsumer_A = new MsgConsumerA();
			msgConsumer_A.init().receiveMsg_A();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void callReceiveMsg_B() {
		MsgConsumerB msgConsumer_B = null;
		
		try {
			msgConsumer_B = new MsgConsumerB();
			msgConsumer_B.init().receiveMsg_B();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] argv) {
		//callSendMsg();
		callReceiveMsg_A();
		//callReceiveMsg_B();
	}

}
