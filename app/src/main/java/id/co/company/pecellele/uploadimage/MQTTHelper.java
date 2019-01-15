package id.co.company.pecellele.uploadimage;

import android.content.Context;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MQTTHelper {

    //define mqtt android client
    public MqttAndroidClient mqttAndroidClient;

    /**
     * Connection Parameter
     */
    final String serverUri = "tcp://167.205.7.226:1883";
    final String clientId = "Android_Client_" + android.os.Build.MODEL;
    final String subscriptionTopic = "/pemilu2018";
    final String username = "/pemilu:pemilu";
    final String password = "pemilu123";

    /**
     * Constructor for MQTT Helper
     * @param context android context
     */
    public MQTTHelper(Context context){

        /**
         * Define new MQTT Connection with callback
         */
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {

            /**
             * Connection Complete Callback
             * @param b
             * @param s
             */
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            /**
             * Connection Lost Callback
             * @param throwable throwable exception
             */
            @Override
            public void connectionLost(Throwable throwable) {

            }

            /**
             * Message Arrived callback
             * @param topic mqtt topic
             * @param mqttMessage mqtt message content
             * @throws Exception
             */
            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Mqtt", mqttMessage.toString());
            }

            /**
             * Delivery Complete callback
             * @param iMqttDeliveryToken
             */
            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });

        //connect now
        connect();
    }

    /**
     * Setup MQTT Callback
     * @param callback
     */
    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    /**
     * MQTT Connection function
     */
    private void connect(){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        try {

            /**
             * mqtt android client connection function
             */
            mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {

                /**
                 * if connection success run this
                 * @param asyncActionToken MQTT Token connection
                 */
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                    disconnectedBufferOptions.setBufferEnabled(true);
                    disconnectedBufferOptions.setBufferSize(100);
                    disconnectedBufferOptions.setPersistBuffer(false);
                    disconnectedBufferOptions.setDeleteOldestMessages(false);
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    subscribeToTopic();
                }

                /**
                 * if connection failed run this
                 * @param asyncActionToken MQTT Token Connection
                 * @param exception Throwable exception
                 */
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Failed to connect to: " + serverUri + exception.toString());
                }
            });

        } catch (MqttException ex){
            ex.printStackTrace();
        }
    }


    /**
     * Subscribe to Topic
     */
    private void subscribeToTopic() {
        try {

            /**
             * mqtt android client subscribe function
             */
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, new IMqttActionListener() {


                /**
                 * if subscribtion success run this
                 * @param asyncActionToken MQTT Token connection
                 */
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.w("Mqtt","Subscribed!");
                }

                /**
                 * if subscribtion failed run this
                 * @param asyncActionToken MQTT Token Connection
                 * @param exception Throwable exception
                 */
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.w("Mqtt", "Subscribed fail!");
                }
            });

        } catch (MqttException ex) {
            System.err.println("Exceptionst subscribing");
            ex.printStackTrace();
        }
    }

    public void publish(String topic_pub, String message) {
        try {
            mqttAndroidClient.publish(topic_pub, message.getBytes(),0,false);
        } catch (MqttException e) {
            e.printStackTrace();

        }
    }
}
