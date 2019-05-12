import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

import static java.lang.Thread.sleep;


public class FinalClient {

    public BufferedReader in;
    public PrintWriter out;

    Socket socket;


    public FinalClient() {



    }


    public void connectToServer() throws IOException, InterruptedException {


        socket = new Socket("localhost", 9999);

        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);





        while(true){
            String odpowiedz = in.readLine();


            System.out.println("Server: "+ odpowiedz);
            if(!odpowiedz.equals("")) {

                // System.out.println(odpowiedz);


            }

            //Wyjście z gry za 10 sec.
            sleep(10000);


            out.println("QUIT");
        }

    }


    public static void main(String[] args) throws Exception {
        FinalClient client = new FinalClient();

        client.connectToServer();




    }





}