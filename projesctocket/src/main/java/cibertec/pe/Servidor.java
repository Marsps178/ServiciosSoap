package cibertec.pe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Servidor {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PUERTO = 5000;
        DataInputStream in;
        DataOutputStream out;

        try {
            Socket sc = new Socket(HOST, PUERTO);
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        System.out.println("Hello world!");
    }
}