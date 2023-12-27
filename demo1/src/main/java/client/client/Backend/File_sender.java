package client.client.Backend;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

public class File_sender implements Runnable{
    public String status;
    public String Hostname;
    public String filedir;

    public String filename;


    public File_sender(String receiver,String filedir,String filename){
        this.Hostname = receiver;
        status = "ocay";
        this.filedir = filedir;
        this.filename = filename;
    }

    @Override
    public void run() {
        //send file
        try {
            Socket socket = new Socket(Hostname,5666);
            DataOutputStream sender = new DataOutputStream(socket.getOutputStream());
            FileInputStream Readfile = new FileInputStream(filedir+"/"+filename);

            System.out.println("Sending the file: "+filename);

            //read length
            int size =(int) new File(filedir+"/"+filename).length();
            System.out.println("size sending: "+size);
            //send the size
            sender.writeInt(size);
            //create buffer for storing the file
            byte[] buffer = new byte[size];
            //reading the file into buffer
            Readfile.read(buffer,0,size);
            //sending to the client.
            sender.write(buffer,0,size);

        }
        catch (Exception e) {
            this.status = "Error";
            e.printStackTrace();
        }
    }
}
