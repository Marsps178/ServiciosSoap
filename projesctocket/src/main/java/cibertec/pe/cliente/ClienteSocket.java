package cibertec.pe.cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClienteSocket {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean conectado;

    public void conectar(String host, int puerto) throws IOException {
        socket = new Socket(host, puerto);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        conectado = true;
    }

    public void enviar(String mensaje) throws IOException {
        if (conectado) {
            out.writeUTF(mensaje);
        }
    }

    public String leer() throws IOException {
        return in.readUTF();
    }

    public void desconectar() {
        conectado = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConectado() {
        return conectado;
    }

}
