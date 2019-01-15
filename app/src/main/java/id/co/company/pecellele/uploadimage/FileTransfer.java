package id.co.company.pecellele.uploadimage;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;

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