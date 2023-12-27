package client.client.Controller;

import client.client.Backend.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;

import javafx.stage.Stage;

import client.client.Exception_Handler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;


public class Login_Controller {
    @FXML
    TextField IP;

    @FXML
    TextArea List_of_dir;

    @FXML
    TextField Current_dir;


    @FXML
    TextField Storing_dir;


    public void Connect_to_server(ActionEvent e) throws Exception{
        //get the Ip
        java.lang.String Server_IP = IP.getText();
        boolean Can_connect_to_Server = true;


        //try to connect to the server
        try{
            //we set the default IP
            if(!Server_IP.equals("next")){
                //open socket to server
                Backend_Client.socket_communicating = new Socket(Server_IP,4333); //communicate to server (private channel)
                //read 1 message before cotinueing
                try{
                    Backend_Client.comunicate_input = new DataInputStream(Backend_Client.socket_communicating.getInputStream());
                    Backend_Client.communicate_output = new DataOutputStream(Backend_Client.socket_communicating.getOutputStream());
                    System.out.println(Backend_Client.comunicate_input.readUTF());
                }
                catch (Exception doi){

                }

                Backend_Client.socket_sending = new Socket(Server_IP, 4334); //waiting for sending (public channel)
                Backend_Client.sending_input = new DataInputStream(Backend_Client.socket_sending.getInputStream());
                Backend_Client.sending_output = new DataOutputStream(Backend_Client.socket_sending.getOutputStream());
            }
        }

        catch (Exception exception_connect){
            new Exception_Handler().new Cannot_connect_to_Server().Display(true);
            Can_connect_to_Server = false;
        }



        if(Can_connect_to_Server){
            try{
                if(!Server_IP.equals("next")){
                    File_manager.Add_the_file_to_the_List();
                    File_manager.Publish_to_Server();
                    Thread processor = new Thread(Backend_Client.processor);
                    processor.start();
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/client/main.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();

                stage.setScene(new Scene(root));
                stage.show();
            }
            catch (Exception exception_file_managing){
                //clear file
                //send message to the server -> clear (for the server to close)

                try{

                    //send clear to the server
                    Backend_Client.communicate_output.writeUTF("clear");
                    System.out.println("clear");
                }
                finally {
                    //clear the file

                    Backend_Client.List_of_Dir.clear();
                    Backend_Client.List_of_File.clear();


                    this.List_of_dir.setText("");
                    this.Current_dir.setText("");
                    //popup
                    new Exception_Handler().new Cannot_connect_to_Server().Display(true);

                }
            }
        }




    }

    public void choose_dir_to_share(ActionEvent chooseFileButton){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Open Resource Directory");

        Stage File_choosing_popUp = new Stage();
        // Show open file dialog
        File selectedFile = directoryChooser.showDialog(File_choosing_popUp);

        if(selectedFile != null) this.Current_dir.setText(selectedFile.getPath());

    }

    public void choose_StoringDir(ActionEvent chooseStoringdirButton){
        DirectoryChooser StoringdirChooser = new DirectoryChooser();
        StoringdirChooser.setTitle("Choose Storing directory");

        Stage Storing_dir_popup = new Stage();

        File selectedStoringFile = StoringdirChooser.showDialog(Storing_dir_popup);

        if(selectedStoringFile != null) {
            this.Storing_dir.setText(selectedStoringFile.getPath());
            Backend_Client.fileStoringdir = selectedStoringFile.getPath();
        }
    }



    public void setAddDir(ActionEvent addDir) throws Exception{
        String dirToAdd = this.Current_dir.getText();
        //check the dir
        try{
            for(int i=0; i<Backend_Client.List_of_Dir.size();i++){
                if(Backend_Client.List_of_Dir.elementAt(i).equals(dirToAdd)){
                    throw new Exception_Handler().new TheSameFile();
                }
            }
        }
        catch (Exception_Handler.TheSameFile e){
            e.Display(true);
            this.Current_dir.setText("");
            return;

        }
        this.List_of_dir.setText(this.List_of_dir.getText()  + Current_dir.getText() +"\n") ;
        Backend_Client.List_of_Dir.add(dirToAdd);
        this.Current_dir.setText("");
    }

}