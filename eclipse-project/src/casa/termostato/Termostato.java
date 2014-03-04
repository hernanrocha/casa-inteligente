package casa.termostato;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;

import javax.swing.SwingUtilities;

public class Termostato extends GuiAgent {

	private static final long serialVersionUID = 1L;
	
	public Codec codec = new SLCodec();
	public Ontology ontology = AmbientOntology.getInstance();
	private TermostatoFrame frame;

	private BehaviourSensor control;

	private boolean disponible;
	
	protected void setup(){

		// Iniciar interfaz grafica
		frame = new TermostatoFrame(this);
		frame.setTitle(getName());
		frame.setVisible(true);
		
		log("Iniciando agente " + getName());

		log("Registrando lenguaje");
		getContentManager().registerLanguage(codec);

		log("Registrando ontologia");
		getContentManager().registerOntology(ontology);
		
		control = new BehaviourSensor(this);
		disponible = true;

		log("Agente iniciado correctamente");
	}
	
	protected void takeDown() {

		log("Dando de baja agente " + getName());

		log("Cerrando interfaz grafica");
		Runnable addIt = new Runnable() {
			public void run() {
				frame.dispose();
			}
		};
		SwingUtilities.invokeLater(addIt);
		
		log("Agente dado de baja correctamente");
		
	}

	@Override
	protected void onGuiEvent(GuiEvent evt) {
		String act = (String) evt.getParameter(0);
		
		if ( act.equals(TermostatoFrame.CONTROL_ACTIVAR) ){
			log("Activar Control de Temperatura");
			addBehaviour(control);
			setEnabled(true);
		}else if ( act.equals(TermostatoFrame.CONTROL_DESACTIVAR) ){
			log("Desactivar Control de Temperatura");
			removeBehaviour(control);
			setEnabled(false);
		}
	}
	
	public void log(final String str){
		System.out.println("[Termostato] " + str);
		Runnable addIt = new Runnable() {
			public void run() {
				frame.log(str);
			}
		};
		SwingUtilities.invokeLater(addIt);
	}
	
	private void setEnabled(final boolean enabled){
		if (enabled){
			log("Control de temperatura activado");
		}else{
			log("Control de temperatura desactivado");
		}
		
		Runnable addIt = new Runnable() {
			public void run() {
				frame.enableControl();
			}
		};
		SwingUtilities.invokeLater(addIt);
	}

	public void setDisponbible(boolean b) {
		disponible  = b;		
	}

	public boolean isDisponible() {
		return disponible;
	}
	
}
