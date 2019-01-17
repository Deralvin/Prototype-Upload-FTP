package id.co.company.pecellele.uploadimage;

import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class SendToRMQ {
    public  void  sendRMQFan(String message) throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException, IOException, TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://pemilu:pemilu123@167.205.7.226");
        factory.setVirtualHost("/pemilu");
        //factory.setUri("amqp://guest:guest@localhost");
        factory.setConnectionTimeout(3000000);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        //send message TO RMQ

            String messageOn = message ;
            channel.basicPublish("amq.topic","pemilu2018",null,messageOn.getBytes());
            //System.out.println("published mesasge"  + messageOn);



        //FAN 2

    }

    public void SendSpeed() throws InterruptedException {
        Thread.sleep(500); //0.5 sec
    }

}
