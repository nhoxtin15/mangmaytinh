package client.client.Backend;

import client.client.Exception_Handler;

import java.io.DataInputStream;

public class Processor implements Runnable {

    public boolean stop;
    public String status ;

    public Processor(){
        this.stop = false;
        this.status = "ocay";
    }


    @Override
    public void run() {
        //wait to reiveive mesage.
        //the mesage is consist of Hostname + file name;
        try{
            System.out.println("processor is running");
            DataInputStream sending_wait = Backend_Client.sending_input;

            String message = sending_wait.readUTF();
            System.out.println(message);

            //check message
            while(status.equals("ocay")){

                if(message.equals("send")){
                    //read the file name and receiver IP

                    String filename = sending_wait.readUTF();
                    String Receiver_Ip= sending_wait.readUTF();
                    //process

                    //find the file dir

                    int i= 0;
                    boolean isFound = false;

                    for(;i<Backend_Client.List_of_File.size();i++){
                        if(Backend_Client.List_of_File.elementAt(i).contains(filename)){

                            isFound = true;
                            break;
                        }
                    }

                    //the file dir is at i


                    String filedir = Backend_Client.List_of_Dir.elementAt(i);


                    //initiate the sender

                    File_sender sender = new File_sender(Receiver_Ip,filedir,filename);
                    Thread sender_thread = new Thread(sender);

                    //run the sender
                    sender_thread.start();

                    //check the status


                }

                else if(message.equals("send error")){
                    //pop up the send error
                    new Exception_Handler().new Cannot_fetch_file().Display(true);
                }

                //read the next message
                message = sending_wait.readUTF();
            }



        }
        catch (Exception e){
            this.status = "not good";
        }
    }
}
