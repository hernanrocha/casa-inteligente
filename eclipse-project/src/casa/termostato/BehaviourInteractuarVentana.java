package casa.termostato;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.DataStore;
import jade.core.behaviours.FSMBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;

public class BehaviourInteractuarVentana extends FSMBehaviour {

	private static final long serialVersionUID = 1L;
	private String ventana;

	public BehaviourInteractuarVentana(DataStore ds) {
		super();

		// DataStore con DFAgentDescription de agente ventana en "window"
		setDataStore(ds);
		
		ventana = ((DFAgentDescription) ds.get("window")).getName().getLocalName();
		
		System.out.println(" ----- Iniciar interaccion con " + ventana + " ----- ");
				
		// Crear Sub-Comportamientos
		Behaviour consultaEnviar = new BehaviourQuerySend(); // Configura template de respuesta "mt-query" y "conv-id" para futuros mensajes
		Behaviour consultaRecibir = new BehaviourQueryWaitResponse();
		Behaviour requestEnviar = new BehaviourChangeRequest(); // Configura template de respuesta "mt-response"
		Behaviour requestRecibir = new BehaviourChangeWaitResponse();
		Behaviour finalizarInteraccion = new BehaviourFinalizarInteraccion();
		
		// Configurar DataStore compartido
		consultaEnviar.setDataStore(ds);
		consultaRecibir.setDataStore(ds);
		requestEnviar.setDataStore(ds);
		requestRecibir.setDataStore(ds);

		// Agregar lista de comportamientos
//		addSubBehaviour(consultaEnviar);
//		addSubBehaviour(consultaRecibir);
//		addSubBehaviour(requestEnviar);
//		addSubBehaviour(requestRecibir);
		
		// Agregar estado inicial
		registerFirstState(consultaEnviar, "consultaEnviar");
		
		// Agregar estados intermedios
		registerState(consultaRecibir, "consultaRecibir");
		registerState(requestEnviar, "requestEnviar");
		registerState(requestRecibir, "requestRecibir");
		
		// Agregar estado final
		registerLastState(finalizarInteraccion, "finalizarInteraccion");
		
		// Agregar transicion entre estados
		registerDefaultTransition("consultaEnviar", "consultaRecibir");
		registerTransition("consultaRecibir", "finalizarInteraccion", 0);
		registerTransition("consultaRecibir", "requestEnviar", 1);
		registerDefaultTransition("requestEnviar", "requestRecibir");
		registerDefaultTransition("requestRecibir", "finalizarInteraccion");
	}
	
	@Override
	public int onEnd(){
		System.out.println(" ----- Interaccion con " + ventana + " finalizada ----- ");
		
		return super.onEnd();
	}

}
