package cibertec.pe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PUERTO = 5000;
        DataInputStream in;
        DataOutputStream out;

        try {
            Socket sc = new Socket(HOST, PUERTO);
               // Leer Flujo de entrada y salida
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());
    
                out.writeUTF("Hola desde el servidor");
                String mensaje = in.readUTF();
                System.out.println("Mensaje recibido: " + mensaje);
                sc.close();
        } catch (UnknownHostException e) {

            e.printStackTrace();
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }
}