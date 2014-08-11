package rpc.topic;

import java.io.IOException;

import com.rabbitmq.client.Channel;

public class Test {

	public void doWorks() {
		Thread send = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				callSendMsg();
			}

		});
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				callReceiveMsg_A();
			}

		});
		Thread t4 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				callReceiveMsg_D();
			}

		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				callReceiveMsg_B();
			}

		});
		Thread t3 = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				callReceiveMsg_C();
			}

		});
		send.start();
		t1.start();
		t4.start();
		t2.start();
		t3.start();

	}

	public static void main(String[] argv) {
		Test test = new Test();
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
			Thread.sleep(1);
			msgConsumer_D = new MsgConsumerD_sub();
			msgConsumer_D.init().receiveMsg_D();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (msgConsumer_D != null) {
				msgConsumer_D.closeConnection();
			}
		}
	}

}
