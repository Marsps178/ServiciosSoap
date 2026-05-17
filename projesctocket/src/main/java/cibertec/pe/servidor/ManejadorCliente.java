package cibertec.pe.servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ManejadorCliente implements Runnable {

    private final Socket socket;
    private final Servidor servidor;
    private DataInputStream in;
    private DataOutputStream out;
    private String nickname;

    public ManejadorCliente(Socket socket, Servidor servidor) {
        this.socket = socket;
        this.servidor = servidor;
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            String linea;
            while ((linea = in.readUTF()) != null) {
                if (linea.startsWith("@login|")) {
                    nickname = linea.substring(7);
                    servidor.broadcast("@notif|" + nickname + " se ha conectado");
                    servidor.broadcast("@lista|" + String.join(",", servidor.getNicks()));
                } else if (linea.startsWith("@msg|")) {
                    String texto = linea.substring(5);
                    servidor.broadcast("@msg|" + nickname + "|" + texto);
                } else if (linea.equals("@salir")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Cliente " + nickname + " desconectado abruptamente");
        } finally {
            desconectar();
        }
    }

    public void enviar(String mensaje) {
        try {
            out.writeUTF(mensaje);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void desconectar() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        servidor.removerCliente(this);
        if (nickname != null) {
            servidor.broadcast("@notif|" + nickname + " se ha desconectado");
            servidor.broadcast("@lista|" + String.join(",", servidor.getNicks()));
        }
    }

    public String getNickname() {
        return nickname;
    }

}
