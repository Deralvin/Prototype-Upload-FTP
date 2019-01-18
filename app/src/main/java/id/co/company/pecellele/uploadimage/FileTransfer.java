package id.co.company.pecellele.uploadimage;


import java.io.BufferedInputStream;
import id.co.company.pecellele.uploadimage.MQTTHelper;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.nio.Buffer;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParser;


public class FileTransfer {

    Long tsLong = System.currentTimeMillis()/1000;
    MQTTHelper mqttHelper ;
    SendToRMQ sendToRMQ = new SendToRMQ();

    private static final String TAG = null;
        public boolean ftpConnect(String srcFilePath, String desFileName,String Imei){
        try {
            String mBitmap =null;
           FTPClient ftpClient = new FTPClient();
            ftpClient.connect("ftp.pptik.id");
            if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
                boolean status1 = ftpClient.login("ftp.pptik.id|ftppptik","XxYyZz123!");
                ftpClient.enterLocalPassiveMode();
                Log.d("Connection success", "ftpConnect: berhasil status = "+status1);

                FileInputStream srcFileStream = new FileInputStream(srcFilePath);
                BufferedInputStream bis = new BufferedInputStream(srcFileStream);
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                Gson gson = new Gson();
                String json = gson.toJson(srcFileStream);
                Log.d("FileName", srcFilePath);
                String ts = tsLong.toString();

                boolean  status = ftpClient.storeFile("Bawaslu-Ftp-Testing/"+ts+"_"+Imei+".jpg", bis);


                JSONObject obj = new JSONObject();
                obj.put("nama file",ts+

                        "_"+Imei+".jpg");
                obj.put("telephone","085224609423");
                obj.put("IMEI",Imei);
                obj.put("provinsi","32");
                obj.put("kabupaten","73");
                obj.put("kelurahan","02");
                obj.put("long","-6.87499");
                obj.put("lat","107.5281");
                String onjTo=obj.toString();

                if (status ==true){
                    sendToRMQ.sendRMQFan(onjTo);

                }else{
                    Log.d("RMQERROR", "ftpConnect: Error data RMQ");
                }
                bis.close();
                return status;
            }
        } catch (SocketException e) {
            Log.d("FTP1", "Error: could not connect to socket " + e );
        } catch (IOException e) {
            Log.d("FTP2", "Error: could not connect to host " + e );
        } catch (JSONException e) {
            Log.d("FTP3", "Error: could not connect to host " + e );
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
            return false;
    }
    public boolean ftpJson(String srcFilePath, String desFileName, File srcPathInternal){

        try {

            FTPClient ftpClient = new FTPClient();
            ftpClient.connect("ftp.pptik.id");
            if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
                boolean status1 = ftpClient.login("ftp.pptik.id|ftppptik","XxYyZz123!");
                ftpClient.enterLocalPassiveMode();
                Log.d("Connection success", "ftpConnect: berhasil status = "+status1);

                FileInputStream srcFileStream = new FileInputStream(srcFilePath);
                BufferedInputStream bis = new BufferedInputStream(srcFileStream);
                ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
                Gson gson = new Gson();
                String json = gson.toJson(srcFileStream);
                Log.d("FileName", srcFilePath);
                boolean  status = ftpClient.storeFile("Bawaslu-Ftp-Testing/"+desFileName, bis);
                bis.close();
                return status;
            }

//                fileWriter.write(obj.toString());
//                Toast.makeText(PreviewCapture.this, "Data ini = "+fileWriter, Toast.LENGTH_SHORT).show();
//
////                fas= fa.ftpJson(fileWriter, "data.json");
//                fileWriter.flush();

        }catch (Exception e){
            Log.d("jsonwrong", "doInBackground: "+e);
        }

        return false;
    }


    /*fileupload*/
    public boolean ftpUpload(String srcFilePath, String desFileName)
    {
        boolean status = false;
        try {
            FTPClient mFTPClient = new FTPClient();
            //File f=new File("D:/img/abc.jpeg");
            FileInputStream srcFileStream = new FileInputStream(srcFilePath);
            // change working directory to the destination directory

            status = mFTPClient.storeFile(desFileName, srcFileStream);
            Log.d("mv dir : ", String.valueOf(status));


            srcFileStream.close();
            Log.d("Status DATA : ", String.valueOf(status));
            return status;
        } catch (Exception e) {
            Log.d("Gagal Upload", "upload failed"+e);
        }

        return status;
    }
//    public boolean ftpChangeDirectory(String directory_path)
//    {
//        try {
//            mFTPClient.changeWorkingDirectory(directory_path);
//        } catch(Exception e) {
//            Log.d(TAG, "Error: could not change directory to " + directory_path);
//        }
//
//        return false;
//    }

}