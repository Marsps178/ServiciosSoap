package cibertec.pe;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class frmCliente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmCliente frame = new frmCliente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmCliente() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 311);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(e -> enviarAfiliado());
		btnEnviar.setBounds(180, 24, 89, 23);
		contentPane.add(btnEnviar);

	}
	
	void enviarAfiliado() {
		Afiliado afiliado1 = new Afiliado(123,1500,true);
		Afiliado afiliado2 = new Afiliado(456,580,false);
		Afiliado afiliado3 = new Afiliado(789,1500,false);
		Afiliado afiliado4 = new Afiliado(901,1800,false);
		Afiliado afiliado5 = new Afiliado(789,1200,false);
		
		List<Afiliado> afiliados = new ArrayList<>();
		
		afiliados.add(afiliado1);
		afiliados.add(afiliado2);
		afiliados.add(afiliado3);
		afiliados.add(afiliado4);
		afiliados.add(afiliado5);
		
		try {
			Socket socket = new Socket("localhost", 500);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(afiliados);
			
			socket.close();
			
			JOptionPane.showMessageDialog(null, "Lista de afiliados enviados");
		} catch (UnknownHostException e){
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
