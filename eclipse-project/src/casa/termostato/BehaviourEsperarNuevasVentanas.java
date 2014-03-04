package casa.termostato;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.lang.acl.ACLMessage;
import jade.proto.SubscriptionInitiator;

public class BehaviourEsperarNuevasVentanas extends SubscriptionInitiator {

	private static final long serialVersionUID = 1L;
	
	public BehaviourEsperarNuevasVentanas(Agent a, ACLMessage msg) {
		super(a, msg);
		
	}

	protected void handleInform(ACLMessage inform) {
		try {
			DFAgentDescription[] dfds =	DFService.decodeNotification(inform.getContent());
			
			// Procesar descripcion de servicio
			Behaviour b = new BehaviourProcesarDescripciones(dfds);
			b.getDataStore().put("closed", getDataStore().get("closed"));			
			myAgent.addBehaviour(b);
			
			// Cancelar suscripcion
			cancel(myAgent.getDefaultDF(), true);
			((Termostato) myAgent).setDisponbible(true);
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}

}
