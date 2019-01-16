package id.co.company.pecellele.uploadimage;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.sql.Wrapper;

public class MainActivity extends AppCompatActivity {


    private Button btnCapturePicture,testButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Checking availability of the camera
        if (!CameraUtils.isDeviceSupportCamera(getApplicationContext())) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device doesn't have camera
            finish();
        }

        btnCapturePicture = findViewById(R.id.btnCapturePicture);
        testButton = findViewById(R.id.testFolderFile);

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

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File rootFile = getApplicationContext().getFilesDir();
                Toast.makeText(MainActivity.this,  String.valueOf(rootFile), Toast.LENGTH_SHORT).show();
            }
        });
        /**
         * Record video on button click
         */


        // restoring storage image path from saved instance state
        // otherwise the path will be null on device rotation
    }

}