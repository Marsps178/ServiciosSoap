package cibertec.pe.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Servidor {

    private final int puerto;
    private final List<ManejadorCliente> clientes;
    private final ExecutorService pool;
    private ServerSocket serverSocket;
    private boolean activo;

    public Servidor(int puerto) {
        this.puerto = puerto;
        this.clientes = new ArrayList<>();
        this.pool = Executors.newCachedThreadPool();
    }

    public void iniciar() {
        try {
            serverSocket = new ServerSocket(puerto);
            activo = true;
            System.out.println("Servidor iniciado en el puerto " + puerto);

            while (activo) {
                Socket sc = serverSocket.accept();
                System.out.println("Nuevo cliente conectado: " + sc.getInetAddress().getHostName());
                ManejadorCliente manejador = new ManejadorCliente(sc, this);
                synchronized (clientes) {
                    clientes.add(manejador);
                }
                pool.execute(manejador);
            }
        } catch (IOException e) {
            if (activo) {
                e.printStackTrace();
            }
        } finally {
            detener();
        }
    }

    public void broadcast(String mensaje) {
        synchronized (clientes) {
            for (ManejadorCliente c : clientes) {
                c.enviar(mensaje);
            }
        }
    }

    public void removerCliente(ManejadorCliente cliente) {
        synchronized (clientes) {
            clientes.remove(cliente);
        }
    }

    public List<String> getNicks() {
        synchronized (clientes) {
            return clientes.stream()
                    .map(ManejadorCliente::getNickname)
                    .filter(n -> n != null)
                    .collect(Collectors.toList());
        }
    }

    public void detener() {
        activo = false;
        pool.shutdownNow();
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Servidor detenido");
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor(5000);
        servidor.iniciar();
    }

}
