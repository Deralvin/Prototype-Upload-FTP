package id.co.company.pecellele.uploadimage;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.opencsv.CSVReader;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import id.co.company.pecellele.uploadimage.models.Post;
import id.co.company.pecellele.uploadimage.view_models.PostAdapter;

public class MainActivity extends AppCompatActivity {



    private FloatingActionButton btnCapturePicture;
    MQTTHelper mqttHelper;

    private List<Post> postList = new ArrayList<>();
    private RecyclerView recyclerView;

    private PostAdapter pAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.photostream);

        pAdapter = new PostAdapter(postList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(pAdapter);



        // Checking availability of the camera
        if (!CameraUtils.isDeviceSupportCamera(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device doesn't have camera
            finish();
        }


        btnCapturePicture = findViewById(R.id.btnPhoto);

        DownloadTask dt=new DownloadTask();
        dt.execute("http://filehosting.pptik.id/Bawaslu-Ftp-Testing/32/73/02/3273021547479080_990000862471858_351756051523997.csv");


        /**
         * Capture image on button click
         */
        btnCapturePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
             Intent intent = new Intent(MainActivity.this,PreviewCapture.class);
             startActivity(intent);
            }
        });


        /**
         * Record video on button click
         */


        // restoring storage image path from saved instance state
        // otherwise the path will be null on device rotation
    }

    private class DownloadTask extends AsyncTask<String,Integer,Void> {
        BufferedReader buffer;

        protected Void doInBackground(String...params){
            URL url;
            try {
                url = new URL(params[0]);
                buffer = new BufferedReader(new InputStreamReader(url.openStream()));
            }catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result){
            try {
                Log.d("execute", "try");
                fillData(buffer);
            } catch (IOException e) {
                Log.d("execute", "error" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void fillData(BufferedReader in) throws IOException {

        CSVReader reader = new CSVReader(in);
        Post post;
        String[] nextRecord;
        while ((nextRecord = reader.readNext()) != null) {
            post = new Post("http://filehosting.pptik.id/Bawaslu-Ftp-Testing/" + nextRecord[0],
                    nextRecord[1],
                    nextRecord[3],
                    nextRecord[4],
                    nextRecord[5],
                    nextRecord[8]
            );
            postList.add(post);
        }
        pAdapter.notifyDataSetChanged();
    }

    void startMQTT() {
        mqttHelper = new MQTTHelper(getApplicationContext());
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {

            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w("Debug",mqttMessage.toString());
                Toast.makeText(getApplicationContext(),mqttMessage.toString(),Toast.LENGTH_LONG);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
    }

}