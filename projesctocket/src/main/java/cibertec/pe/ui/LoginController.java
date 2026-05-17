package cibertec.pe.ui;

import cibertec.pe.cliente.ClienteSocket;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField txtHost;
    @FXML private TextField txtPuerto;
    @FXML private TextField txtNick;
    @FXML private Button btnConectar;
    @FXML private Label lblEstado;

    private ClienteSocket cliente;

    @FXML
    private void conectar() {
        String host = txtHost.getText().trim();
        String puertoTexto = txtPuerto.getText().trim();
        String nick = txtNick.getText().trim();

        if (host.isEmpty() || puertoTexto.isEmpty() || nick.isEmpty()) {
            lblEstado.setText("Todos los campos son obligatorios");
            return;
        }

        int puerto;
        try {
            puerto = Integer.parseInt(puertoTexto);
        } catch (NumberFormatException e) {
            lblEstado.setText("Puerto inválido");
            return;
        }

        btnConectar.setDisable(true);
        lblEstado.setText("Conectando...");

        new Thread(() -> {
            try {
                cliente = new ClienteSocket();
                cliente.conectar(host, puerto);
                cliente.enviar("@login|" + nick);

                Platform.runLater(() -> abrirChat(nick));
            } catch (Exception e) {
                Platform.runLater(() -> {
                    lblEstado.setText("Error de conexión: " + e.getMessage());
                    btnConectar.setDisable(false);
                });
            }
        }).start();
    }

    private void abrirChat(String nick) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/chat.fxml"));
            Scene scene = new Scene(loader.load(), 500, 400);

            ChatController controller = loader.getController();
            controller.inicializar(cliente, nick);

            Stage stage = (Stage) btnConectar.getScene().getWindow();
            stage.setTitle("Chat - " + nick);
            stage.setScene(scene);
            stage.setResizable(true);
        } catch (Exception e) {
            lblEstado.setText("Error al abrir chat: " + e.getMessage());
            btnConectar.setDisable(false);
        }
    }

}
