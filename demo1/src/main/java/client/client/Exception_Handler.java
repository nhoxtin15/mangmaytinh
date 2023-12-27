package client.client;


import client.client.Controller.Exception_Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Exception_Handler {
    public  class Popup extends  Exception{
        public String Error_Name;
        public String Error_Description;

        public Popup(String Error_Name, String Error_Description){
            super();
            this.Error_Name = Error_Name;
            this.Error_Description = Error_Description;
        }
        public Stage popup_window;

        public void Display(boolean showing) throws Exception{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("popup.fxml"));
            Parent root = loader.load();
            Exception_Controller controller = loader.getController();
            controller.Error_name.setText(this.Error_Name);
            controller.Error_description.setText(this.Error_Description);

            popup_window = new Stage();
            popup_window.setTitle(this.Error_Name);
            popup_window.setScene(new Scene(root));
            if(showing) popup_window.show();
        }

    }




    public class Cannot_connect_to_Server extends Popup{

        public Cannot_connect_to_Server(){
            super("Cannot connect to the Server",
                    "Either the connection is lost or the HostIP is invalid.\n" +
                    "Redirecting to the login page now!");
        }
    }

    public class TheSameFile extends Popup{
        public TheSameFile(){
            super("You've already added this file!!",
                    "Please choose again!");
        }
    }

    public class Cannot_fetch_file extends Popup{
        public Cannot_fetch_file(){
            super("Cannot fetch file",
                    "The list of file is out-dated." +
                            "\nPlease update the list and try again!"
            );
        }
    }

    public class Cannot_write_file extends Popup{
        public Cannot_write_file(){
            super("Cannot write file","Error happen when trying to write file");
        }
    }

    public class Already_have extends Popup {
        public Already_have(){
            super("You already have this file!","Please download alnother file");
        }
    }





    public class downloading extends Popup{
        public downloading(){
            super("Downloading the file","The file is being fetched, Please wait!");
        }
    }

    public class done_Download extends Popup{
        public done_Download(){
            super("The download is success","The file is successfully fetched to your local directory.\nPlease check the file!");
        }
    }












}
