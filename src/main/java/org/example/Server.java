package org.example;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server extends Thread
{
    private static int[][] matrix = new int[3][3];
    private Socket clientAccepted;
    public Server(Socket clientAccepted)
    {
        this.clientAccepted=clientAccepted;
    }

    public static void main(String[] arg) throws IOException {
        ServerSocket serverSocket = null;
        Socket clientAccepted     = null;//объявление объекта класса Socket

        System.out.println("server starting....");
        serverSocket = new ServerSocket(2525);//создание сокета сервера для //заданного порта
        try {
            while (true) {
                clientAccepted = serverSocket.accept();//выполнение метода, который //обеспечивает реальное подключение сервера к клиенту
                System.out.println("connection established....");
//создание потока ввода
                Server server = new Server(clientAccepted);
                server.start();
            }
        }
        finally {
                clientAccepted.close();//закрытие сокета, выделенного для клиента
                serverSocket.close();//закрытие сокета сервера
            }

    }
    @Override
    public void run()
    {//объявление объекта класса ServerSocket
        ObjectInputStream  sois   = null;//объявление байтового потока ввода
        ObjectOutputStream soos   = null;//объявление байтового потока вывода
        try {
            sois = new ObjectInputStream(clientAccepted.getInputStream());
            soos = new ObjectOutputStream(clientAccepted.getOutputStream());//создание потока//вывода
            //String clientMessageRecieved=null;//объявление //строки и присваивание ей данных потока ввода, представленных
            Array array=new Array();
            while(true)
            {
                array.GetMatrixFromClient(sois);
                array.PrintArray("Matrix");
                array.GetInverseMatrix();
                array.PrintArray("Inverse matrix");
                array.SendMatrixToClient(soos);
            }
        }catch(Exception e)  {
        } finally {
            try {
                sois.close();//закрытие потока ввода
                soos.close();//закрытие потока вывода
            } catch(Exception e) {
                e.printStackTrace();//вызывается метод исключения е
            }
        }
    }
}
