package id.co.company.pecellele.uploadimage;


import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import android.util.Log;

public class FileTransfer {

    public FTPClient mFTPClient = new FTPClient();
    private static final String TAG = null;

    public boolean ftpConnect(String host,String username,String password,int port)
    {
        try {
            // FTPClient mFTPClient = new FTPClient();
            // connecting to the host
            mFTPClient.connect(host, port);
            boolean status = mFTPClient.login(username, password);
            // now check the reply code, if positive mean connection success

                /* Set File Transfer Mode
                 *
                 * To avoid corruption issue you must specified a correct
                 * transfer mode, such as ASCII_FILE_TYPE, BINARY_FILE_TYPE,
                 * EBCDIC_FILE_TYPE .etc. Here, I use BINARY_FILE_TYPE
                 * for transferring text, image, and compressed files.
                 */
                mFTPClient.setFileType(FTP.BINARY_FILE_TYPE);
                mFTPClient.enterLocalPassiveMode();

                return status;

        } catch(Exception e) {
            Log.d(TAG, "Error: could not connect to host " + host );
        }

        return false;
    }
    /*fileupload*/
    public boolean ftpUpload(String srcFilePath, String desFileName,
                             String desDirectory)
    {
        boolean status = false;
        try {
            //File f=new File("D:/img/abc.jpeg");
            FileInputStream srcFileStream = new FileInputStream(srcFilePath);
            // change working directory to the destination directory

            status = mFTPClient.storeFile(desFileName, srcFileStream);
            Log.d("mv dir : ", String.valueOf(status));


            srcFileStream.close();
            Log.d("Status DATA : ", String.valueOf(status));
            return status;
        } catch (Exception e) {
            Log.d(TAG, "upload failed");
        }

        return status;
    }
    public boolean ftpChangeDirectory(String directory_path)
    {
        try {
            mFTPClient.changeWorkingDirectory(directory_path);
        } catch(Exception e) {
            Log.d(TAG, "Error: could not change directory to " + directory_path);
        }

        return false;
    }

}