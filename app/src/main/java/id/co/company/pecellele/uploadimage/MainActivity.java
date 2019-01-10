package id.co.company.pecellele.uploadimage;


import java.io.File;
import java.io.FileInputStream;

//import org.apache.commons.net.ftp.FTPClient;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    protected static final int SELECT_PICTURE = 0;
    public static String var;
    TextView textTargetUri;
    //public FTPClient mFTPClient = null;
    boolean status = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button buttonLoadImage = (Button)findViewById(R.id.loadimage);
        textTargetUri = (TextView)findViewById(R.id.targeturi);

        buttonLoadImage.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View arg0) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Click Picture To Export"), SELECT_PICTURE);

            }});
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            Uri targetUri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA };
            Cursor cursor = managedQuery(targetUri, projection, null, null, null);
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            //return cursor.getString(column_index);
            textTargetUri.setText(cursor.getString(column_index));
            String var=cursor.getString(column_index);
            // textTargetUri.setText("connected"+var);
            textTargetUri.setText(var);
            String img=var.substring(12);


            try{
                FileTransfer fr =new FileTransfer();
                /*Toast.makeText(getApplicationContext(),
                        "ftp connected",
                        Toast.LENGTH_LONG).show();*/
                fr.ftpConnect("10.5.30.204","test","test",21);

                Toast.makeText(getApplicationContext(),
                        img,
                        Toast.LENGTH_LONG).show();

                try{
                    fr.ftpUpload(img,"testingOPM.jpg","/");

                    Toast.makeText(getApplicationContext(),
                            "image uploaded to the server",
                            Toast.LENGTH_LONG).show();

                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(),
                            "exception for upload",
                            Toast.LENGTH_LONG).show();
                }
            }
            catch(Exception es){
                Toast.makeText(getApplicationContext(),
                        "server",
                        Toast.LENGTH_LONG).show();
            }
            // System.out.println("path:"+var);

/* boolean tempStatus = false;
  String desFileName = "" ;
  FileInputStream srcFileStream = null;*/


            //textTargetUri.setText("uploaded");
            //File f = new File(var);
            //textTargetUri.setText("uploaded"+var);
            //desFileName = "myUploadFile.JPEG";
/*  try
  {
  srcFileStream = new FileInputStream(f);
  //textTargetUri.setText("uploaded"+var);

  }
  catch (Exception e)
  {
  e.toString();
  e.printStackTrace();
  }

 // mFTPClient = new FTPClient();
 // mFTPClient.connect("xx.xx.xx.xx");

  SimpleFTPClient a = new SimpleFTPClient ();
  a.setHost("xx.xx.xx.xx");
  //textTargetUri.setText("connected"+var);
 // FTPClient client = new FTPClient();
  try{
 //* client.connect("xx.xx.xx.xx");
  //textTargetUri.setText("connected"+var);
  }
  catch(Exception e){}
  a.setUser("user");
  a.setPassword("pass");
    boolean connected=a.connect();

    if ( connected){

        System.out.println("connected");
      // Upload a file from your local drive, lets say in â€œc:/ftpul/u.txtâ€�
   /* if (a.uploadFile(var))
      {
        textTargetUri.setText("connected"+var);

      System.out.println(a.getLastSuccessMessage ());
      }
      else
          textTargetUri.setText("connected1"+var);
        System.out.println(a.getLastErrorMessage ());
    }*/

            // a.uploadFile(var);

            //textTargetUri.setText("connected"+var);


        }

        Toast.makeText(getApplicationContext(),
                "finally uploaded to the server",
                Toast.LENGTH_LONG).show();
    }

}