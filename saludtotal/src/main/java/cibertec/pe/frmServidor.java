package cibertec.pe;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class frmServidor extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextArea txtResultado = new JTextArea();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmServidor frame = new frmServidor();
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
	public frmServidor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 239);
		contentPane.add(scrollPane);
		
		//JTextArea txtResultado = new JTextArea();
		scrollPane.setViewportView(txtResultado);
		
		
		iniciarServidor();
	
	}
	
	void iniciarServidor() {
		new Thread(()->{
			try {
				ServerSocket server = new ServerSocket(5000);
				txtResultado.append("Servidor Iniciado" + "\n");
				
				Socket socket = server.accept();
				
				txtResultado.append("Cliente conectado" + "\n\n");
				
				ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
				List<Afiliado> listAfiliados = (List<Afiliado>) entrada.readObject();
				
				procesarAfiliados(listAfiliados);
				
				socket.close();
				server.close();
				
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}).start();
		
	}
	
	void procesarAfiliados(List<Afiliado> afiliados) {
		
		double sumaTotal = 0;
		int cantidadMorosos = 0;
		for(Afiliado c : afiliados) {
			
			
			sumaTotal = sumaTotal + c.getMonto();
			
			
			String estado; 
			if (c.getCondicion() == 1) estado = "Puntual";
			else {
				estado = "Moroso";
				cantidadMorosos++;
			}
			
			txtResultado.append("Codigo: " + c.getCodigo() + "\n");
			txtResultado.append("Nombre: " + c.getNombre() + "\n");
			txtResultado.append("Apellido: " + c.getApellido() + "\n");
			txtResultado.append("Correo: " + c.getCorreo() + "\n");
			txtResultado.append("Monto: " + c.getMonto()+ "\n");
			txtResultado.append("Condicion: " + estado+ "\n\n\n");
			
		}
		
		txtResultado.append("La suma total del monto es: " + sumaTotal + "\n\n");
		txtResultado.append("La promedio del monto total del monto es: " + (sumaTotal/afiliados.size()) + "\n\n");
		txtResultado.append("La suma total de Morosos es: " + cantidadMorosos + "\n");  
		txtResultado.append("La suma total de Puntuales es: " + (afiliados.size() - cantidadMorosos) + "\n");	
	}
}
