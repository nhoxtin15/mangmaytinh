package client.client.Backend;

import client.client.Exception_Handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class File_fetcher implements Runnable{
    String filename;

    public File_fetcher(String filename){
        this.filename = filename;
    }


    @Override
    public void run() {
        try{
            ServerSocket serverSocket = new ServerSocket(5666);
            Socket receiver = serverSocket.accept();
            DataInputStream receiver_Stream = new DataInputStream(receiver.getInputStream());


            int size = receiver_Stream.readInt();
            System.out.println(size);
            byte[] buffer = new byte[size];

            receiver_Stream.read(buffer,0,size);


            try(FileOutputStream file_Writer = new FileOutputStream(Backend_Client.fileStoringdir+"/" + this.filename)){
                //write to the file
                file_Writer.write(buffer,0,size);
                System.out.println("write file: "+Backend_Client.fileStoringdir+"/" + this.filename);
            }
            catch (Exception e){
                System.out.println("cannot write file");
                new Exception_Handler().new Cannot_write_file().Display(true);
            }
            serverSocket.close();
        }
        catch (Exception cannot_fetch){
            System.out.println("cannot fee file");
            try{
                new Exception_Handler().new Cannot_fetch_file().Display(true);
            }
            catch(Exception e1){

            }

        }
    }
}
