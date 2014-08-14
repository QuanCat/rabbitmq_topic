package rpc.topic;

import java.io.IOException;

import rpc.topic.client.MsgConsumerA;
import rpc.topic.client.MsgConsumerB;
import rpc.topic.client.MsgConsumerC;
import rpc.topic.client.MsgConsumerD_sub;
import rpc.topic.client.MsgProducer;
import rpc.topic.client.randqueue.MsgConsumerE;
import rpc.topic.message.JsonToJava;
import rpc.topic.server.Srv;

import com.rabbitmq.client.Channel;

public class Test {
	
	public void doSrvWorks() {	
		callSendMsg();
	}

	public void doWorks() {

		Thread t1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				callReceiveMsg_A();
			}

		});
		Thread t4 = new Thread(new Runnable() {

			@Override
			public void run() {
			/*	try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				callReceiveMsg_D();
			}

		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				callReceiveMsg_B();
			}

		});
		Thread t3 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				callReceiveMsg_C();
			}

		});
		Thread t5 = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				callReceiveMsg_E();
			}

		});
		

		//t1.start();
		//t2.start();
		//t3.start();
		t4.start();
		t5.start();
		try {
			//t1.join();
			//t2.join();
			//t3.join();
			t4.join();
			t5.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] argv) {
		Test test = new Test();
		test.doSrvWorks();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.doWorks();
	}

	public void callSendMsg() {

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
		} finally {
			if (srv != null) {
				srv.closeConnection();
			}
		}

	}

	public void callReceiveMsg_A() {

		MsgConsumerA msgConsumer_A = null;
		try {
			Thread.sleep(1);
			msgConsumer_A = new MsgConsumerA();
			msgConsumer_A.init().receiveMsg_A();

		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (msgConsumer_A != null) {
				msgConsumer_A.closeConnection();
			}

		}

	}

	public void callReceiveMsg_B() {

		MsgConsumerB msgConsumer_B = null;

		try {
			msgConsumer_B = new MsgConsumerB();
			msgConsumer_B.init().receiveMsg_B();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (msgConsumer_B != null) {
				msgConsumer_B.closeConnection();
			}
		}

	}

	public void callReceiveMsg_C() {

		MsgConsumerC msgConsumer_C = null;

		try {
			msgConsumer_C = new MsgConsumerC();
			msgConsumer_C.init().receiveMsg_C();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (msgConsumer_C != null) {
				msgConsumer_C.closeConnection();
			}
		}

	}

	public void callReceiveMsg_D() {

		MsgConsumerD_sub msgConsumer_D = null;

		try {
			msgConsumer_D = new MsgConsumerD_sub();
			msgConsumer_D.init().receiveMsg_D();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (msgConsumer_D != null) {
				msgConsumer_D.closeConnection();
			}
		}
	}
	public void callReceiveMsg_E() {

		MsgConsumerE msgConsumer_E = null;

		try {
			msgConsumer_E = new MsgConsumerE();
			msgConsumer_E.init().receiveMsg_E();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (msgConsumer_E != null) {
				msgConsumer_E.closeConnection();
			}
		}
	}

}
