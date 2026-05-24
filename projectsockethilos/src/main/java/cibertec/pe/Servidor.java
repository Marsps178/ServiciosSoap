package cibertec.pe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            Socket sc; 
            System.out.println("Servidor iniciado en el puerto 5000");

            while (true) {
                sc = server.accept();

                DataInputStream in = new DataInputStream(sc.getInputStream());
                DataOutputStream out = new DataOutputStream(sc.getOutputStream());

                out.writeUTF("Ingresar nombre cliente: ");
                String nombreCliente = in.readUTF();


                ServidorHilo hilo = new ServidorHilo(in, out, nombreCliente);
                hilo.start();

                System.out.println("Cliente conectado: " + nombreCliente);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
