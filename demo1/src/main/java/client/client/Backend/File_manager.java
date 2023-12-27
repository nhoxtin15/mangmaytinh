package client.client.Backend;

import java.io.DataOutputStream;
import java.io.File;
import java.util.Vector;


public class File_manager {
    static public void Add_the_file_to_the_List() throws Exception{
        for(int i=0;i<Backend_Client.List_of_Dir.size();i++){
            Vector<String> Files = new Vector<>();

            File[] arrOfFile = (new File(Backend_Client.List_of_Dir.elementAt(i))).listFiles();

            for(File File_Name : arrOfFile){
                if(File_Name.isDirectory()) continue;
                Files.add(File_Name.getName());

            }

            Backend_Client.List_of_File.add(Files);
        }
    }

    static public void Publish_to_Server() throws Exception{
        try{
            DataOutputStream sender = Backend_Client.communicate_output;
            //send publish
            sender.writeUTF("publish");


            //calculate the total message

            int sum =0;
            for(Vector<String> List : Backend_Client.List_of_File){
                sum += List.size();
            }

            //send the size
            sender.writeInt(sum);

            for(Vector<String> List:Backend_Client.List_of_File) {
                for (String Filename : List) {
                    sender.writeUTF(Filename);
                }
            }

        }
        catch (Exception e){
            throw e;
        }
    }




}



