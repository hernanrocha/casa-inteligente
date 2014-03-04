package casa.termostato;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BehaviourQueryWaitResponse extends Behaviour {

	private static final long serialVersionUID = 1L;
	
	private boolean procesado = false;
	private int returnState = 0;
	
	@Override
	public void action() {
		Termostato termostato = (Termostato) myAgent;
		
		MessageTemplate mt = (MessageTemplate) getDataStore().get("mt-query");
		
		ACLMessage msg = myAgent.receive(mt);
		if (msg != null) {
			if(msg.getPerformative() == ACLMessage.CONFIRM){
				termostato.log("Respuesta: CONFIRM. La ventana esta cerrada");
				if(getDataStore().get("closed").equals("n")){
					termostato.log("Iniciar solicitud de cambio a abierta");
					returnState = 1;
				}
			}else if(msg.getPerformative() == ACLMessage.DISCONFIRM){
				termostato.log("Respuesta: DISCONFIRM. La ventana esta abierta");
				if(getDataStore().get("closed").equals("y")){
					termostato.log("Iniciar solicitud de cambio a cerrada");
					returnState = 1;
				}
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

	@Override
	public int onEnd(){
		return returnState;
	}
	
}
