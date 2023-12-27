package client.client.Backend;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.Vector;


public class Backend_Client {

    public static Socket socket_communicating; // private channel
    public static DataInputStream comunicate_input;
    public static DataOutputStream communicate_output;


    public static Socket socket_sending; // public channel
    public static DataInputStream sending_input;
    public static DataOutputStream sending_output;



    public static Vector<String> List_of_Dir = new Vector<>();
    public static Vector<Vector<String>> List_of_File = new Vector<>();

    public static String fileStoringdir;

    public static Vector<String> List_of_ServerFile = new Vector<>();


    public static Processor processor = new Processor();






}
