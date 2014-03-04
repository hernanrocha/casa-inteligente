package casa.termostato;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BehaviourFindWindow extends OneShotBehaviour {

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {
		Termostato termostato = (Termostato) myAgent;	
	
		termostato.log("Buscando agentes con servicio ventana en DF");
	
		// Crear Template de Servicio
		ServiceDescription serviceDesc = new ServiceDescription();
		serviceDesc.setType("ambient-intelligent");
		serviceDesc.setName("window");

		// Crear Template de Agente
		DFAgentDescription template = new DFAgentDescription();
		template.addServices(serviceDesc);

		try {
			// Realizar busqueda
			DFAgentDescription[] result = DFService.search(myAgent,	template);
			
			if(result.length == 0){
				termostato.log("Ninguna ventana activa. Suscribirse");
				
				termostato.setDisponbible(false);
				Behaviour b = new BehaviourEsperarNuevasVentanas(
					myAgent,
					DFService.createSubscriptionMessage(myAgent, myAgent.getDefaultDF(), template, null));
				b.getDataStore().put("closed", getDataStore().get("closed"));
				myAgent.addBehaviour(b);
			}else{
				Behaviour b = new BehaviourProcesarDescripciones(result);
				b.getDataStore().put("closed", getDataStore().get("closed"));
				
				myAgent.addBehaviour(b);
			}
		}
		catch (FIPAException fe) {
			fe.printStackTrace();
		}

	}

}
