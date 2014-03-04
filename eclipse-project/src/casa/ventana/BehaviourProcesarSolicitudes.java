package casa.ventana;

import jade.content.lang.Codec.CodecException;
import jade.content.onto.OntologyException;
import jade.content.onto.UngroundedException;
import jade.content.onto.basic.Action;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class BehaviourProcesarSolicitudes extends CyclicBehaviour {

	private static final long serialVersionUID = 1L;

	@Override
	public void action() {

		Ventana ventana = (Ventana) myAgent;
		
		// Esperar Solicitudes
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
		ACLMessage msg = myAgent.receive(mt);

		Action act = null;
		if (msg != null) {
			ventana.log("REQUEST Recibido");
			try {
				// Procesar Consulta
				act = (Action) myAgent.getContentManager().extractContent(msg);				
				ChangeWindowStateAction changeState = (ChangeWindowStateAction) act.getAction();
				ventana.log("Agente Solicitante: " + act.getActor().getName());

				// Procesar Respuesta
				ACLMessage resp = msg.createReply();
				resp.setPerformative(ACLMessage.AGREE);
				Ventana v = (Ventana) myAgent;
				if (v.isClosed()){
					ventana.log("Realizar accion: Abrir Ventana");
					v.setClosed(false);
				}else{
					ventana.log("Realizar accion: Cerrar Ventana");
					v.setClosed(true);
				}

				try {			
					// Agregar el contenido
					myAgent.getContentManager().fillContent(resp, new Action(myAgent.getAID(), changeState));
				} catch (CodecException e) {
					e.printStackTrace();
				} catch (OntologyException e) {
					e.printStackTrace();
				}

				// Enviar respuesta
				myAgent.send(resp);
				
				ventana.log("REQUEST Respondido");
			} catch (UngroundedException e) {
				e.printStackTrace();
			} catch (CodecException e) {
				e.printStackTrace();
			} catch (OntologyException e) {
				e.printStackTrace();
			} catch (ClassCastException e){
				e.printStackTrace();
			}
		}else{
			block();
		}

	}

}
