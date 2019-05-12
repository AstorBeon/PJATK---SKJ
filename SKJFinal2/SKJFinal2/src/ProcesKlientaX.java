import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

class ProcesKlientaX extends Thread {
    Socket socket;
    int clientNumber;
    BufferedReader in;
    PrintWriter out;
    List zNimiRozegralem = new ArrayList();
    List <Integer> rzuconeliczby = new ArrayList<>();  //używane tylko gdy gracz dołącza do gry
    Integer rzuconaLiczba=0;

    public ProcesKlientaX(Socket socket, int clientNumber) {
        this.socket = socket;
        this.clientNumber = clientNumber;
        System.out.println("New Client added: " + clientNumber + " Socket: " + socket);

        for(int i=0;i<FinalServerX.gracze.size();i++){

            rzuconeliczby.add((int)(Math.random()*100));
        }
        zNimiRozegralem=FinalServerX.gracze;

    }


    public void run() {
        try {


            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);



            while (true) {


                //SPRAWDZANIE ODPOWIEDZI!

                String input = in.readLine();


                if(input.equals("QUIT")) {
                    if (zNimiRozegralem.size() == FinalServerX.gracze.size()) {
                        FinalServerX.zamkniecie();
                        FinalServerX.gracze.remove(this);

                        return;

                    }
                }





            }
        } catch (IOException e) {
            System.out.println("Error with client: " + clientNumber + ": " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error while closing socket");
            }
            System.out.println("Disconnected " + clientNumber);
            FinalServerX.gracze.remove(this);
        } //dodać łapanie interrupted


    }


    public void losujLiczbe(){
        rzuconaLiczba=(int)(Math.random()*100);
    }




}


