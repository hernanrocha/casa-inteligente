package casa.termostato;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BehaviourChangeWaitResponse extends Behaviour {

	private static final long serialVersionUID = 1L;
	
	private boolean procesado = false;
	
	@Override
	public void action() {
		Termostato termostato = (Termostato) myAgent;
		
		MessageTemplate mt = (MessageTemplate) getDataStore().get("mt-request");
		
		ACLMessage msg = myAgent.receive(mt);
		if (msg != null) {	
			if(msg.getPerformative() == ACLMessage.AGREE){
				termostato.log("Respuesta: AGREE. La ventana acepta cambiar de estado");
			}else{
				termostato.log("Respuesta: Desconocida");
			}
			procesado = true;
		} else {
			block();
		}
	}

	@Override
	public boolean done() {
		return procesado;
	}

}
