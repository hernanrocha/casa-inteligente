package casa.ventana;

import javax.swing.SwingUtilities;

import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.content.onto.Ontology;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Ventana extends Agent {

	private static final long serialVersionUID = 1L;
	
	private Codec codec = new SLCodec();
	private Ontology ontology = AmbientOntology.getInstance();	
	private boolean closed;
	
	private VentanaFrame frame;
	
	protected void setup(){

		// Iniciar interfaz grafica
		frame = new VentanaFrame(this);
		frame.setTitle(getName());
		frame.setVisible(true);
		
		log("Iniciando agente " + getName());

		log("Registrando lenguaje");
		getContentManager().registerLanguage(codec);		

		log("Registrando ontologia");
		getContentManager().registerOntology(ontology);	
		
		log("Agregando comportamientos");
		
		// Esperar QUERY
		addBehaviour(new BehaviourProcesarConsultas());
		
		// Esperar REQUEST
		addBehaviour(new BehaviourProcesarSolicitudes());

		log("Registrando agente en DF");
		
		// Crear Descripcion de Servicio
		ServiceDescription serviceDesc = new ServiceDescription();
		serviceDesc.setType("ambient-intelligent");
		serviceDesc.setName("window");
		
		// Crear Descripcion de Agente
		DFAgentDescription agentDesc = new DFAgentDescription();
		agentDesc.setName(getAID());
		agentDesc.addServices(serviceDesc);
		
		// Registrar
		try {
			DFService.register(this, agentDesc);
		} catch (FIPAException e) {
			e.printStackTrace();
		}		

		log("Agente iniciado correctamente");
	}

	protected void takeDown() {
		log("Dando de baja agente " + getName());
		
		// Quitar el servicio del DF
		try {
			log("Quitando agente del DF");
			DFService.deregister(this);
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}
		
		log("Cerrando interfaz grafica");
		Runnable addIt = new Runnable() {
			public void run() {
				frame.dispose();
			}
		};
		SwingUtilities.invokeLater(addIt);
		
		log("Agente dado de baja correctamente");
	}
	
	public void log(final String str){
		System.out.println("[ Ventana  ] " + str);
		Runnable addIt = new Runnable() {
			public void run() {
				frame.log(str);
			}
		};
		SwingUtilities.invokeLater(addIt);
	}
	
	public boolean isClosed() {
		return closed;
	}

	public void setClosed(final boolean closed) {
		this.closed = closed;
		
		Runnable addIt = new Runnable() {
			public void run() {
				frame.setImagen(closed);
			}
		};
		SwingUtilities.invokeLater(addIt);
	}

}
