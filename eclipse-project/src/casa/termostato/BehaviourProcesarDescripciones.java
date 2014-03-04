package casa.termostato;

import jade.core.behaviours.DataStore;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.FIPAAgentManagement.DFAgentDescription;

public class BehaviourProcesarDescripciones extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;
	
	private DFAgentDescription[] result;
	
	public BehaviourProcesarDescripciones(DFAgentDescription[] result){
		this.result = result;
	}

	@Override
	public void action() {
		Termostato termostato = (Termostato) myAgent;	
		
		// Ver resultados
		for (DFAgentDescription agente : result){
			termostato.log("Ventana Encontrada: " + agente.getName().getName());
			termostato.log("Iniciar interaccion");
			DataStore ds = new DataStore();
			System.out.println("CLOSED: " + getDataStore().get("closed"));
			ds.put("closed", getDataStore().get("closed"));
			ds.put("window", agente);
			BehaviourInteractuarVentana interaccion = new BehaviourInteractuarVentana(ds);
			myAgent.addBehaviour(interaccion);
		}

	}

}
