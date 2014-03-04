package casa.termostato;

import jade.gui.GuiEvent;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class TermostatoFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final String CONTROL_ACTIVAR = "Activar Control";
	public static final String CONTROL_DESACTIVAR = "Desactivar Control";
	
	private JPanel contentPane;
	Termostato myAgent;

	private JTextPane txtLog;

	private JButton btnControl;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;

	/**
	 * Create the frame.
	 */
	public TermostatoFrame(Termostato t) {
		setBounds(100, 100, 600, 300);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setContentPane(contentPane);

		this.myAgent = t;
		initGUI();
		
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
            	myAgent.doDelete();
            }
        });
	}

	private void initGUI() {
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 100, 0};
		gbl_contentPane.rowHeights = new int[]{160, 23, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TermostatoFrame.class.getResource("/img/termostato.jpg")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);
		
		txtLog = new JTextPane();
		scrollPane.setViewportView(txtLog);
		txtLog.setText("------------------------------ Termostato - Logs ------------------------------");
		
		btnControl = new JButton(CONTROL_ACTIVAR);
		btnControl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controlPerformed();
			}
		});
		GridBagConstraints gbc_btnControl = new GridBagConstraints();
		gbc_btnControl.insets = new Insets(0, 0, 5, 5);
		gbc_btnControl.gridx = 0;
		gbc_btnControl.gridy = 1;
		contentPane.add(btnControl, gbc_btnControl);
		
		JButton btnSalir = new JButton("Finalizar Agente");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myAgent.doDelete();
//				dispose();
			}
		});
		GridBagConstraints gbc_btnSalir = new GridBagConstraints();
		gbc_btnSalir.insets = new Insets(0, 0, 0, 5);
		gbc_btnSalir.gridx = 0;
		gbc_btnSalir.gridy = 2;
		contentPane.add(btnSalir, gbc_btnSalir);
		
	}

	protected void controlPerformed() {
		// Desactivar boton
		btnControl.setEnabled(false);
		
		// Gui Event con el mensaje
		GuiEvent evento = new GuiEvent(this, 0);
		
		if(btnControl.getText().equals(CONTROL_ACTIVAR)){
			// Activar
			evento.addParameter(TermostatoFrame.CONTROL_ACTIVAR);
			btnControl.setText(TermostatoFrame.CONTROL_DESACTIVAR);
		}else if (btnControl.getText().equals(CONTROL_DESACTIVAR)){
			// Desactivar
			evento.addParameter(TermostatoFrame.CONTROL_DESACTIVAR);
			btnControl.setText(TermostatoFrame.CONTROL_ACTIVAR);
		}
		
		// Postear al Agente
		myAgent.postGuiEvent(evento);
	}

	public void log(String str) {	
		txtLog.setText(txtLog.getText() + "\n" + str);
	}

	public void enableControl() {
		btnControl.setEnabled(true);
	}
	
}
