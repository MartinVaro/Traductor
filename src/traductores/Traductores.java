package traductores;

import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class Traductores extends JFrame {

	private JPanel contentPane;
	private JTable table;
	ArrayList<Token> lista_token = new ArrayList<Token>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Traductores frame = new Traductores();
					frame.setVisible(true);
					frame.setTitle("Analizador Léxico y Sintáctico");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Traductores() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 643, 456);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//TXT de codigo
		JTextArea txt_datos = new JTextArea();
		JScrollPane scroll_d = new JScrollPane(txt_datos);
		scroll_d.setBounds(20, 22, 222, 165);
		contentPane.add(scroll_d);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 225, 567, 159);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Token[][] {
			},
			new String[] {
				"Lexema", "Token", "#"
			}
		));
		
		
		JButton btn_txt = new JButton("");
		btn_txt.setEnabled(false);
		btn_txt.setBounds(353, 23, 234, 23);
		contentPane.add(btn_txt);
		btn_txt.setBackground(Color.lightGray);
		
		//BOTON SINTACTICO
		JTextArea txt_sintactico = new JTextArea();
		JScrollPane scroll = new JScrollPane(txt_sintactico);
		scroll.setBounds(353, 49, 234, 138);
		contentPane.add(scroll);
		
		JButton btn_sintactico = new JButton("Sintáctico");
		btn_sintactico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sintactico sint = new Sintactico(lista_token);
				boolean validar;
				String datos;
				validar = sint.LR_1_2();
				datos = sint.datoSintactico();
				
				if(validar) {
					btn_txt.setText("Expresión correcta");
					btn_txt.setBackground(Color.green);
					txt_sintactico.setText(datos);
				} else {
					btn_txt.setText("Error: Expresión incorrecta");
					btn_txt.setBackground(Color.red);
					txt_sintactico.setText(datos);
				}
			}
		});
		btn_sintactico.setBounds(252, 82, 97, 23);
		contentPane.add(btn_sintactico);
		btn_sintactico.setEnabled(false);
		
		//BOTON LEXICO
		JButton btn_validar = new JButton("Analizar");
		btn_validar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Le pasa la lista de lo escrito en el txt_editor
				new Lexico(lista_token).analizar(txt_datos.getText()+" $");
				
				//Se imprimen los datos en la matriz
				String matriz[][] = new String[lista_token.size()][3];
				for(int i=0; i<lista_token.size(); i++) {
					matriz[i][0] = lista_token.get(i).getLexema();
					matriz[i][1] = lista_token.get(i).getTipo();
					matriz[i][2] = Integer.toString(lista_token.get(i).getNumero_token());
				}
				
				table.setModel(new DefaultTableModel(
						matriz,
						new String[] {
							"Lexema", "Token", "#"
						}
					));
				btn_sintactico.setEnabled(true);
			}
		});
		btn_validar.setBounds(252, 23, 89, 23);
		contentPane.add(btn_validar);
		
		//BOTON LIMPIAR
		JButton btn_limpiar = new JButton("Limpiar");
		btn_limpiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lista_token.clear();
				txt_datos.setText("");
				txt_sintactico.setText("");
				table.setModel(new DefaultTableModel(
						new Token[][] {
						},
						new String[] {
							"Lexema", "Token", "#"
						}
					));
				btn_sintactico.setEnabled(false);
				btn_txt.setText("");
				btn_txt.setBackground(Color.lightGray);
			}
		});
		btn_limpiar.setBounds(252, 151, 89, 23);
		contentPane.add(btn_limpiar);
	}
}
