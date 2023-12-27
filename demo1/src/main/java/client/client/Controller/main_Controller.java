package client.client.Controller;

import client.client.Backend.File_fetcher;
import client.client.Exception_Handler;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Vector;
import java.util.stream.Stream;

import client.client.Login;
import javafx.stage.Stage;
import client.client.Backend.Backend_Client;

public class main_Controller {

    @FXML
    TextArea Box_List_of_file;

    @FXML
    ChoiceBox<String> List_of_Download;

    public void Update(ActionEvent event) throws Exception{
        try{
            DataOutputStream messenger = Backend_Client.communicate_output;

            DataInputStream receiver = Backend_Client.comunicate_input;

            //message the server for updating

            //message the server for update
            messenger.writeUTF("update");
            int size = receiver.readInt(); // read the number of file

            //create new array
            String[] temp_list_file = new String[size];


            //String builder for display ing

            StringBuilder temp_Stringbuilder = new StringBuilder();

            for(int i=0;i<size;i++){
                temp_list_file[i] = receiver.readUTF();
                temp_Stringbuilder.append(temp_list_file[i]).append("\n");
            }



            //updating

            this.Box_List_of_file.setText(temp_Stringbuilder.toString());
            //this.List_of_Download = new ChoiceBox<>();
            this.List_of_Download.setItems(FXCollections.observableArrayList(temp_list_file));

        }

        catch (Exception e){
            //display the pop up
            Exception_Handler.Cannot_connect_to_Server Error = new Exception_Handler().new Cannot_connect_to_Server();
            Error.Display(false);

            Error.popup_window.setOnHidden(Hidden -> {
                try{
                    FXMLLoader loader1 = new FXMLLoader(Login.class.getResource("login.fxml"));
                    Parent login_root = loader1.load();
                    Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(login_root));
                    stage.show();
                }
                catch (Exception er){

                }
            });
            Error.popup_window.show();

        }
    }


    public void fetch(ActionEvent event) throws Exception{
        //get file
        //message the server for the file


        try{
            File_fetcher fetcher = new File_fetcher(this.List_of_Download.getValue());
            Thread fetcher_thread = new Thread(fetcher);
            fetcher_thread.start();
            DataOutputStream sender = Backend_Client.communicate_output;
            sender.writeUTF("send");
            sender.writeUTF(this.List_of_Download.getValue());




        }
        catch (Exception e){

        }


    }




}
