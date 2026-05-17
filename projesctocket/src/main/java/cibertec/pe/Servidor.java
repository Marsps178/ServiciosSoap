package cibertec.pe;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {
    public static void main(String[] args) {
        ServerSocket servidor = null;
        Socket sc = null;
        DataInputStream in;
        DataOutputStream out;
        final int PUERTO = 5000;

        try {
            servidor = new ServerSocket(PUERTO);
            System.out.println("Servidor iniciado en el puerto " + PUERTO);
            while (true) {
                sc = servidor.accept();
                System.out.println("Cliente conectado: " + sc.getInetAddress().getHostName());
                in = new DataInputStream(sc.getInputStream());
                out = new DataOutputStream(sc.getOutputStream());
                String mensaje = in.readUTF();
                System.out.println("Mensaje recibido: " + mensaje);
                out.writeUTF("Hola desde el cliente");
                sc.close();
                System.out.println("Cliente desconectado: " + sc.getInetAddress().getHostName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (servidor != null) {
                    servidor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}