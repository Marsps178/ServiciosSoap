package cibertec.pe.ui;

import cibertec.pe.cliente.ClienteSocket;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class ChatController {

    @FXML private TextArea txtAreaMensajes;
    @FXML private TextField txtInput;
    @FXML private Button btnEnviar;
    @FXML private Button btnDesconectar;
    @FXML private ListView<String> lstUsuarios;

    private ClienteSocket cliente;
    private String nickname;
    private Thread lectorThread;
    private volatile boolean activo;

    public void inicializar(ClienteSocket cliente, String nickname) {
        this.cliente = cliente;
        this.nickname = nickname;
        this.activo = true;

        txtInput.setOnAction(e -> enviarMensaje());
        btnEnviar.setOnAction(e -> enviarMensaje());
        btnDesconectar.setOnAction(e -> desconectar());

        lectorThread = new Thread(this::escucharServidor);
        lectorThread.setDaemon(true);
        lectorThread.start();
    }

    private void escucharServidor() {
        try {
            while (activo && cliente.isConectado()) {
                String mensaje = cliente.leer();

                Platform.runLater(() -> {
                    if (mensaje.startsWith("@msg|")) {
                        String[] partes = mensaje.split("\\|", 3);
                        if (partes.length == 3) {
                            txtAreaMensajes.appendText(partes[1] + ": " + partes[2] + "\n");
                        }
                    } else if (mensaje.startsWith("@notif|")) {
                        txtAreaMensajes.appendText("[ " + mensaje.substring(7) + " ]\n");
                    } else if (mensaje.startsWith("@lista|")) {
                        String[] nicks = mensaje.substring(7).split(",");
                        lstUsuarios.getItems().clear();
                        lstUsuarios.getItems().addAll(nicks);
                    }
                });
            }
        } catch (Exception e) {
            if (activo) {
                Platform.runLater(() -> {
                    txtAreaMensajes.appendText("[ Conexión perdida ]\n");
                    btnEnviar.setDisable(true);
                });
            }
        }
    }

    @FXML
    private void enviarMensaje() {
        String texto = txtInput.getText().trim();
        if (texto.isEmpty()) return;

        try {
            if (texto.equals("/salir")) {
                desconectar();
                return;
            }
            cliente.enviar("@msg|" + texto);
            txtInput.clear();
        } catch (Exception e) {
            txtAreaMensajes.appendText("[ Error al enviar mensaje ]\n");
        }
    }

    @FXML
    private void desconectar() {
        activo = false;
        try {
            cliente.enviar("@salir");
            cliente.desconectar();
        } catch (Exception ignored) {}

        Platform.runLater(() -> {
            Stage stage = (Stage) btnDesconectar.getScene().getWindow();
            stage.close();
            System.exit(0);
        });
    }

}
