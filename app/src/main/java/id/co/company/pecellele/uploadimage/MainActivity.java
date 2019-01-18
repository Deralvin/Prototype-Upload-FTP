package id.co.company.pecellele.uploadimage;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    private String csv_download_url = "http://filehosting.pptik.id/Bawaslu-Ftp-Testing/32/73/02/3273021547479080_990000862471858_351756051523997.csv";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.photostream);

        /**
         * Inititate Recycle View
         */
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

        // Button Capture Camera
        btnCapturePicture = findViewById(R.id.btnPhoto);

        /**
         * Download Images Task
         */
        DownloadTask dt=new DownloadTask();
        dt.execute(csv_download_url);


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
    }

    /**
     * Downloader Task
     */
    private class DownloadTask extends AsyncTask<String,Integer,Void> {
        BufferedReader buffer;

        /**
         * Download Task
         * @param params
         * @return
         */
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

        /**
         * Execute after download
         * @param result
         */
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

    /**
     * Download From CSV and put to Container
     * @param in Buffered Reader from downloading CSV
     * @throws IOException
     */
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

}