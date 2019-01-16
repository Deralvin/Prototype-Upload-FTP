package id.co.company.pecellele.uploadimage;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
import java.nio.Buffer;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

public class FileTransfer {


    private static final String TAG = null;

        public boolean ftpConnect(String srcFilePath, String desFileName){
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
                boolean  status = ftpClient.storeFile("Bawaslu-Ftp-Testing/"+desFileName, bis);
                bis.close();
                return status;
            }
        } catch (SocketException e) {
            Log.d("FTP1", "Error: could not connect to socket " + e );
        } catch (IOException e) {
            Log.d("FTP2", "Error: could not connect to host " + e );
        }
        return false;
    }
    public boolean ftpJson(String srcFilePath, String desFileName){
        JsonParser jsonParser = new JsonParser();
        try {

            FTPClient ftpClient = new FTPClient();
            ftpClient.connect("ftp.pptik.id");
            if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
                boolean status1 = ftpClient.login("ftp.pptik.id|ftppptik","XxYyZz123!");
                ftpClient.enterLocalPassiveMode();
                Log.d("Connection success", "ftpConnect: berhasil status = "+status1);
            FileTransfer fa = new FileTransfer();


           OutputStream output = new FileOutputStream(srcFilePath);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(output);
            JSONObject obj = new JSONObject();
            obj.put("name", "mkyong.com");
            obj.put("age", new Integer(100));

            JSONArray list = new JSONArray();
            list.put("data");
            list.put("data2");

            obj.put("messages", list);

                Log.d("dataJsonman", "ftpJson: "+obj);
                outputStreamWriter.write(obj.toString());
                Log.d("dataJsonman1", String.valueOf(Environment.getDataDirectory()));

                FileInputStream fileInputStream = new FileInputStream(srcFilePath);
            BufferedInputStream bis = new BufferedInputStream(fileInputStream);

            boolean status = ftpClient.storeFile("Bawaslu-Ftp-Testing/"+desFileName,bis);
                bis.close();
                outputStreamWriter.close();
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