/**
 * 
 */
package casa.ventana;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

/**
 * @author hernan
 *
 */
public class VentanaFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private Ventana myAgent;
	private JTextPane txtLog;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	
	private ImageIcon ventanaAbierta = new ImageIcon(VentanaFrame.class.getResource("/img/ventana_abierta.jpg"));
	private ImageIcon ventanaCerrada = new ImageIcon(VentanaFrame.class.getResource("/img/ventana_cerrada.jpg"));

	/**
	 * Create the frame.
	 */
	public VentanaFrame(Ventana v) {
		setBounds(100, 100, 600, 300);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		this.myAgent = v;
		initGUI();
		
		this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
            	myAgent.doDelete();
            }
        });
	}

	private void initGUI() {
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{125, 100, 0};
		gbl_contentPane.rowHeights = new int[]{160, 23, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		lblNewLabel = new JLabel();
		lblNewLabel.setIcon(ventanaAbierta);
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridheight = 2;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane.gridx = 1;
		gbc_scrollPane.gridy = 0;
		contentPane.add(scrollPane, gbc_scrollPane);

		txtLog = new JTextPane();
		scrollPane.setViewportView(txtLog);
		txtLog.setText("------------------------------ Ventana - Logs ------------------------------");
				
						JButton btnSalir = new JButton("Finalizar Agente");
						btnSalir.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								myAgent.doDelete();
								dispose();
							}
						});
						GridBagConstraints gbc_btnSalir = new GridBagConstraints();
						gbc_btnSalir.insets = new Insets(0, 0, 0, 5);
						gbc_btnSalir.gridx = 0;
						gbc_btnSalir.gridy = 1;
						contentPane.add(btnSalir, gbc_btnSalir);
		
	}

	public void log(String str) {	
		txtLog.setText(txtLog.getText() + "\n" + str);
	}

	public void setImagen(boolean closed) {
		if(closed){
			lblNewLabel.setIcon(ventanaCerrada);
		}else{
			lblNewLabel.setIcon(ventanaAbierta);
		}
	}

}
