package lyratest;

import net.jodah.lyra.ConnectionOptions;
import net.jodah.lyra.Connections;
import net.jodah.lyra.config.Config;
import net.jodah.lyra.config.ConfigurableChannel;
import net.jodah.lyra.config.ConfigurableConnection;
import net.jodah.lyra.config.RecoveryPolicies;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;


public class RecoveryTest {
	private static RecoveryTest test;

    private String EXCHANGE_NAME = "ORDER_UPDATES";
    private String EXCHANGE_HOST = "rvy";
    private String EXCHANGE_USER = "recover";
    private String EXCHANGE_PASS = "recover";
    private String QUEUE = "TEST_UPDATES";
    private String TOPIC = "*.#";

    private final Config config = new Config().withRecoveryPolicy(RecoveryPolicies.recoverAlways());;
    private Channel channel;
    private Connection connection;
    private ConnectionFactory factory;
    private ConnectionOptions options;
    private QueueingConsumer consumer;
    
    public RecoveryTest() throws Exception {
        
//        options = new ConnectionOptions().withAddresses(EXCHANGE_HOST);   
        options = new ConnectionOptions().withHost(EXCHANGE_HOST);
        factory = options.getConnectionFactory();
        factory.setUsername(EXCHANGE_USER);
        factory.setPassword(EXCHANGE_PASS);
        
        connection = Connections.create(options, config);
        
        ConfigurableConnection RecoveryConnection = Config.of(connection);
        ConfigurableChannel configurableChannel = Config.of(channel);
        
        channel = RecoveryConnection.createChannel();
        channel.queueDeclare(QUEUE, true, false, false, null);
        channel.queueBind(QUEUE, EXCHANGE_NAME, TOPIC);

        consumer = new QueueingConsumer(channel);
        boolean autoAck = true;
        channel.basicConsume(QUEUE, autoAck, consumer);
  }
    
    public void run() { 
        QueueingConsumer.Delivery delivery = null;   
        System.out.println("Starting Test Run");
        while(true){           
            try {              
                delivery = consumer.nextDelivery();
            } catch (Exception ex) {
                  System.out.println("Somthing went wrong ..." + ex.getLocalizedMessage());
            }     
            byte [] msg = delivery.getBody();
            System.out.println(String.valueOf(new String(msg)));
            try {
                System.out.println("Lets take a rest");
                Thread.sleep(8000);
            } catch (InterruptedException ex) {
               System.out.println("We have been intrupted");
            }
              
        }
    } 
    public static void main(String[] args) throws Exception  {
        test = new RecoveryTest();
        test.run();
    }
}
