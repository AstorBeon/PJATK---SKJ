import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


public class FinalServerX {

    static ArrayList<ProcesKlientaX> gracze = new ArrayList<>();
    static ProcesKlientaX nowyGracz;
    static String wynikiDoHtml="";
    static FileWriter fw;
    static boolean czyMogeSieZamknac = false;
    static ServerSocket ThisServerSocket;

    static {
        try {
            fw = new FileWriter("WielkiTurniej.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static File htmlfile = new File("WielkiTurniej.html");

    public FinalServerX() throws IOException {
    }


    public static void main(String[] args) throws Exception {
        //OknoPolecen op = new OknoPolecen();


        //Plik html


        Desktop.getDesktop().browse(htmlfile.toURI());  //Plik html

        String html1 = "<!DOCTYPE html>" +
                "<html>" +
                "<body>" +
                "<h1> WIELKI TURNIEJ </h1>";

        String html2 = "</body>" +
                "</html> ";

        fw.write(html1+ wynikiDoHtml + html2);

        fw.flush();


        //Koniec html






        System.out.println("Server is running...");

        int clientNumber = 0; //poczatkowo
        //ServerSocket
        ServerSocket ConnectListener = new ServerSocket(9999);
        ThisServerSocket = ConnectListener;
        //PORT 9999
        try {
            while (true) {
                ProcesKlientaX temp = new ProcesKlientaX(ConnectListener.accept(), clientNumber++);
                czyMogeSieZamknac=true;
                BufferedReader in = new BufferedReader(new InputStreamReader(temp.socket.getInputStream()));
                PrintWriter out = new PrintWriter(temp.socket.getOutputStream(), true);


                nowyGracz=temp;
                temp.start();
                out.println("Witaj na serwerze do gry w marynarzyka \n" +
                        "Obecnych jest: " + gracze.size() + " graczy.");


                for(int i=0;i<gracze.size();i++) {


                    gracze.get(i).losujLiczbe();


                }


                if(gracze.size()>1) {
                    System.out.println("Wyniki gry:");
                }
                for(int i=0;i<gracze.size();i++){
                    //  System.out.println("SPRAWDZAMY WYNIKI!     "+ nowyGracz.rzuconeliczby.size() + "    ilosc graczy: "+ gracze.size());
                    int serverRandomizer =(int)(Math.random()*2);
                    if(nowyGracz.rzuconeliczby.get(i)*gracze.get(i).rzuconaLiczba+serverRandomizer%2==0){
                        System.out.println(nowyGracz.socket.getPort() + " rzuca: " + nowyGracz.rzuconeliczby.get(i)+ " vs "+gracze.get(i).socket.getPort() +" rzuca: " + gracze.get(i).rzuconaLiczba + "  Wygrywa gracz: " + nowyGracz.socket.getPort());
                        wynikiDoHtml+="<p>"+nowyGracz.socket.getPort() + " rzuca: " + nowyGracz.rzuconeliczby.get(i)+ " vs "+gracze.get(i).socket.getPort() +" rzuca: " + gracze.get(i).rzuconaLiczba + "  Wygrywa gracz: " + nowyGracz.socket.getPort()+"</p>";
                    }else{
                        System.out.println("Wygrywa gracz: "+ gracze.get(i).socket.getPort());
                        wynikiDoHtml+="<p>"+nowyGracz.socket.getPort() + " rzuca: " + nowyGracz.rzuconeliczby.get(i)+ " vs "+gracze.get(i).socket.getPort() +" rzuca: " + gracze.get(i).rzuconaLiczba + "  Wygrywa gracz: " + gracze.get(i).socket.getPort()+"</p>";
                    }

                }


                FileOutputStream writer = new FileOutputStream("WielkiTurniej.html");
                writer.write(("").getBytes());
                writer.close();

                fw.write(html1+ wynikiDoHtml + html2);
                fw.flush();




                gracze.add(temp);
                //temp.start();
                System.out.println("ROZMIAR TABLICY: " + gracze.size());



            }
        }catch(SocketException se){
            System.out.println("SERWER ZAMKNIĘTY!");
            return;
        }
        finally {
            ConnectListener.close();
        }


    }




    public static void zamkniecie() throws IOException {

        if(gracze.size()==1 && czyMogeSieZamknac){
            ThisServerSocket.close();
        }
    }


}



